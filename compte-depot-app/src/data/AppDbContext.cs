using Microsoft.EntityFrameworkCore;

namespace CompteDepotService.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    public DbSet<CompteDepotService.Models.TypeOperation> TypeOperations { get; set; }
    public DbSet<CompteDepotService.Models.TauxInteret> TauxInterets { get; set; }
    public DbSet<CompteDepotService.Models.CompteDepot> ComptesDepot { get; set; }
    public DbSet<CompteDepotService.Models.Transaction> Transactions { get; set; }
    public DbSet<CompteDepotService.Models.InteretHistorique> InteretHistoriques { get; set; }
    }
}
