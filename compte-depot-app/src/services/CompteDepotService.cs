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

        // Déposer un montant sur un compte dépôt
        public Transaction Deposer(decimal montant, DateTime dateDepot, long compteDepotId)
        {
            // Récupérer le compte
            var compte = _context.ComptesDepot.FirstOrDefault(c => c.Id == compteDepotId);
            if (compte == null)
                throw new Exception($"Aucun compte_depot avec id={compteDepotId}");
            // Récupérer le type d'opération "DEPOT" (id=1)
            var typeOp = _context.TypeOperations.Find(1L);
            if (typeOp == null)
                throw new Exception("TypeOperation 'DEPOT' (id=1) introuvable");
            var soldeAvant = compte.Solde;
            var soldeApres = soldeAvant + montant;  //ajoutez les histoires de taux ici
            // Créer la transaction
            var transaction = new Transaction
            {
                TypeOperationId = typeOp.Id,
                CompteDepotId = compte.Id,
                Montant = montant,
                SoldeAvant = soldeAvant,
                SoldeApres = soldeApres,
                DateTransaction = DateTime.SpecifyKind(dateDepot, DateTimeKind.Utc)
            };
            // Mettre à jour le solde du compte
            compte.Solde = soldeApres;
            _context.Transactions.Add(transaction);
            _context.SaveChanges();
            return transaction;
        }

        public IEnumerable<CompteDepot> GetAllComptesDepot()
        {
            return _context.ComptesDepot.ToList();
        }

        // Crée un nouveau taux d'intérêt en base
        public TauxInteret CreateTauxInteret(TauxInteret tauxInteret)
        {
            if (tauxInteret == null) throw new ArgumentNullException(nameof(tauxInteret));
            if (tauxInteret.Valeur <= 0) throw new ArgumentException("La valeur du taux doit être strictement positive");

            if (tauxInteret.DateDebut == default(DateTime))
            {
                tauxInteret.DateDebut = DateTime.UtcNow;
            }
            // Ensure UTC kind for timestamptz compatibility
            tauxInteret.DateDebut = DateTime.SpecifyKind(tauxInteret.DateDebut, DateTimeKind.Utc);

            _context.TauxInterets.Add(tauxInteret);
            _context.SaveChanges();

            Console.WriteLine($"[DEBUG] TauxInteret créé Id={tauxInteret.Id}, Valeur={tauxInteret.Valeur}, DateDebut={tauxInteret.DateDebut}");
            return tauxInteret;
        }

        public TauxInteret GetTauxById(long id)
        {
            return _context.TauxInterets.Find(id);
        }

        public TauxInteret GetLatestTaux()
        {
            return _context.TauxInterets
                .OrderByDescending(t => t.DateDebut)
                .FirstOrDefault();
        }

        
    }
}
