<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <h2>Échéances du Contrat</h2>

    <c:if test="${empty echeances}">
        <div class="alert alert-info">
            Aucune échéance trouvée pour ce contrat.
        </div>
    </c:if>

    <c:if test="${not empty echeances}">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Échéance</th>
                        <th>Date Échéance</th>
                        <th>Montant Capital</th>
                        <th>Montant Intérêt</th>
                        <th>Montant Total</th>
                        <th>Statut</th>
                        <th>Date de Paiement</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="echeance" items="${echeances}">
                        <tr>
                            <td>${echeance.id}</td>
                                                        <td>${echeance.dateEcheance}</td>
                            <td><fmt:formatNumber value="${echeance.montantCapital}" type="currency" currencySymbol="€"/></td>
                            <td><fmt:formatNumber value="${echeance.montantInteret}" type="currency" currencySymbol="€"/></td>
                            <td><fmt:formatNumber value="${echeance.montantTotal}" type="currency" currencySymbol="€"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${echeance.statutLibelle == 'PAYÉ'}">
                                        <span class="badge bg-success">${echeance.statutLibelle}</span>
                                    </c:when>
                                    <c:when test="${echeance.statutLibelle == 'EN ATTENTE'}">
                                        <span class="badge bg-warning">${echeance.statutLibelle}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${echeance.statutLibelle}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${echeance.datePaiement != null ? echeance.datePaiement : '-'}</td>
                            <td>
                                <c:if test="${echeance.statutLibelle != 'PAYÉ'}">
                                    <form method="post" action="${pageContext.request.contextPath}/compte-pret/payer-echeance" style="display: inline;">
                                        <input type="hidden" name="echeanceId" value="${echeance.id}">
                                        <input type="hidden" name="contratId" value="${param.contratId}">
                                        <input type="hidden" name="compteId" value="${compteId}">
                                        <button type="submit" class="btn btn-success btn-sm">Payer</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <div class="mt-3">
        <a href="${pageContext.request.contextPath}/compte-pret/contrats?compteId=${compteId}" class="btn btn-secondary">Retour aux Contrats</a>
    </div>
</div>