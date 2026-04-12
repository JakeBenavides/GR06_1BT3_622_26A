<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
        Poli<span class="brand-servis">Servis</span>
        <span class="epn-pill">EPN</span>
    </a>
    <div class="navbar-nav">
        <a href="${pageContext.request.contextPath}/home">Inicio</a>
        <a href="${pageContext.request.contextPath}/servicio/buscar">Buscar</a>
        <c:if test="${not empty sessionScope.usuarioActual}">
            <a href="${pageContext.request.contextPath}/servicio/publicar">Publicar servicio</a>
            <a href="${pageContext.request.contextPath}/servicio/mis-solicitudes">Mis solicitudes</a>
            <span class="text-muted" style="font-size:.85rem">
                Hola, <strong>${sessionScope.usuarioActual.nombre}</strong>
            </span>
            <form action="${pageContext.request.contextPath}/logout" method="post" style="margin:0">
                <button type="submit" class="btn btn-outline btn-sm">Cerrar sesión</button>
            </form>
        </c:if>
        <c:if test="${empty sessionScope.usuarioActual}">
            <a href="${pageContext.request.contextPath}/login">Iniciar sesión</a>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-accent btn-sm">Registrarse</a>
        </c:if>
    </div>
</nav>
