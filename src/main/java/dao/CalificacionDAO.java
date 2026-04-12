package dao;

import modelo.Calificacion;
import modelo.Servicio;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class CalificacionDAO {

    // GUARDAR - registrarCalificacion() del diagrama de secuencia
    public void guardar(Calificacion calificacion) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(calificacion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al guardar calificación", e);
        }
    }

    // LISTAR POR SERVICIO - para mostrar calificaciones en el detalle
    public List<Calificacion> listarPorServicio(Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Calificacion c WHERE c.servicio = :servicio ORDER BY c.fechaCalificacion DESC";
            return session.createQuery(hql, Calificacion.class)
                    .setParameter("servicio", servicio)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar calificaciones", e);
        }
    }

    // CONTROL DUPLICIDAD - del diagrama de secuencia: verifica si usuario ya calificó ese servicio
    public boolean yaCalifico(Usuario usuario, Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(c) FROM Calificacion c WHERE c.usuario = :usuario AND c.servicio = :servicio";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("usuario", usuario)
                    .setParameter("servicio", servicio)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar calificación duplicada", e);
        }
    }

    // PROMEDIO - obtenerPromedio(Puntuacion) del diagrama de clases
    public Double obtenerPromedio(Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT AVG(c.puntuacion) FROM Calificacion c WHERE c.servicio = :servicio";
            return session.createQuery(hql, Double.class)
                    .setParameter("servicio", servicio)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular promedio", e);
        }
    }
}
