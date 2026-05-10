<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PoliServis — Servicios Estudiantiles EPN</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&family=Montserrat:wght@700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="container">
    <!-- Buscador -->
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/home" method="get">
            <div class="search-box">
                <input type="text" name="query" placeholder="¿Qué necesitas hoy? (Ej: Tutorías de Física, Diseño de logos...)" value="${param.query}">
                <button type="submit">Buscar</button>
            </div>
        </form>
    </div>

    <!-- Filtros de Categorías -->
    <div class="categories-nav">
        <a href="${pageContext.request.contextPath}/home" class="cat-pill ${empty param.categoria ? 'active' : ''}">Todos</a>
        <c:forEach var="cat" items="${categorias}">
            <a href="${pageContext.request.contextPath}/home?categoria=${cat.idCategoria}" 
               class="cat-pill ${param.categoria == cat.idCategoria ? 'active' : ''}">${cat.nombre}</a>
        </c:forEach>
    </div>

    <!-- Grid de Servicios -->
    <div class="grid-servicios">
        <c:forEach var="srv" items="${servicios}">
            <div class="card servicio-card">
                <div class="card-img-container" style="height: 160px; background: #eee; border-radius: 12px 12px 0 0; overflow: hidden;">
                    <c:choose>
                        <c:when test="${not empty srv.fotoUrl}">
                            <img src="${pageContext.request.contextPath}/uploads/servicios/${srv.fotoUrl}" 
                                 alt="${srv.tituloServicio}" style="width: 100%; height: 100%; object-fit: cover;">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/img/no-image.png" 
                                 alt="Sin imagen" style="width: 100%; height: 100%; object-fit: cover; opacity: 0.5;">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="card-body">
                    <div class="epn-tag small mb-1">${srv.categoria.nombre}</div>
                    <h3 class="card-title">${srv.tituloServicio}</h3>
                    <p class="text-muted small mb-2">Por: <strong>${srv.usuario.nombre}</strong></p>
                    <div class="card-footer">
                        <span class="precio">$<fmt:formatNumber value="${srv.precioServicio}" pattern="0.00"/></span>
                        <a href="${pageContext.request.contextPath}/servicio/detalle?id=${srv.idServicio}" class="btn btn-primary btn-sm">Ver más</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
