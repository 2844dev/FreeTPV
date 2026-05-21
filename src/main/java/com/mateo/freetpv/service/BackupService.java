package com.mateo.freetpv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BackupService {

    private static final Logger log = LoggerFactory.getLogger(BackupService.class);

    public boolean hacerBackup(String rutaDestino) {
        if (rutaDestino.isEmpty()) return false;
        try {
            Files.createDirectories(Path.of(rutaDestino));

            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Path archivoDestino = Path.of(rutaDestino, "freetpv_" + fecha + ".tpv");

            Path origen = Path.of(System.getProperty("user.home"), ".freetpv");
            Path logsDir = origen.resolve("logs");
            try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(archivoDestino.toFile()))) {
                Files.walk(origen)
                        .filter(path -> !path.startsWith(logsDir))
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                String entryName = origen.relativize(path).toString();
                                zip.putNextEntry(new ZipEntry(entryName));
                                Files.copy(path, zip);
                                zip.closeEntry();
                            } catch (IOException e) {
                                log.error("Error al comprimir archivo: {}", path, e);
                            }
                        });
            }
            return true;
        } catch (IOException e) {
            log.error("No se pudo hacer la copia de seguridad", e);
            return false;
        }
    }

    public boolean restaurarBackup(File backup) {
        if (backup == null || !backup.exists()) return false;
        Path destino = Path.of(System.getProperty("user.home"), ".freetpv");
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(backup))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                Path destinoFinal = destino.resolve(entry.getName());
                if (!destinoFinal.normalize().startsWith(destino.normalize())) {
                    throw new IOException("Entrada ZIP fuera del directorio permitido: " + entry.getName());
                }
                Files.createDirectories(destinoFinal.getParent());
                Files.copy(zip, destinoFinal, StandardCopyOption.REPLACE_EXISTING);
                zip.closeEntry();
            }
            return true;
        } catch (IOException e) {
            log.error("No se pudo importar la copia de seguridad", e);
            return false;
        }

    }
}
