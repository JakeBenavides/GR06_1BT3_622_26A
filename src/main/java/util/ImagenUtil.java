package util;

import jakarta.servlet.http.Part;
import java.io.IOException;

public class ImagenUtil {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
    private static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png"};

    public static String validarImagen(Part filePart) {
        // TDD: Implementation removed to make tests fail (Red Phase)
        return null;
    }

    public static String guardarImagen(Part filePart, String uploadPath) throws IOException {
        // TDD: Failing implementation
        return "placeholder.png";
    }
}
