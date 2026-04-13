package servlet.Servicio;

import dao.ServicioDAO;
import dao.CategoriaDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import modelo.Servicio;

import java.io.IOException;
import java.util.List;

@WebServlet("/servicio/buscar")
public class BuscarServicioServlet extends HttpServlet {

    private final ServicioDAO servicioDAO = new ServicioDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("q") != null ? req.getParameter("q").trim() : "";

        List<Servicio> resultados = keyword.isEmpty()
                ? servicioDAO.listarActivos()
                : servicioDAO.buscarPorPalabraClave(keyword);

        req.setAttribute("resultados", resultados);
        req.setAttribute("keyword", keyword);
        req.setAttribute("categorias", categoriaDAO.listarTodas());
        req.setAttribute("totalResultados", resultados.size());

        req.getRequestDispatcher("/WEB-INF/jsp/servicio/buscar.jsp").forward(req, resp);
    }
}
