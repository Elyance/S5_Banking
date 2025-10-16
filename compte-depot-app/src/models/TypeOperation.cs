using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

using System.ComponentModel.DataAnnotations.Schema;

namespace CompteDepotService.Models
{
    [Table("type_operation")]
    public class TypeOperation
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [MaxLength(50)]
        [Column("libelle")]
        public string Libelle { get; set; }

        public ICollection<Transaction> Transactions { get; set; }
    }
}
