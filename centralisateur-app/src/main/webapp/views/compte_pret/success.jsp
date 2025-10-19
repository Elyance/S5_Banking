<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-12">
        <div class="text-center py-5">
            <div class="card">
                <div class="card-body">
                    <i class="fas fa-check-circle fa-4x text-success mb-4"></i>
                    <h2 class="text-success mb-3">Opération réussie !</h2>
                    <p class="lead mb-4">
                        <c:choose>
                            <c:when test="${not empty successMessage}">
                                ${successMessage}
                            </c:when>
                            <c:otherwise>
                                L'opération a été effectuée avec succès.
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <div class="d-flex justify-content-center gap-3">
                        <a href="${pageContext.request.contextPath}/compte-pret/list" class="btn btn-primary">
                            <i class="fas fa-list me-2"></i>
                            Voir les comptes
                        </a>
                        <a href="${pageContext.request.contextPath}/clients" class="btn btn-secondary">
                            <i class="fas fa-arrow-left me-2"></i>
                            Retour à la liste des clients
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>