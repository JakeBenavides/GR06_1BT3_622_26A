package dao;

import modelo.Servicio;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioDAOTest {
    private final ServicioDAO servicioDAO = new ServicioDAO();

    @Test
    public void testListarActivosPorUsuario_NotNull() {
        List<Servicio> result = servicioDAO.listarActivosPorUsuario(1);
        assertNotNull(result);
    }
}
