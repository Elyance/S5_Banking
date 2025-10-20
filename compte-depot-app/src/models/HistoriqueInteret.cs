using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("historique_interet")]
    public class HistoriqueInteret
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("compte_depot_id")]
        public long CompteDepotId { get; set; }

        [Column("date_debut")]
        public DateTime DateDebut { get; set; }

        [Column("date_fin")]
        public DateTime DateFin { get; set; }

        [Column("montant", TypeName = "decimal(15,2)")]
        public decimal Montant { get; set; }

        [Column("taux", TypeName = "decimal(5,2)")]
        public decimal Taux { get; set; }

        [Column("interet_calcule", TypeName = "decimal(15,2)")]
        public decimal InteretCalcule { get; set; }
    }
}