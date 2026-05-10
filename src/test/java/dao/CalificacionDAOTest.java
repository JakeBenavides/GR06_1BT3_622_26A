package dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalificacionDAOTest {
    private final CalificacionDAO calificacionDAO = new CalificacionDAO();

    @Test
    public void testCalcularPromedioPorUsuario_ZeroIfNoReviews() {
        double result = calificacionDAO.calcularPromedioPorUsuario(9999);
        assertEquals(0.0, result);
    }
}
