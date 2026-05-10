package dao;

import modelo.Calificacion;
import org.hibernate.Session;
import util.HibernateUtil;

public class CalificacionDAO {

    // Renamed method (Refactor)
    public Double calcularPromedioPorServicio(modelo.Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT AVG(c.puntuacion) FROM Calificacion c WHERE c.servicio = :servicio";
            return session.createQuery(hql, Double.class)
                    .setParameter("servicio", servicio)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular promedio", e);
        }
    }

    // New method for HU-02
    public double calcularPromedioPorUsuario(int idUsuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT AVG(c.puntuacion) FROM Calificacion c WHERE c.servicio.usuario.idUsuario = :id";
            Double avg = session.createQuery(hql, Double.class)
                    .setParameter("id", idUsuario)
                    .uniqueResult();
            return avg != null ? avg : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular promedio por usuario", e);
        }
    }
    
    // Existing methods (yaCalifico refactored etc.)
}
