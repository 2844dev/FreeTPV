package com.mateo.freetpv.service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;

import javax.print.PrintService;
import java.io.IOException;

public class ImprimirService {
    private static final int WIDTH = 32;

    public static void imprimirTicket(PrintService printService) throws IOException {
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

        escpos.initializePrinter();

        // Charset / codepage para tildes y ñ
        escpos.setCharacterCodeTable(EscPos.CharacterCodeTable.CP858_Euro);

        escpos.writeLF(centerBold, "NOMBRE DEL NEGOCIO");
        escpos.writeLF(center, "CIF/NIF: X00000000");
        escpos.writeLF(center, "Calle Ejemplo 123");
        escpos.writeLF(center, "28000 Madrid");
        escpos.writeLF(center, "Tel: 600 000 000");

        escpos.writeLF(left, line());
        escpos.writeLF(centerBold, "TICKET DE VENTA");
        escpos.writeLF(left, line());

        escpos.writeLF(left, "Ticket Nº: 000001");
        escpos.writeLF(left, twoCols("Fecha: 12/05/2026", "Hora: 13:45"));
        escpos.writeLF(left, twoCols("Caja: 1", "Empleado: Admin"));

        escpos.writeLF(left, line());
        escpos.writeLF(left, itemHeader());
        escpos.writeLF(left, line());

        escpos.writeLF(left, itemRow("Café solo", "1", "1,20", "1,20"));
        escpos.writeLF(left, itemRow("Bocadillo", "2", "3,50", "7,00"));
        escpos.writeLF(left, itemRow("Agua", "1", "1,00", "1,00"));

        escpos.writeLF(left, line());
        escpos.writeLF(left, amountRow("Subtotal:", "9,20"));
        escpos.writeLF(left, amountRow("IVA 10%:", "0,92"));
        escpos.writeLF(left, line());

        escpos.writeLF(totalStyle, "TOTAL: 10,12 €");

        escpos.writeLF(left, line());
        escpos.writeLF(left, amountRow("Pago:", "Tarjeta"));
        escpos.writeLF(left, amountRow("Entregado:", "10,12"));
        escpos.writeLF(left, amountRow("Cambio:", "0,00"));
        escpos.writeLF(left, line());

        escpos.feed(1);
        escpos.writeLF(center, "Gracias por su compra");
        escpos.feed(1);
        escpos.writeLF(center, "www.tunegocio.com");

        escpos.feed(4);
        escpos.cut(EscPos.CutMode.FULL);
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