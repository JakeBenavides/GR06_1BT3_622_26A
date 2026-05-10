<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${servicio.tituloServicio} — PoliServis</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&family=Montserrat:wght@700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="hero-banner" style="min-height: auto; padding: 2rem;">
        <div class="hero-stripe"></div>
        <div class="epn-tag">${categoria.nombre}</div>
        <h1 style="font-size: 2.5rem;">${servicio.tituloServicio}</h1>
        <p style="font-size: 1.1rem; opacity: 0.9;">Publicado por 
            <a href="${pageContext.request.contextPath}/vendedor/perfil?id=${proveedor.idUsuario}" 
               style="color: white; text-decoration: underline; font-weight: 700;">
                ${proveedor.nombre}
            </a>
        </p>
    </div>

    <div class="grid" style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-top: 2rem;">
        
        <div class="main-content">
            <div class="card mb-3" style="padding: 0; overflow: hidden;">
                <c:choose>
                    <c:when test="${not empty servicio.fotoUrl}">
                        <img src="${pageContext.request.contextPath}/uploads/servicios/${servicio.fotoUrl}" 
                             alt="${servicio.tituloServicio}" style="width: 100%; max-height: 400px; object-fit: cover;">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/img/no-image.png" 
                             alt="Sin imagen" style="width: 100%; max-height: 300px; object-fit: cover; opacity: 0.5;">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="card">
                <div class="card-body">
                    <h2>Descripción</h2>
                    <p style="white-space: pre-line; line-height: 1.6;">${servicio.descripcionServicio}</p>
                </div>
            </div>
        </div>

        <div class="sidebar">
            <div class="card sticky" style="top: 2rem;">
                <div class="card-body">
                    <div class="precio-hero">$<fmt:formatNumber value="${servicio.precioServicio}" pattern="0.00"/></div>
                    <p class="text-muted small mb-3">Precio por el servicio completo</p>
                    <hr class="my-3">
                    <button class="btn btn-primary w-100 mb-2">Solicitar ahora</button>
                    <button class="btn btn-outline w-100">Contactar al vendedor</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
