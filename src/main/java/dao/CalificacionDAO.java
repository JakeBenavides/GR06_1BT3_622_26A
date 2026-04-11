package dao;

import modelo.Calificacion;
import modelo.Servicio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class CalificacionDAO {

    // 🔥 GUARDAR
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

    // 🔥 LISTAR POR SERVICIO
    public List<Calificacion> listarPorServicio(Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Calificacion c WHERE c.servicio = :servicio";

            return session.createQuery(hql, Calificacion.class)
                    .setParameter("servicio", servicio)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar calificaciones", e);
        }
    }

    // 🔥 PROMEDIO DE CALIFICACIONES
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