<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="mb-4 d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="fw-bold">Historique des transactions</h2>
                    <p class="text-muted">Compte : <c:out value="${compte.numeroCompte}"/></p>
                </div>
                <div class="text-end">
                    <a href="${pageContext.request.contextPath}/clients/situation?id=${compte.clientId}" class="btn btn-outline-secondary">Retour aux comptes</a>
                    <a href="${pageContext.request.contextPath}/compte-courant/transaction?compteId=${compte.id}" class="btn btn-success ms-2">Nouvelle transaction</a>
                </div>
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3">${errorMessage}</div>
            </c:if>
                    <div class="mb-3">
                        <strong>Solde actuel :</strong>
                        <c:if test="${not empty compte}">
                            <fmt:formatNumber value="${compte.solde}" type="number" minFractionDigits="2" maxFractionDigits="2" /> Ariary
                        </c:if>
                    </div>

                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Type</th>
                                <th>Montant</th>
                                <th>Solde avant</th>
                                <th>Solde après</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="t" items="${transactions}">
                                <tr>
                                    <td><c:out value="${t.dateTransactionFormatted}"/></td>
                                    <td><c:out value="${t.typeOperationLibelle}"/></td>
                                    <td><fmt:formatNumber value="${t.montant}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${t.soldeAvantTransaction}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber value="${t.soldeApresTransaction}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td><c:out value="${t.description}"/></td>
                                    <td>
                                        <c:if test="${not t.validated}">
                                            <form action="${pageContext.request.contextPath}/compte-courant/valider" method="post" style="display:inline;">
                                                <input type="hidden" name="transactionId" value="${t.id}" />
                                                <input type="hidden" name="compteId" value="${compte.id}" />
                                                <button type="submit" class="btn btn-primary btn-sm">Valider</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${t.validated}">
                                            <span class="text-success">Validée</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${empty transactions}">
                        <div class="alert alert-info">Aucune transaction trouvée pour ce compte.</div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
