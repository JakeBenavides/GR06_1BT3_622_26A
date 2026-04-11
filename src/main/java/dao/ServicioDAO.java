package dao;

import modelo.Servicio;
import modelo.Usuario;
import modelo.types.EstadoServicio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ServicioDAO {

    // 🔥 PUBLICAR (INSERT)
    public void guardar(Servicio servicio) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(servicio);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al guardar servicio", e);
        }
    }

    // 🔥 LISTAR SERVICIOS ACTIVOS
    public List<Servicio> listarActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Servicio s WHERE s.estado = :estado ORDER BY s.fechaPublicacionServicio DESC";

            return session.createQuery(hql, Servicio.class)
                    .setParameter("estado", EstadoServicio.ACTIVO)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar servicios activos", e);
        }
    }

    // 🔥 BUSCAR POR PALABRA CLAVE
    public List<Servicio> buscarPorPalabraClave(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Servicio s WHERE s.estado = :estado AND " +
                    "(lower(s.tituloServicio) LIKE :kw OR lower(s.descripcionServicio) LIKE :kw) " +
                    "ORDER BY s.fechaPublicacionServicio DESC";

            String patron = "%" + keyword.toLowerCase().trim() + "%";

            return session.createQuery(hql, Servicio.class)
                    .setParameter("estado", EstadoServicio.ACTIVO)
                    .setParameter("kw", patron)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar servicios", e);
        }
    }

    // 🔥 LISTAR POR USUARIO
    public List<Servicio> listarPorUsuario(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = "FROM Servicio s WHERE s.usuario = :usuario ORDER BY s.fechaPublicacionServicio DESC";

            return session.createQuery(hql, Servicio.class)
                    .setParameter("usuario", usuario)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error al listar servicios por usuario", e);
        }
    }

    // 🔥 BUSCAR POR ID
    public Optional<Servicio> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Servicio servicio = session.get(Servicio.class, id);
            return Optional.ofNullable(servicio);

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar servicio", e);
        }
    }

    // 🔥 ACTUALIZAR
    public void actualizar(Servicio servicio) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(servicio);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al actualizar servicio", e);
        }
    }

    // 🔥 ELIMINAR
    public void eliminar(Servicio servicio) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.remove(servicio);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error al eliminar servicio", e);
        }
    }
}