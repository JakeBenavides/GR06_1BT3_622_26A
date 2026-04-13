package servlet.auth;

import dao.UsuarioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import modelo.Usuario;
import modelo.types.Rol;

import java.io.IOException;

@WebServlet("/register")
public class RegistroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioActual") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre     = request.getParameter("nombre")     != null ? request.getParameter("nombre").trim()     : "";
        String correo     = request.getParameter("correo")     != null ? request.getParameter("correo").trim()     : "";
        String contrasena = request.getParameter("contrasena") != null ? request.getParameter("contrasena").trim() : "";
        String confirmar  = request.getParameter("confirmar")  != null ? request.getParameter("confirmar").trim()  : "";

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        if (!contrasena.equals(confirmar)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.setAttribute("nombre", nombre);
            request.setAttribute("correo", correo);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        if (contrasena.length() < 6) {
            request.setAttribute("error", "La contraseña debe tener al menos 6 caracteres.");
            request.setAttribute("nombre", nombre);
            request.setAttribute("correo", correo);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        if (usuarioDAO.existeCorreo(correo)) {
            request.setAttribute("error", "El correo ya está registrado.");
            request.setAttribute("nombre", nombre);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new Usuario(nombre, correo, contrasena, Rol.ESTUDIANTE);
        usuarioDAO.guardar(usuario);

        HttpSession session = request.getSession();
        session.setAttribute("usuarioActual", usuario);
        session.setAttribute("rolActual", usuario.getRol().name());

        response.sendRedirect(request.getContextPath() + "/home");
    }
}
