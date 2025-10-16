using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("transaction")]
    public class Transaction
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("type_operation_id")]
        public long TypeOperationId { get; set; }
        [ForeignKey("TypeOperationId")]
        public TypeOperation TypeOperation { get; set; }

        [Required]
        [Column("compte_depot_id")]
        public long CompteDepotId { get; set; }
        [ForeignKey("CompteDepotId")]
        public CompteDepot CompteDepot { get; set; }

        [Column("montant", TypeName = "decimal(15,2)")]
        public decimal Montant { get; set; }
        [Column("solde_avant", TypeName = "decimal(15,2)")]
        public decimal SoldeAvant { get; set; }
        [Column("solde_apres", TypeName = "decimal(15,2)")]
        public decimal SoldeApres { get; set; }

        [Column("date_transaction")]
        public DateTime DateTransaction { get; set; } = DateTime.Now;

        public InteretHistorique InteretHistorique { get; set; }
    }
}
