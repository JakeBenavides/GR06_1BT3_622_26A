package util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImagenUtil {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
    private static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png"};

    public static String validarImagen(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) return null;
        if (filePart.getSize() > MAX_FILE_SIZE) return "El archivo supera el tamaño máximo de 2 MB.";

        String mimeType = filePart.getContentType();
        boolean validMime = false;
        for (String type : ALLOWED_MIME_TYPES) {
            if (type.equalsIgnoreCase(mimeType)) {
                validMime = true;
                break;
            }
        }
        return validMime ? null : "Solo se permiten imágenes JPG o PNG.";
    }

    public static String guardarImagen(Part filePart, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = filePart.getSubmittedFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;

        File file = new File(uploadDir, newFileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return newFileName;
    }
}
