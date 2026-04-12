package dao;

import modelo.Solicitud;
import modelo.Usuario;
import modelo.Servicio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class SolicitudDAO {

    // GUARDAR - registrarSolicitud() del diagrama de secuencia
    public void guardar(Solicitud solicitud) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(solicitud);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al guardar solicitud", e);
        }
    }

    // LISTAR POR USUARIO - para la pantalla "Mis servicios solicitados"
    public List<Solicitud> listarPorUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Solicitud s WHERE s.usuario = :usuario ORDER BY s.fechaSolicitud DESC";
            return session.createQuery(hql, Solicitud.class)
                    .setParameter("usuario", usuario)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar solicitudes por usuario", e);
        }
    }

    // LISTAR POR SERVICIO
    public List<Solicitud> listarPorServicio(Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Solicitud s WHERE s.servicio = :servicio";
            return session.createQuery(hql, Solicitud.class)
                    .setParameter("servicio", servicio)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar solicitudes por servicio", e);
        }
    }

    // VERIFICAR SI YA EXISTE - evita solicitudes duplicadas (ControlValidacion del diagrama)
    public boolean existeSolicitud(Usuario usuario, Servicio servicio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(s) FROM Solicitud s WHERE s.usuario = :usuario AND s.servicio = :servicio";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("usuario", usuario)
                    .setParameter("servicio", servicio)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar solicitud", e);
        }
    }

    // BUSCAR POR ID
    public Optional<Solicitud> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Solicitud.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar solicitud", e);
        }
    }
}
