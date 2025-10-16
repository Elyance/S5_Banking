using CompteDepotService.Data;
using CompteDepotService.Models;

namespace CompteDepotService.Services
{
    public class CompteDepotService
    {
        private readonly AppDbContext _context;

        public CompteDepotService(AppDbContext context)
        {
            _context = context;
        }

        public CompteDepot CreateCompteDepot(CompteDepot compteDepot)
        {
            Console.WriteLine("[DEBUG] Début création CompteDepot pour ClientId: " + compteDepot.ClientId);

            // Associer le dernier taux d'intérêt (le plus récent)
            var dernierTaux = _context.TauxInterets
                .OrderByDescending(t => t.DateDebut)
                .FirstOrDefault();
            if (dernierTaux != null)
            {
                Console.WriteLine($"[DEBUG] Dernier taux trouvé: Id={dernierTaux.Id}, Valeur={dernierTaux.Valeur}, DateDebut={dernierTaux.DateDebut}");
                compteDepot.TauxInteretId = dernierTaux.Id;
                compteDepot.TauxInteret = dernierTaux;
            }
            else
            {
                Console.WriteLine("[DEBUG] Aucun taux d'intérêt trouvé en base !");
            }

            _context.ComptesDepot.Add(compteDepot);
            Console.WriteLine("[DEBUG] CompteDepot ajouté au contexte, SaveChanges...");
            _context.SaveChanges();

            // Générer le numéro de compte au format CD00001
            compteDepot.NumeroCompte = $"CD{compteDepot.Id.ToString().PadLeft(5, '0')}";
            Console.WriteLine($"[DEBUG] Numéro de compte généré: {compteDepot.NumeroCompte}");
            _context.SaveChanges();

            Console.WriteLine($"[DEBUG] CompteDepot créé avec Id={compteDepot.Id}, NumeroCompte={compteDepot.NumeroCompte}, Solde={compteDepot.Solde}");
            return compteDepot;
        }
    }
}
