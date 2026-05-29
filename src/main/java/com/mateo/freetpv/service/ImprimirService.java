package com.mateo.freetpv.service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.image.BitonalThreshold;
import com.github.anastaciocintra.escpos.image.CoffeeImageImpl;
import com.github.anastaciocintra.escpos.image.EscPosImage;
import com.github.anastaciocintra.escpos.image.GraphicsImageWrapper;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mateo.freetpv.model.DatosTicket;
import com.mateo.freetpv.model.LineaTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.PrintService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mateo.freetpv.util.ConversionUtil.centimosEuros;

public class ImprimirService {
    private static int WIDTH = 32;

    private static final Logger log = LoggerFactory.getLogger(ImprimirService.class);

    public static void imprimirTicket(PrintService printService, DatosTicket ticket, int ancho, String codepage, boolean cortar) throws IOException {
        WIDTH = ancho == 58 ? 32 : 48;


        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        EscPos escpos = new EscPos(printerOutputStream);

        Style center = new Style()
                .setJustification(EscPosConst.Justification.Center);

        Style centerBold = new Style()
                .setBold(true)
                .setJustification(EscPosConst.Justification.Center);

        Style left = new Style()
                .setJustification(EscPosConst.Justification.Left_Default);

        Style totalStyle = new Style()
                .setBold(true)
                .setJustification(EscPosConst.Justification.Center);

        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = ahora.format(DateTimeFormatter.ofPattern("HH:mm"));

        escpos.initializePrinter();
        EscPos.CharacterCodeTable charset = switch (codepage) {
            case "CP850" -> EscPos.CharacterCodeTable.CP850_Multilingual;
            default -> EscPos.CharacterCodeTable.CP858_Euro;
        };
        escpos.setCharacterCodeTable(charset);


        escpos.feed(1);
        escpos.writeLF(centerBold, ticket.nombreEmpresa());
        if (ticket.mostrarCif()) escpos.writeLF(center, "CIF/NIF: " + ticket.cif());
        escpos.writeLF(center, ticket.direccion());
        escpos.writeLF(center, ticket.codigoPostal() + " " + ticket.ciudad());
        if (ticket.mostrarTelefono()) escpos.writeLF(center, "Tlf: " + ticket.telefono());

        escpos.writeLF(left, line());
        escpos.writeLF(centerBold, ticket.tituloTicket());
        escpos.writeLF(left, line());

        escpos.writeLF(left, "Ticket Nº: N/A");
        escpos.writeLF(left, twoCols("Fecha: " + fecha, "Hora: " + hora));
        escpos.writeLF(left, twoCols("Caja: 1", "Empleado: " + ticket.nombreCamarero()));

        escpos.writeLF(left, line());
        escpos.writeLF(left, itemHeader());
        escpos.writeLF(left, line());

        for (LineaTicket linea : ticket.lineas()) {
            escpos.writeLF(left, itemRow(linea.getNombreTicket(),
                    String.valueOf(linea.getCantidad()),
                    centimosEuros(linea.getPrecioUnitario()).orElse("0,00") + "€",
                    centimosEuros(linea.getSubtotal()).orElse("0,00") + "€"));
        }

        escpos.writeLF(left, line());

        int total = ticket.lineas().stream().mapToInt(LineaTicket::getSubtotal).sum();
        int iva = ticket.lineas().stream().mapToInt(l -> {
            double factor = l.getIva() / 100.0;
            return (int) Math.round(l.getSubtotal() * factor / (1 + factor));
        }).sum();
        int subtotal = total - iva;

        escpos.writeLF(left, amountRow("Subtotal:", centimosEuros(subtotal).orElse("0,00") + " €"));

        if (ticket.mostrarIva()) {
            Map<Integer, List<LineaTicket>> lineasPorIva = ticket.lineas().stream()
                    .collect(Collectors.groupingBy(LineaTicket::getIva));

            lineasPorIva.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                    .forEach(entry -> {
                        int tipo = entry.getKey();
                        if (tipo == 0) return;
                        int cuota = entry.getValue().stream().mapToInt(l -> {
                            double factor = l.getIva() / 100.0;
                            return (int) Math.round(l.getSubtotal() * factor / (1 + factor));
                        }).sum();
                        try {
                            escpos.writeLF(left, amountRow(
                                    "IVA " + tipo + "%:",
                                    centimosEuros(cuota).orElse("0,00") + " €"
                            ));
                        } catch (IOException e) {
                            log.error("Error al escribir IVA", e);
                        }
                    });
        }

        escpos.writeLF(left, line());
        escpos.writeLF(totalStyle, "TOTAL: " + centimosEuros(total).orElse("0,00") + " €");
        escpos.writeLF(left, line());
        escpos.writeLF(left, amountRow("Pago:", ticket.metodoPago()));

        if (ticket.entregado() > 0) {
            int cambio = ticket.entregado() - total;
            escpos.writeLF(left, amountRow("Entregado:", centimosEuros(ticket.entregado()).orElse("0,00") + " €"));
            escpos.writeLF(left, amountRow("Cambio:", centimosEuros(cambio).orElse("0,00") + " €"));
        }

        escpos.writeLF(left, line());

        escpos.feed(1);
        escpos.writeLF(center, ticket.mensajeFinal());
        escpos.feed(1);
        if (ticket.mostrarWeb()) escpos.writeLF(center, ticket.web());

        if (ticket.mostrarQr() && !ticket.qr().isBlank()) {
            try {
                BitMatrix bitMatrix = new QRCodeWriter().encode(ticket.qr(), BarcodeFormat.QR_CODE, 200, 200);
                BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                GraphicsImageWrapper imageWrapper = new GraphicsImageWrapper();
                imageWrapper.setJustification(EscPosConst.Justification.Center);
                EscPosImage escposImage = new EscPosImage(new CoffeeImageImpl(qrImage), new BitonalThreshold());
                escpos.write(imageWrapper, escposImage);
            } catch (WriterException e) {
                log.error("Error al dibujar el código QR", e);
            }
        }

        escpos.feed(2);
        if (cortar) escpos.cut(EscPos.CutMode.FULL);
        escpos.close();
    }

    public static void abrirCajon(PrintService printService) throws IOException {
        PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
        EscPos escpos = new EscPos(printerOutputStream);
        escpos.pulsePin(EscPos.PinConnector.Pin_2, 120, 240);
        escpos.pulsePin(EscPos.PinConnector.Pin_5, 120, 240);
        escpos.close();
    }

    private static String line() {
        return "-".repeat(WIDTH);
    }

    private static String twoCols(String left, String right) {
        int spaces = WIDTH - left.length() - right.length();
        if (spaces < 1) spaces = 1;
        return left + " ".repeat(spaces) + right;
    }

    private static String amountRow(String label, String amount) {
        String text = label + " " + amount;
        if (text.length() > WIDTH) {
            return text.substring(0, WIDTH);
        }

        int spaces = WIDTH - label.length() - amount.length();
        return label + " ".repeat(Math.max(1, spaces)) + amount;
    }

    private static String itemHeader() {
        return String.format("%-13s%3s%7s%9s",
                "Producto", "Ud", "P.U", "Total");
    }

    private static String itemRow(String name, String qty, String unitPrice, String total) {
        return String.format("%-13s%3s%7s%9s",
                cut(name, 13),
                qty,
                unitPrice,
                total
        );
    }

    private static String cut(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max);
    }
}