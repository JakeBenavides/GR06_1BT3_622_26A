<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session != null && session.getAttribute("usuarioActual") != null) {
        response.sendRedirect(request.getContextPath() + "/home");
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
