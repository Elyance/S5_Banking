using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace compte_depot_app.Migrations
{
    /// <inheritdoc />
    public partial class ReInitializeBase : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ComptesDepot_TauxInterets_TauxInteretId",
                table: "ComptesDepot");

            migrationBuilder.DropForeignKey(
                name: "FK_InteretHistoriques_Transactions_TransactionId",
                table: "InteretHistoriques");

            migrationBuilder.DropForeignKey(
                name: "FK_Transactions_ComptesDepot_CompteDepotId",
                table: "Transactions");

            migrationBuilder.DropForeignKey(
                name: "FK_Transactions_TypeOperations_TypeOperationId",
                table: "Transactions");

            migrationBuilder.DropPrimaryKey(
                name: "PK_TypeOperations",
                table: "TypeOperations");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Transactions",
                table: "Transactions");

            migrationBuilder.DropPrimaryKey(
                name: "PK_TauxInterets",
                table: "TauxInterets");

            migrationBuilder.DropPrimaryKey(
                name: "PK_InteretHistoriques",
                table: "InteretHistoriques");

            migrationBuilder.DropPrimaryKey(
                name: "PK_ComptesDepot",
                table: "ComptesDepot");

            migrationBuilder.RenameTable(
                name: "TypeOperations",
                newName: "type_operation");

            migrationBuilder.RenameTable(
                name: "Transactions",
                newName: "transaction");

            migrationBuilder.RenameTable(
                name: "TauxInterets",
                newName: "taux_interet");

            migrationBuilder.RenameTable(
                name: "InteretHistoriques",
                newName: "interet_historique");

            migrationBuilder.RenameTable(
                name: "ComptesDepot",
                newName: "compte_depot");

            migrationBuilder.RenameIndex(
                name: "IX_Transactions_TypeOperationId",
                table: "transaction",
                newName: "IX_transaction_TypeOperationId");

            migrationBuilder.RenameIndex(
                name: "IX_Transactions_CompteDepotId",
                table: "transaction",
                newName: "IX_transaction_CompteDepotId");

            migrationBuilder.RenameIndex(
                name: "IX_InteretHistoriques_TransactionId",
                table: "interet_historique",
                newName: "IX_interet_historique_TransactionId");

            migrationBuilder.RenameIndex(
                name: "IX_ComptesDepot_TauxInteretId",
                table: "compte_depot",
                newName: "IX_compte_depot_TauxInteretId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_type_operation",
                table: "type_operation",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_transaction",
                table: "transaction",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_taux_interet",
                table: "taux_interet",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_interet_historique",
                table: "interet_historique",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_compte_depot",
                table: "compte_depot",
                column: "Id");

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

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
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

            migrationBuilder.DropPrimaryKey(
                name: "PK_type_operation",
                table: "type_operation");

            migrationBuilder.DropPrimaryKey(
                name: "PK_transaction",
                table: "transaction");

            migrationBuilder.DropPrimaryKey(
                name: "PK_taux_interet",
                table: "taux_interet");

            migrationBuilder.DropPrimaryKey(
                name: "PK_interet_historique",
                table: "interet_historique");

            migrationBuilder.DropPrimaryKey(
                name: "PK_compte_depot",
                table: "compte_depot");

            migrationBuilder.RenameTable(
                name: "type_operation",
                newName: "TypeOperations");

            migrationBuilder.RenameTable(
                name: "transaction",
                newName: "Transactions");

            migrationBuilder.RenameTable(
                name: "taux_interet",
                newName: "TauxInterets");

            migrationBuilder.RenameTable(
                name: "interet_historique",
                newName: "InteretHistoriques");

            migrationBuilder.RenameTable(
                name: "compte_depot",
                newName: "ComptesDepot");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_TypeOperationId",
                table: "Transactions",
                newName: "IX_Transactions_TypeOperationId");

            migrationBuilder.RenameIndex(
                name: "IX_transaction_CompteDepotId",
                table: "Transactions",
                newName: "IX_Transactions_CompteDepotId");

            migrationBuilder.RenameIndex(
                name: "IX_interet_historique_TransactionId",
                table: "InteretHistoriques",
                newName: "IX_InteretHistoriques_TransactionId");

            migrationBuilder.RenameIndex(
                name: "IX_compte_depot_TauxInteretId",
                table: "ComptesDepot",
                newName: "IX_ComptesDepot_TauxInteretId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_TypeOperations",
                table: "TypeOperations",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Transactions",
                table: "Transactions",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_TauxInterets",
                table: "TauxInterets",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_InteretHistoriques",
                table: "InteretHistoriques",
                column: "Id");

            migrationBuilder.AddPrimaryKey(
                name: "PK_ComptesDepot",
                table: "ComptesDepot",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ComptesDepot_TauxInterets_TauxInteretId",
                table: "ComptesDepot",
                column: "TauxInteretId",
                principalTable: "TauxInterets",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_InteretHistoriques_Transactions_TransactionId",
                table: "InteretHistoriques",
                column: "TransactionId",
                principalTable: "Transactions",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Transactions_ComptesDepot_CompteDepotId",
                table: "Transactions",
                column: "CompteDepotId",
                principalTable: "ComptesDepot",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Transactions_TypeOperations_TypeOperationId",
                table: "Transactions",
                column: "TypeOperationId",
                principalTable: "TypeOperations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
