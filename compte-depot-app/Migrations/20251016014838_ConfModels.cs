using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace compte_depot_app.Migrations
{
    /// <inheritdoc />
    public partial class ConfModels : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "TauxInterets",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Valeur = table.Column<decimal>(type: "numeric(5,3)", nullable: false),
                    DateDebut = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TauxInterets", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "TypeOperations",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Libelle = table.Column<string>(type: "character varying(50)", maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TypeOperations", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ComptesDepot",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    ClientId = table.Column<long>(type: "bigint", nullable: false),
                    NumeroCompte = table.Column<string>(type: "character varying(20)", maxLength: 20, nullable: false),
                    DateCreation = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    Solde = table.Column<decimal>(type: "numeric(15,2)", nullable: false),
                    TauxInteretId = table.Column<long>(type: "bigint", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ComptesDepot", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ComptesDepot_TauxInterets_TauxInteretId",
                        column: x => x.TauxInteretId,
                        principalTable: "TauxInterets",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "Transactions",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    TypeOperationId = table.Column<long>(type: "bigint", nullable: false),
                    CompteDepotId = table.Column<long>(type: "bigint", nullable: false),
                    Montant = table.Column<decimal>(type: "numeric(15,2)", nullable: false),
                    SoldeAvant = table.Column<decimal>(type: "numeric(15,2)", nullable: false),
                    SoldeApres = table.Column<decimal>(type: "numeric(15,2)", nullable: false),
                    DateTransaction = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Transactions", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Transactions_ComptesDepot_CompteDepotId",
                        column: x => x.CompteDepotId,
                        principalTable: "ComptesDepot",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Transactions_TypeOperations_TypeOperationId",
                        column: x => x.TypeOperationId,
                        principalTable: "TypeOperations",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "InteretHistoriques",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    TransactionId = table.Column<long>(type: "bigint", nullable: false),
                    MontantInteret = table.Column<decimal>(type: "numeric(15,2)", nullable: false),
                    DateTransaction = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_InteretHistoriques", x => x.Id);
                    table.ForeignKey(
                        name: "FK_InteretHistoriques_Transactions_TransactionId",
                        column: x => x.TransactionId,
                        principalTable: "Transactions",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ComptesDepot_TauxInteretId",
                table: "ComptesDepot",
                column: "TauxInteretId");

            migrationBuilder.CreateIndex(
                name: "IX_InteretHistoriques_TransactionId",
                table: "InteretHistoriques",
                column: "TransactionId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_Transactions_CompteDepotId",
                table: "Transactions",
                column: "CompteDepotId");

            migrationBuilder.CreateIndex(
                name: "IX_Transactions_TypeOperationId",
                table: "Transactions",
                column: "TypeOperationId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "InteretHistoriques");

            migrationBuilder.DropTable(
                name: "Transactions");

            migrationBuilder.DropTable(
                name: "ComptesDepot");

            migrationBuilder.DropTable(
                name: "TypeOperations");

            migrationBuilder.DropTable(
                name: "TauxInterets");
        }
    }
}
