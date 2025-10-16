using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("compte_depot")]
    public class CompteDepot
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("client_id")]
        public long ClientId { get; set; }

        [MaxLength(20)]
        [Column("numero_compte")]
        public string? NumeroCompte { get; set; } // nullable

        [Column("date_creation")]
        public DateTime DateCreation { get; set; } = DateTime.UtcNow;

        [Column("solde", TypeName = "decimal(15,2)")]
        public decimal Solde { get; set; } = 0.00M;

        [Column("taux_interet_id")]
        public long? TauxInteretId { get; set; }

        [ForeignKey("TauxInteretId")]
        public TauxInteret? TauxInteret { get; set; } // nullable

        public ICollection<Transaction> Transactions { get; set; } = new List<Transaction>();
    }
}
