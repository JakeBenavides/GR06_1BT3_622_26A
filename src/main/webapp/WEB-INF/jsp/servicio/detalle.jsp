<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${servicio.tituloServicio} — PoliServis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <a href="${pageContext.request.contextPath}/home" class="text-muted">← Volver al inicio</a>
    </div>

    <div style="display:grid;grid-template-columns:2fr 1fr;gap:1.5rem;align-items:start">

        <%-- Detalle principal --%>
        <div class="card">
            <div class="card-body">
                <c:if test="${not empty categoria}">
                    <span class="badge badge-primary mb-2">${categoria.nombre}</span>
                </c:if>
                <h1 class="card-title" style="font-size:1.5rem">${servicio.tituloServicio}</h1>
                <p class="text-muted mt-1">
                    Publicado el:
                    <fmt:formatDate value="${servicio.fechaPublicacionServicio}" pattern="dd/MM/yyyy"/>
                </p>

                <hr style="border:none;border-top:1px solid var(--border);margin:1rem 0">

                <h3 style="font-size:1rem;margin-bottom:.5rem">Descripción</h3>
                <p style="line-height:1.7">${servicio.descripcionServicio}</p>
            </div>
        </div>

        <%-- Panel lateral --%>
        <div>
            <div class="card mb-2">
                <div class="card-body" style="text-align:center">
                    <p class="text-muted" style="font-size:.8rem;margin-bottom:.25rem">PRECIO</p>
                    <p class="precio" style="font-size:2rem">
                        $<fmt:formatNumber value="${servicio.precioServicio}" pattern="0.00"/>
                    </p>
                    <c:if test="${sessionScope.usuarioActual.idUsuario != servicio.usuario.idUsuario}">
                        <a href="#" class="btn btn-primary btn-full mt-2">Solicitar servicio</a>
                    </c:if>
                    <c:if test="${sessionScope.usuarioActual.idUsuario == servicio.usuario.idUsuario}">
                        <p class="text-muted mt-2" style="font-size:.85rem">Este es tu servicio</p>
                    </c:if>
                </div>
            </div>

            <c:if test="${not empty proveedor}">
                <div class="card">
                    <div class="card-body">
                        <h3 style="font-size:.95rem;margin-bottom:.5rem">Proveedor</h3>
                        <p class="fw-bold">${proveedor.nombre}</p>
                        <p class="text-muted">${proveedor.correo}</p>
                        <c:if test="${not empty proveedor.descripcionPerfil}">
                            <p class="mt-1" style="font-size:.87rem">${proveedor.descripcionPerfil}</p>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </div>

    </div>

    <div style="height:3rem"></div>
</div>

</body>
</html>
