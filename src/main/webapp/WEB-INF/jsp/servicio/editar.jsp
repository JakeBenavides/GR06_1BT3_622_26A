<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar servicio — PoliServis</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&family=Montserrat:wght@700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<%@ include file="../navbar.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Editar servicio</h1>
    </div>

    <div class="card" style="max-width:640px">
        <div class="card-body">

            <c:if test="${not empty sessionScope.mensajeError}">
                <div class="alert alert-danger">${sessionScope.mensajeError}</div>
                <c:remove var="mensajeError" scope="session" />
            </c:if>

            <form action="${pageContext.request.contextPath}/servicio/editar" method="post" enctype="multipart/form-data" novalidate>
                <input type="hidden" name="idServicio" value="${servicio.idServicio}">

                <div class="form-group">
                    <label for="titulo">Título del servicio *</label>
                    <input type="text" id="titulo" name="titulo" class="form-control"
                           value="${servicio.tituloServicio}" required maxlength="100">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripción *</label>
                    <textarea id="descripcion" name="descripcion" class="form-control"
                               required>${servicio.descripcionServicio}</textarea>
                </div>

                <div class="form-group">
                    <label for="precio">Precio (USD) *</label>
                    <input type="number" id="precio" name="precio" class="form-control"
                           value="${servicio.precioServicio}" min="0" step="0.50" required>
                </div>

                <div class="form-group">
                    <label for="idCategoria">Categoría *</label>
                    <select id="idCategoria" name="idCategoria" class="form-control" required>
                        <c:forEach var="cat" items="${categorias}">
                            <option value="${cat.idCategoria}" ${cat.idCategoria == servicio.categoria.idCategoria ? 'selected' : ''}>
                                ${cat.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Imagen actual</label>
                    <div style="margin-bottom: 1rem;">
                        <c:choose>
                            <c:when test="${not empty servicio.fotoUrl}">
                                <img src="${pageContext.request.contextPath}/uploads/servicios/${servicio.fotoUrl}" 
                                     alt="Actual" style="width: 150px; height: 100px; object-fit: cover; border-radius: 8px;">
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted">No tiene imagen cargada.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <label for="foto">Reemplazar imagen (JPG o PNG, máx 2MB)</label>
                    <input type="file" id="foto" name="foto" class="form-control" accept="image/jpeg,image/png">
                </div>

                <div class="form-group">
                    <label for="estado">Estado del servicio *</label>
                    <select id="estado" name="estado" class="form-control" required>
                        <option value="ACTIVO" ${servicio.estado == 'ACTIVO' ? 'selected' : ''}>Activo (Visible)</option>
                        <option value="INACTIVO" ${servicio.estado == 'INACTIVO' ? 'selected' : ''}>Inactivo (Oculto)</option>
                    </select>
                </div>

                <div class="d-flex gap-1 mt-3">
                    <button type="submit" class="btn btn-primary">Guardar cambios</button>
                    <a href="${pageContext.request.contextPath}/servicio/mis-servicios" class="btn btn-outline">Cancelar</a>
                </div>

            </form>
        </div>
    </div>
</div>

</body>
</html>
