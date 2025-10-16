using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("taux_interet")]
    public class TauxInteret
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("valeur", TypeName = "decimal(5,3)")]
        public decimal Valeur { get; set; }

        [Required]
        [Column("date_debut")]
        public DateTime DateDebut { get; set; }

    }
}
