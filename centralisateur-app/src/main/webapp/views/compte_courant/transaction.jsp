<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="mb-4">
                <h2 class="fw-bold">Nouvelle transaction</h2>
                <p class="text-muted">Effectuez une opération sur le compte courant sélectionné.</p>
            </div>

            <!-- Summary card for the compte -->
            <div class="card mb-4 border-primary">
                <div class="card-header bg-primary text-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <small class="text-white-50">Compte</small>
                            <div class="fw-bold">
                                <c:choose>
                                    <c:when test="${not empty compte and not empty compte.numeroCompte}">
                                        ${compte.numeroCompte}
                                    </c:when>
                                    <c:otherwise>
                                        ${compte.compteId}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="text-end">
                            <small class="text-white-50">Solde</small>
                            <div class="fw-bold fs-5 text-white">
                                <c:choose>
                                    <c:when test="${not empty compte}">
                                        <fmt:formatNumber value="${compte.solde}" type="number" minFractionDigits="2" maxFractionDigits="2" /> Ariary
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <small class="text-muted">Découvert autorisé</small>
                            <div class="fw-bold">
                                <c:choose>
                                    <c:when test="${not empty compte}">
                                        <fmt:formatNumber value="${decouvertAutorise}" type="number" minFractionDigits="2" maxFractionDigits="2" /> Ariary
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="col-md-6 text-end">
                            <small class="text-muted">Date d'ouverture</small>
                            <div class="fw-bold">
                                <c:if test="${not empty compte}">
                                    <fmt:formatDate value="${compte.dateCreation}" pattern="dd/MM/yyyy"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Transaction form card -->
             <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success mt-3">${successMessage}</div>
            </c:if>
            <div class="card shadow-sm">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/compte-courant/transaction" method="post" class="needs-validation" novalidate>
                        <input type="hidden" name="compteId" value="${param.compteId}" />

                        <div class="mb-3">
                            <label for="type" class="form-label">Type de transaction</label>
                            <select name="typeOperationId" id="type" class="form-select" required>
                                <option value="">-- Choisir --</option>
                                <c:forEach var="entry" items="${typeOperations}">
                                    <option value="${entry.key}">${entry.value}</option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Veuillez choisir le type de transaction.</div>
                        </div>

                        <div class="mb-3">
                            <label for="montant" class="form-label">Montant</label>
                            <div class="input-group">
                                <input type="number" step="0.01" min="0.01" name="montant" id="montant" class="form-control" required />
                                <!-- Sélecteur de devise -->
                                <select name="devise" class="form-select" style="max-width: 120px;">
                                    <option value="">Devise</option>
                                    <c:forEach var="devise" items="${devises}">
                                        <option value="${devise}">${devise}</option>
                                    </c:forEach>
                                </select>
                                <select name="devise" class="form-select" style="max-width: 120px;">
                                    <option value="">Devise WebService</option>
                                    <c:forEach var="devise" items="${devisesApi}">
                                        <option value="${devise}">${devise}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-text text-muted">Entrez un montant positif. Une confirmation vous sera demandée si nécessaire.</div>
                            <div class="invalid-feedback">Veuillez saisir un montant valide et sélectionner une devise.</div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description (facultatif)</label>
                            <textarea name="description" id="description" class="form-control" rows="2" placeholder="Ex: Paiement loyer, retrait distributeur..."></textarea>
                        </div>

                        <div class="mb-3">
                            <label for="dateTransaction" class="form-label">Date de la transaction</label>
                            <input type="datetime-local" name="dateTransaction" id="dateTransaction" class="form-control"/>
                            <div class="form-text text-muted">Sélectionnez la date et l'heure de la transaction. Par défaut, la date actuelle sera utilisée.</div>
                        </div>

                        <div class="d-flex gap-2 justify-content-end">
                            <a href="${pageContext.request.contextPath}/compte-courant/list" class="btn btn-outline-secondary">Annuler</a>
                            <button type="submit" class="btn btn-success">Confirmer la transaction</button>
                        </div>
                    </form>
                </div>
            </div>

            
        </div>
    </div>
</div>

<script>
    // Bootstrap form validation
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>