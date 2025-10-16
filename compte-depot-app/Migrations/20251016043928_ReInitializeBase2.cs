using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace compte_depot_app.Migrations
{
    /// <inheritdoc />
    public partial class ReInitializeBase2 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_compte_depot_taux_interet_TauxInteretId",
                table: "compte_depot");

            migrationBuilder.DropForeignKey(
                name: "FK_interet_historique_transaction_TransactionId",
                table: "interet_historique");

            migrationBuilder.DropForeignKey(
                name: "FK_transaction_compte_depot_CompteDepotId",
                table: "transaction");

            migrationBuilder.DropForeignKey(
                name: "FK_transaction_type_operation_TypeOperationId",
                table: "transaction");

            migrationBuilder.RenameColumn(
                name: "Libelle",
                table: "type_operation",
                newName: "libelle");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "type_operation",
                newName: "id");

            migrationBuilder.RenameColumn(
                name: "Montant",
                table: "transaction",
                newName: "montant");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "transaction",
                newName: "id");

            migrationBuilder.RenameColumn(
                name: "TypeOperationId",
                table: "transaction",
                newName: "type_operation_id");

            migrationBuilder.RenameColumn(
                name: "SoldeAvant",
                table: "transaction",
                newName: "solde_avant");

            migrationBuilder.RenameColumn(
                name: "SoldeApres",
                table: "transaction",
                newName: "solde_apres");

            migrationBuilder.RenameColumn(
                name: "DateTransaction",
                table: "transaction",
                newName: "date_transaction");

            migrationBuilder.RenameColumn(
                name: "CompteDepotId",
                table: "transaction",
                newName: "compte_depot_id");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_TypeOperationId",
                table: "transaction",
                newName: "IX_transaction_type_operation_id");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_CompteDepotId",
                table: "transaction",
                newName: "IX_transaction_compte_depot_id");

            migrationBuilder.RenameColumn(
                name: "Valeur",
                table: "taux_interet",
                newName: "valeur");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "taux_interet",
                newName: "id");

            migrationBuilder.RenameColumn(
                name: "DateDebut",
                table: "taux_interet",
                newName: "date_debut");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "interet_historique",
                newName: "id");

            migrationBuilder.RenameColumn(
                name: "TransactionId",
                table: "interet_historique",
                newName: "transaction_id");

            migrationBuilder.RenameColumn(
                name: "MontantInteret",
                table: "interet_historique",
                newName: "montant_interet");

            migrationBuilder.RenameColumn(
                name: "DateTransaction",
                table: "interet_historique",
                newName: "date_transaction");

            migrationBuilder.RenameIndex(
                name: "IX_interet_historique_TransactionId",
                table: "interet_historique",
                newName: "IX_interet_historique_transaction_id");

            migrationBuilder.RenameColumn(
                name: "Solde",
                table: "compte_depot",
                newName: "solde");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "compte_depot",
                newName: "id");

            migrationBuilder.RenameColumn(
                name: "TauxInteretId",
                table: "compte_depot",
                newName: "taux_interet_id");

            migrationBuilder.RenameColumn(
                name: "NumeroCompte",
                table: "compte_depot",
                newName: "numero_compte");

            migrationBuilder.RenameColumn(
                name: "DateCreation",
                table: "compte_depot",
                newName: "date_creation");

            migrationBuilder.RenameColumn(
                name: "ClientId",
                table: "compte_depot",
                newName: "client_id");

            migrationBuilder.RenameIndex(
                name: "IX_compte_depot_TauxInteretId",
                table: "compte_depot",
                newName: "IX_compte_depot_taux_interet_id");

            migrationBuilder.AddForeignKey(
                name: "FK_compte_depot_taux_interet_taux_interet_id",
                table: "compte_depot",
                column: "taux_interet_id",
                principalTable: "taux_interet",
                principalColumn: "id");

            migrationBuilder.AddForeignKey(
                name: "FK_interet_historique_transaction_transaction_id",
                table: "interet_historique",
                column: "transaction_id",
                principalTable: "transaction",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_transaction_compte_depot_compte_depot_id",
                table: "transaction",
                column: "compte_depot_id",
                principalTable: "compte_depot",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_transaction_type_operation_type_operation_id",
                table: "transaction",
                column: "type_operation_id",
                principalTable: "type_operation",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_compte_depot_taux_interet_taux_interet_id",
                table: "compte_depot");

            migrationBuilder.DropForeignKey(
                name: "FK_interet_historique_transaction_transaction_id",
                table: "interet_historique");

            migrationBuilder.DropForeignKey(
                name: "FK_transaction_compte_depot_compte_depot_id",
                table: "transaction");

            migrationBuilder.DropForeignKey(
                name: "FK_transaction_type_operation_type_operation_id",
                table: "transaction");

            migrationBuilder.RenameColumn(
                name: "libelle",
                table: "type_operation",
                newName: "Libelle");

            migrationBuilder.RenameColumn(
                name: "id",
                table: "type_operation",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "montant",
                table: "transaction",
                newName: "Montant");

            migrationBuilder.RenameColumn(
                name: "id",
                table: "transaction",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "type_operation_id",
                table: "transaction",
                newName: "TypeOperationId");

            migrationBuilder.RenameColumn(
                name: "solde_avant",
                table: "transaction",
                newName: "SoldeAvant");

            migrationBuilder.RenameColumn(
                name: "solde_apres",
                table: "transaction",
                newName: "SoldeApres");

            migrationBuilder.RenameColumn(
                name: "date_transaction",
                table: "transaction",
                newName: "DateTransaction");

            migrationBuilder.RenameColumn(
                name: "compte_depot_id",
                table: "transaction",
                newName: "CompteDepotId");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_type_operation_id",
                table: "transaction",
                newName: "IX_transaction_TypeOperationId");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_compte_depot_id",
                table: "transaction",
                newName: "IX_transaction_CompteDepotId");

            migrationBuilder.RenameColumn(
                name: "valeur",
                table: "taux_interet",
                newName: "Valeur");

            migrationBuilder.RenameColumn(
                name: "id",
                table: "taux_interet",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "date_debut",
                table: "taux_interet",
                newName: "DateDebut");

            migrationBuilder.RenameColumn(
                name: "id",
                table: "interet_historique",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "transaction_id",
                table: "interet_historique",
                newName: "TransactionId");

            migrationBuilder.RenameColumn(
                name: "montant_interet",
                table: "interet_historique",
                newName: "MontantInteret");

            migrationBuilder.RenameColumn(
                name: "date_transaction",
                table: "interet_historique",
                newName: "DateTransaction");

            migrationBuilder.RenameIndex(
                name: "IX_interet_historique_transaction_id",
                table: "interet_historique",
                newName: "IX_interet_historique_TransactionId");

            migrationBuilder.RenameColumn(
                name: "solde",
                table: "compte_depot",
                newName: "Solde");

            migrationBuilder.RenameColumn(
                name: "id",
                table: "compte_depot",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "taux_interet_id",
                table: "compte_depot",
                newName: "TauxInteretId");

            migrationBuilder.RenameColumn(
                name: "numero_compte",
                table: "compte_depot",
                newName: "NumeroCompte");

            migrationBuilder.RenameColumn(
                name: "date_creation",
                table: "compte_depot",
                newName: "DateCreation");

            migrationBuilder.RenameColumn(
                name: "client_id",
                table: "compte_depot",
                newName: "ClientId");

            migrationBuilder.RenameIndex(
                name: "IX_compte_depot_taux_interet_id",
                table: "compte_depot",
                newName: "IX_compte_depot_TauxInteretId");

            migrationBuilder.AddForeignKey(
                name: "FK_compte_depot_taux_interet_TauxInteretId",
                table: "compte_depot",
                column: "TauxInteretId",
                principalTable: "taux_interet",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_interet_historique_transaction_TransactionId",
                table: "interet_historique",
                column: "TransactionId",
                principalTable: "transaction",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_transaction_compte_depot_CompteDepotId",
                table: "transaction",
                column: "CompteDepotId",
                principalTable: "compte_depot",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_transaction_type_operation_TypeOperationId",
                table: "transaction",
                column: "TypeOperationId",
                principalTable: "type_operation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
