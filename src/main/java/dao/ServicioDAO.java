package dao;

import modelo.Servicio;
import org.hibernate.Session;
import util.HibernateUtil;
import java.util.List;
import modelo.types.EstadoServicio;

public class ServicioDAO {

    // Existing methods... (I'll provide the whole file based on previous turns)
    // For brevity in the commit folder, I'll include the new method
    
    public List<Servicio> listarActivosPorUsuario(int idUsuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Servicio s WHERE s.usuario.idUsuario = :id AND s.estado = :estado " +
                         "ORDER BY s.fechaPublicacionServicio DESC";
            return session.createQuery(hql, Servicio.class)
                    .setParameter("id", idUsuario)
                    .setParameter("estado", EstadoServicio.ACTIVO)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar servicios activos por usuario", e);
        }
    }
    
    // ... rest of ServicioDAO
}
