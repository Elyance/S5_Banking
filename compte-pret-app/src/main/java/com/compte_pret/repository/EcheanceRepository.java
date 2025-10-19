package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.Echeance;

import java.util.List;
import com.compte_pret.dto.EcheanceDTO;


@ApplicationScoped
public class EcheanceRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    public void save(Echeance echeance) {
        em.persist(echeance);
    }

    public Echeance findById(Long id) {
        return em.find(Echeance.class, id);
    }


    // Return List<Echeance> for calculations
    public List<Echeance> findByContratPretId(Long contratPretId) {
        return em.createQuery("SELECT e FROM Echeance e WHERE e.contratPret.id = :contratPretId ORDER BY e.dateEcheance", Echeance.class)
                 .setParameter("contratPretId", contratPretId)
                 .getResultList();
    }

    // Return List<EcheanceDTO> with native query
    @SuppressWarnings("unchecked")
    public List<EcheanceDTO> findEcheancesDTOByContratPretId(Long contratPretId) {
        String sql = "SELECT \n" + //
                        "    e.id,\n" + //
                        "    TO_CHAR(e.date_echeance, 'YYYY-MM-DD') as date_echeance,\n" + //
                        "    e.montant_capital,\n" + //
                        "    e.montant_interet,\n" + //
                        "    COALESCE(se.libelle, 'INCONNU') as statut_libelle,\n" + //
                        "    TO_CHAR(mse_paye.date_changement, 'YYYY-MM-DD') as date_paiement\n" + //
                        "FROM echeances e\n" + //
                        "LEFT JOIN mvt_statut_echeance mse ON e.id = mse.echeance_id \n" + //
                        "    AND mse.id = (SELECT MAX(mse2.id) FROM mvt_statut_echeance mse2 WHERE mse2.echeance_id = e.id)\n" + //
                        "LEFT JOIN statut_echeance se ON mse.statut_echeance_id = se.id\n" + //
                        "LEFT JOIN mvt_statut_echeance mse_paye ON e.id = mse_paye.echeance_id \n" + //
                        "    AND mse_paye.statut_echeance_id = 2\n" + //
                        "    AND mse_paye.id = (SELECT MIN(mse3.id) FROM mvt_statut_echeance mse3 WHERE mse3.echeance_id = e.id AND mse3.statut_echeance_id = 2)\n" + //
                        "WHERE e.contrat_pret_id = :contratPretId\n" + //
                        "ORDER BY e.date_echeance";
        return em.createNativeQuery(sql, EcheanceDTO.class)
                 .setParameter("contratPretId", contratPretId)
                 .getResultList();
    }
}
