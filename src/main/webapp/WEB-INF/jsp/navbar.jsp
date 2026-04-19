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
            <%-- Enlace a notificaciones con badge de no leídas --%>
            <a href="${pageContext.request.contextPath}/notificaciones"
               style="position:relative;display:inline-flex;align-items:center;gap:.3rem">
                🔔 Notificaciones
                <c:if test="${not empty sessionScope.notifNoLeidas and sessionScope.notifNoLeidas > 0}">
                    <span style="
                        background:var(--accent);
                        color:#fff;
                        font-size:.65rem;
                        font-weight:700;
                        padding:.1rem .4rem;
                        border-radius:999px;
                        line-height:1.4;
                        min-width:1.2rem;
                        text-align:center;
                    ">${sessionScope.notifNoLeidas}</span>
                </c:if>
            </a>
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
