<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card mt-4">
                <div class="card-body">
                    <h4 class="card-title mb-3">Ajouter un nouveau découvert autorisé</h4>
                    <form action="${pageContext.request.contextPath}/compte-courant/decouvert" method="post">
                        <div class="mb-3">
                            <label for="montant" class="form-label">Montant (Ariary)</label>
                            <input type="number" step="0.01" min="0" id="montant" name="montant" class="form-control" required />
                        </div>
                        <div class="mb-3">
                            <label for="dateEffective" class="form-label">Date effective</label>
                            <input type="datetime-local" id="dateEffective" name="dateEffective" class="form-control" required />
                        </div>
                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
