<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="jakarta.json.Json" %>
<%@ page import="jakarta.json.JsonArray" %>
<%@ page import="jakarta.json.JsonObject" %>
<%@ page import="jakarta.json.JsonReader" %>
<%@ page import="java.io.StringReader" %>
<%@ page import="java.math.BigDecimal" %>

<c:set var="pageTitle" value="Détails du compte dépôt" />

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header bg-info text-white">
                    <h4 class="mb-0">
                        <i class="fas fa-history me-2"></i>
                        Historique des transactions - Compte ${compteId}
                    </h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>Date</th>
                                    <th>Type</th>
                                    <th>Montant</th>
                                    <th>Solde Avant</th>
                                    <th>Solde Après</th>
                                    <th>Intérêts</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    String historyJson = (String) request.getAttribute("historyJson");
                                    if (historyJson != null && !historyJson.isEmpty()) {
                                        try (JsonReader reader = Json.createReader(new StringReader(historyJson))) {
                                            jakarta.json.JsonArray arr = reader.readArray();
                                            for (jakarta.json.JsonValue v : arr) {
                                                if (v.getValueType() == jakarta.json.JsonValue.ValueType.OBJECT) {
                                                    JsonObject obj = v.asJsonObject();
                                                    String type = obj.getString("type", "");
                                                    String date = obj.getString("dateTransaction", "");
                                                    String typeOp = obj.getString("typeOperationLibelle", "");
                                                    BigDecimal montant = obj.getJsonNumber("montant").bigDecimalValue();
                                                    BigDecimal soldeAvant = obj.getJsonNumber("soldeAvant").bigDecimalValue();
                                                    BigDecimal soldeApres = obj.getJsonNumber("soldeApres").bigDecimalValue();
                                                    
                                                    String rowClass = "transaction".equals(type) ? "" : "table-info";
                                %>
                                <tr class="<%= rowClass %>">
                                    <td><%= date %></td>
                                    <td><%= typeOp %></td>
                                    <td><%= montant %> Ariary</td>
                                    <td><%= soldeAvant %> Ariary</td>
                                    <td><%= soldeApres %> Ariary</td>
                                    <td><%= "interest".equals(type) ? "Intérêt calculé" : "-" %></td>
                                </tr>
                                <%
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                %>
                                <tr>
                                    <td colspan="6" class="text-danger">Erreur lors du parsing des données</td>
                                </tr>
                                <%
                                        }
                                    } else {
                                %>
                                <tr>
                                    <td colspan="6" class="text-muted">Aucune transaction trouvée</td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/compte-depot/transaction?compteId=${compteId}" class="btn btn-primary me-2">
                            <i class="fas fa-plus me-2"></i>
                            Nouvelle transaction
                        </a>
                        <a href="${pageContext.request.contextPath}/compte-depot/list" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-2"></i>
                            Retour à la liste
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>