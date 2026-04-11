package dao;

import modelo.Notificacion;
import modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class NotificacionDAO {

    // 🔥 GUARDAR
    public void guardar(Notificacion notificacion) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(notificacion);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al guardar notificación", e);
        }
    }

    // 🔥 LISTAR POR USUARIO
    public List<Notificacion> listarPorUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Notificacion n WHERE n.usuario = :usuario ORDER BY n.fechaNotificacion DESC";

            return session.createQuery(hql, Notificacion.class)
                    .setParameter("usuario", usuario)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar notificaciones", e);
        }
    }

    // 🔥 MARCAR COMO LEÍDA
    public void marcarLeida(Notificacion notificacion) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            notificacion.setLeida(true);
            session.merge(notificacion);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al actualizar notificación", e);
        }
    }
}