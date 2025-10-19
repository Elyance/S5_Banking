<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <h2>Contrats de Prêt</h2>

    <c:if test="${empty contrats}">
        <div class="alert alert-info">
            Aucun contrat trouvé pour ce compte.
        </div>
    </c:if>

    <c:if test="${not empty contrats}">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID Contrat</th>
                        <th>Montant Emprunté</th>
                        <th>Date Début</th>
                        <th>Date Fin Théorique</th>
                        <th>Durée (Mois)</th>
                        <th>Mensualité</th>
                        <th>Montant Total à Rembourser</th>
                        <th>Montant Remboursé</th>
                        <th>Montant Restant à Rembourser</th>
                        <th>Taux d'Intérêt (%)</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="contrat" items="${contrats}">
                        <tr>
                            <td>${contrat.id}</td>
                            <td><fmt:formatNumber value="${contrat.montantEmprunte}" type="currency" currencySymbol="€"/></td>
                            <td>${contrat.dateDebutContrat}</td>
                            <td>${contrat.dateFinContrat}</td>
                            <td>${contrat.duree}</td>
                            <td><fmt:formatNumber value="${contrat.mensualite}" type="currency" currencySymbol="Ariary"/></td>
                            <td><fmt:formatNumber value="${contrat.montantTotalArembourser}" type="currency" currencySymbol="Ariary"/></td>
                            <td><fmt:formatNumber value="${contrat.montantRembourse}" type="currency" currencySymbol="Ariary"/></td>
                            <td><fmt:formatNumber value="${contrat.montantRestantDu}" type="currency" currencySymbol="Ariary"/></td>
                            <td><fmt:formatNumber value="${contrat.tauxInteret}" type="percent" maxFractionDigits="2"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/compte-pret/echeances?contratId=${contrat.id}&compteId=${compteId}" class="btn btn-primary btn-sm">
                                    Voir Échéances
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <div class="mt-3">
        <a href="${pageContext.request.contextPath}/client/situation" class="btn btn-secondary">Retour à la Situation</a>
    </div>
</div>