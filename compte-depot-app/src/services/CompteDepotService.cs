using CompteDepotService.Data;
using CompteDepotService.Models;
using Microsoft.EntityFrameworkCore;

namespace CompteDepotService.Services
{
    public class CompteDepotService
    {
        private readonly AppDbContext _context;

        public CompteDepotService(AppDbContext context)
        {
            _context = context;
        }

        public CompteDepot CreateCompteDepot(CompteDepot compteDepot, DateTime dateCreation)
        {
            Console.WriteLine("[DEBUG] Début création CompteDepot pour ClientId: " + compteDepot.ClientId);

            // Associer le taux d'intérêt applicable à la date de création
            var tauxApplicable = GetTauxByDate(dateCreation);
            if (tauxApplicable != null)
            {
                Console.WriteLine($"[DEBUG] Taux applicable trouvé: Id={tauxApplicable.Id}, Valeur={tauxApplicable.Valeur}, DateDebut={tauxApplicable.DateDebut}");
                compteDepot.TauxInteretId = tauxApplicable.Id;
                compteDepot.TauxInteret = tauxApplicable;
            }
            else
            {
                Console.WriteLine("[DEBUG] Aucun taux d'intérêt applicable trouvé pour la date !");
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

            // Calculer les intérêts pour la période précédente
            var lastTransaction = _context.Transactions
                .Where(t => t.CompteDepotId == compteDepotId)
                .OrderByDescending(t => t.DateTransaction)
                .FirstOrDefault();

            var tauxApplicable = GetTauxByDate(compte.DateCreation);
            Console.WriteLine($"[DEBUG] compte.DateCreation: {compte.DateCreation}");
            Console.WriteLine($"[DEBUG] tauxApplicable: {tauxApplicable?.Valeur ?? 0}");

            // Récupérer le type d'opération "DEPOT" (id=1)
            var typeOp = _context.TypeOperations.Find(1L);
            if (typeOp == null)
                throw new Exception("TypeOperation 'DEPOT' (id=1) introuvable");

            var soldeAvant = compte.Solde;
            var soldeApres = soldeAvant + montant;

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

            // Calculer les intérêts pour la période précédente et insérer dans interet_historique
            decimal montantInteret = 0;
            if (lastTransaction != null)
            {
                // Calculer la durée en jours
                var duree = (transaction.DateTransaction - lastTransaction.DateTransaction).TotalDays;
                Console.WriteLine($"[DEBUG] duree: {duree}");
                if (duree > 0 && tauxApplicable != null)
                {
                    decimal tauxAnnuel = tauxApplicable.Valeur; // en pourcentage, e.g. 5.0 pour 5%
                    decimal tauxParJour = tauxAnnuel / 100 / 365; // taux quotidien
                    montantInteret = lastTransaction.SoldeApres * tauxParJour * (decimal)duree;
                    Console.WriteLine($"[DEBUG] montantInteret: {montantInteret}");
                }
            }

            // Insérer dans interet_historique
            var interetHistorique = new InteretHistorique
            {
                TransactionId = transaction.Id,
                MontantInteret = montantInteret,
                DateTransaction = transaction.DateTransaction
            };
            _context.InteretHistoriques.Add(interetHistorique);
            _context.SaveChanges();

            // Ajouter l'intérêt au solde du compte
            if (montantInteret > 0)
            {
                compte.Solde += montantInteret;
                _context.SaveChanges();
            }

            return transaction;
        }

        // Retirer un montant d'un compte dépôt
        public Transaction Retirer(decimal montant, DateTime dateRetrait, long compteDepotId)
        {
            // Récupérer le compte
            var compte = _context.ComptesDepot.FirstOrDefault(c => c.Id == compteDepotId);
            if (compte == null)
                throw new Exception($"Aucun compte_depot avec id={compteDepotId}");

            // Calculer les intérêts pour la période précédente
            var lastTransaction = _context.Transactions
                .Where(t => t.CompteDepotId == compteDepotId)
                .OrderByDescending(t => t.DateTransaction)
                .FirstOrDefault();

            var tauxApplicable = GetTauxByDate(compte.DateCreation);
            Console.WriteLine($"[DEBUG] compte.DateCreation: {compte.DateCreation}");
            Console.WriteLine($"[DEBUG] tauxApplicable: {tauxApplicable?.Valeur ?? 0}");

            // Calculer les intérêts pour la période précédente
            decimal montantInteret = 0;
            if (lastTransaction != null)
            {
                // Calculer la durée en jours
                var duree = (dateRetrait - lastTransaction.DateTransaction).TotalDays;
                Console.WriteLine($"[DEBUG] duree: {duree}");
                if (duree > 0 && tauxApplicable != null)
                {
                    decimal tauxAnnuel = tauxApplicable.Valeur; // en pourcentage, e.g. 5.0 pour 5%
                    decimal tauxParJour = tauxAnnuel / 100 / 365; // taux quotidien
                    montantInteret = lastTransaction.SoldeApres * tauxParJour * (decimal)duree;
                    Console.WriteLine($"[DEBUG] montantInteret: {montantInteret}");
                }
            }

            // Ajouter l'intérêt au solde du compte avant le retrait
            if (montantInteret > 0)
            {
                compte.Solde += montantInteret;
                _context.SaveChanges();
            }

            // Vérifier le solde
            if (compte.Solde < montant)
                throw new Exception("Solde insuffisant pour le retrait");

            var soldeAvant = compte.Solde;
            var soldeApres = soldeAvant - montant;

            // Récupérer le type d'opération "RETRAIT" (id=2)
            var typeOp = _context.TypeOperations.Find(2L);
            if (typeOp == null)
                throw new Exception("TypeOperation 'RETRAIT' (id=2) introuvable");

            // Créer la transaction
            var transaction = new Transaction
            {
                TypeOperationId = typeOp.Id,
                CompteDepotId = compte.Id,
                Montant = montant,
                SoldeAvant = soldeAvant,
                SoldeApres = soldeApres,
                DateTransaction = DateTime.SpecifyKind(dateRetrait, DateTimeKind.Utc)
            };

            // Mettre à jour le solde du compte
            compte.Solde = soldeApres;
            _context.Transactions.Add(transaction);
            _context.SaveChanges();

            // Insérer dans interet_historique
            var interetHistorique = new InteretHistorique
            {
                TransactionId = transaction.Id,
                MontantInteret = montantInteret,
                DateTransaction = transaction.DateTransaction
            };
            _context.InteretHistoriques.Add(interetHistorique);
            _context.SaveChanges();

            return transaction;
        }

        public IEnumerable<object> GetAllComptesDepot()
        {
            var comptes = _context.ComptesDepot.ToList();
            var result = new List<object>();

            foreach (var compte in comptes)
            {
                var taux = GetTauxByDate(compte.DateCreation);
                result.Add(new
                {
                    compte.Id,
                    compte.NumeroCompte,
                    compte.ClientId,
                    compte.Solde,
                    compte.DateCreation,
                    compte.TauxInteretId,
                    TauxInteret = taux?.Valeur ?? 0
                });
            }

            return result;
        }

        public IEnumerable<object> GetComptesByClientId(long clientId)
        {
            var comptes = _context.ComptesDepot
                .Where(c => c.ClientId == clientId)
                .ToList();

            var result = new List<object>();

            foreach (var compte in comptes)
            {
                var taux = GetTauxByDate(compte.DateCreation);
                result.Add(new
                {
                    compte.Id,
                    compte.NumeroCompte,
                    compte.ClientId,
                    compte.Solde,
                    compte.DateCreation,
                    compte.TauxInteretId,
                    TauxInteret = taux?.Valeur ?? 0
                });
            }

            return result;
        }

        public IEnumerable<object> GetTransactionHistoryWithInterests(long compteDepotId)
        {
            var transactions = _context.Transactions
                .Include(t => t.TypeOperation)
                .Where(t => t.CompteDepotId == compteDepotId)
                .OrderBy(t => t.DateTransaction)
                .ToList();

            var result = new List<object>();

            foreach (var transaction in transactions)
            {
                var interet = _context.InteretHistoriques
                    .FirstOrDefault(i => i.TransactionId == transaction.Id);

                decimal montantInteret = interet?.MontantInteret ?? 0;

                // Add transaction entry
                result.Add(new
                {
                    Type = "transaction",
                    Id = transaction.Id,
                    TypeOperationLibelle = transaction.TypeOperation?.Libelle ?? "Unknown",
                    Montant = transaction.Montant,
                    SoldeAvant = transaction.SoldeAvant,
                    SoldeApres = transaction.SoldeApres,
                    DateTransaction = transaction.DateTransaction
                });

                // Add interest entry if > 0
                if (montantInteret > 0)
                {
                    result.Add(new
                    {
                        Type = "interest",
                        Id = transaction.Id,
                        TypeOperationLibelle = "Intérêt",
                        Montant = montantInteret,
                        SoldeAvant = transaction.SoldeApres,
                        SoldeApres = transaction.SoldeApres + montantInteret,
                        DateTransaction = transaction.DateTransaction
                    });
                }
            }

            return result;
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
                .OrderByDescending(static t => t.DateDebut)
                .FirstOrDefault();
        }

        public TauxInteret GetTauxByDate(DateTime date)
        {
            return _context.TauxInterets
                .Where(t => t.DateDebut <= date)
                .OrderByDescending(t => t.DateDebut)
                .FirstOrDefault();

        }
    }
}
