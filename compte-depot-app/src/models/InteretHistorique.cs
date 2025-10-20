using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("interet_historique")]
    public class InteretHistorique
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Column("transaction_id")]
        public long TransactionId { get; set; }

        [Column("montant_interet")]
        public decimal MontantInteret { get; set; }

        [Column("date_transaction")]
        public DateTime DateTransaction { get; set; }

        // Navigation property (optional)
        [ForeignKey("TransactionId")]
        public virtual Transaction? Transaction { get; set; }
    }
}