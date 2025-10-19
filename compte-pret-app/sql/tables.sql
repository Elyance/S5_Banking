CREATE DATABASE compte_pret_db;
\c compte_pret_db;


CREATE TABLE taux_interet (
    id BIGSERIAL PRIMARY KEY,
    valeur DECIMAL(5,2) NOT NULL CHECK (valeur >= 0),
    date_debut TIMESTAMP WITH TIME ZONE,
    UNIQUE (date_debut)
);

-- Table des types de paiement (fréquence de remboursement)
CREATE TABLE type_paiement (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    valeur INTEGER NOT NULL CHECK (valeur > 0)
);

-- Table des statuts de compte prêt
CREATE TABLE statut_compte_pret (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE compte_pret (
    id BIGSERIAL PRIMARY KEY,
    numero_compte VARCHAR(30) UNIQUE NOT NULL,
    client_id BIGINT NOT NULL, -- Référence vers centralisateur (pas de FK)
    
    solde_restant_du DECIMAL(15,2) NOT NULL CHECK (solde_restant_du >= 0),
    date_creation TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
    
);

CREATE TABLE mvt_statut_compte_pret (
    id BIGSERIAL PRIMARY KEY,
    compte_pret_id BIGINT NOT NULL REFERENCES compte_pret(id) ON DELETE CASCADE,
    statut_compte_pret_id BIGINT NOT NULL REFERENCES statut_compte_pret(id),
    date_changement TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contrat_pret (
    id BIGSERIAL PRIMARY KEY,
    compte_pret_id BIGINT NOT NULL REFERENCES compte_pret(id) ON DELETE CASCADE,
    
    -- Détails du prêt
    montant_emprunte DECIMAL(15,2) NOT NULL CHECK (montant_emprunte > 0),
    date_debut DATE NOT NULL,
    duree_totale_mois INTEGER NOT NULL CHECK (duree_totale_mois > 0),
    type_paiement_id BIGINT NOT NULL REFERENCES type_paiement(id),

    date_fin_theorique DATE NOT NULL
);

CREATE TABLE echeances (
    id BIGSERIAL PRIMARY KEY,
    contrat_pret_id BIGINT NOT NULL REFERENCES contrat_pret(id) ON DELETE CASCADE,
    
    -- Informations de l'échéance
    date_echeance DATE NOT NULL,
    montant_capital DECIMAL(12,2) NOT NULL CHECK (montant_capital >= 0),
    montant_interet DECIMAL(12,2) NOT NULL CHECK (montant_interet >= 0)
);

-- paye ou non paye
CREATE TABLE statut_echeance (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE mvt_statut_echeance (
    id BIGSERIAL PRIMARY KEY,
    echeance_id BIGINT NOT NULL REFERENCES echeances(id) ON DELETE CASCADE,
    statut_echeance_id BIGINT NOT NULL REFERENCES statut_echeance(id),
    date_changement TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Statuts de compte prêt
INSERT INTO statut_compte_pret (libelle) VALUES
  ('ACTIF'),
  ('SUSPENDU'),
  ('INACTIF');

-- Statuts d’échéance
INSERT INTO statut_echeance (libelle) VALUES
  ('PAYÉ'),
  ('NON PAYÉ');

  INSERT INTO type_paiement (libelle, valeur) VALUES
  ('Mensuel', 1),
  ('Trimestriel', 3),
  ('Semestriel', 6),
  ('Annuel', 12);
