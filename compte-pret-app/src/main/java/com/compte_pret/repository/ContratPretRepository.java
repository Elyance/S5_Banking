package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.ContratPret;

import java.util.List;
import com.compte_pret.dto.ContratPretDTO;


@ApplicationScoped
public class ContratPretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    // save
    public void save(ContratPret contratPret) {
        em.persist(contratPret);
    }

    // find contrats by comptePretId
    @SuppressWarnings("unchecked")
    public List<ContratPretDTO> findByComptePretId(Long comptePretId) {
        String sql = "SELECT \n" + //
                        "    cp.id,\n" + //
                        "    cp.compte_pret_id,\n" + //
                        "    cpt.numero_compte as compte_pret_numero,\n" + //
                        "    cp.duree_totale_mois as duree,\n" + //
                        "    cp.montant_emprunte,\n" + //
                        "    cpt.solde_restant_du as montant_restant_du,\n" + //
                        "    tp.id as type_paiement_id,\n" + //
                        "    tp.libelle as type_paiement_libelle,\n" + //
                        "    TO_CHAR(cp.date_debut, 'YYYY-MM-DD') as date_debut_contrat,\n" + //
                        "    TO_CHAR(cp.date_fin_theorique, 'YYYY-MM-DD') as date_fin_contrat\n" + //
                        "\n" + //
                        "FROM contrat_pret cp\n" + //
                        "JOIN compte_pret cpt ON cp.compte_pret_id = cpt.id\n" + //
                        "JOIN type_paiement tp ON cp.type_paiement_id = tp.id\n" + //
                        "WHERE cp.compte_pret_id = :comptePretId\n" + //
                        "ORDER BY cp.id";
        return em.createNativeQuery(sql, ContratPretDTO.class)
                 .setParameter("comptePretId", comptePretId)
                 .getResultList();
    }
}
