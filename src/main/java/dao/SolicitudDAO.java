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

    // 🔥 GUARDAR
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

    // 🔥 LISTAR TODAS
    public List<Solicitud> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Solicitud s ORDER BY s.fechaSolicitud DESC";

            return session.createQuery(hql, Solicitud.class).getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar solicitudes", e);
        }
    }

    // 🔥 LISTAR POR USUARIO
    public List<Solicitud> listarPorUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Solicitud s WHERE s.usuario = :usuario";

            return session.createQuery(hql, Solicitud.class)
                    .setParameter("usuario", usuario)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar solicitudes por usuario", e);
        }
    }

    // 🔥 LISTAR POR SERVICIO
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

    // 🔥 BUSCAR POR ID
    public Optional<Solicitud> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return Optional.ofNullable(session.get(Solicitud.class, id));

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar solicitud", e);
        }
    }
}