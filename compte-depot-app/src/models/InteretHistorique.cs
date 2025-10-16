using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("interet_historique")]
    public class InteretHistorique
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("transaction_id")]
        public long TransactionId { get; set; }
        [ForeignKey("TransactionId")]
        public Transaction Transaction { get; set; }

        [Column("montant_interet", TypeName = "decimal(15,2)")]
        public decimal MontantInteret { get; set; }

        [Column("date_transaction")]
        public DateTime DateTransaction { get; set; } = DateTime.Now;
    }
}
