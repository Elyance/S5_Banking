-- Création de la base de données
CREATE DATABASE compte_depot_db;
\c compte_depot_db;

-- Table des types d'opération (basique)
CREATE TABLE type_operation (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

-- Table des taux d'intérêt
CREATE TABLE taux_interet (
    id BIGSERIAL PRIMARY KEY,
    valeur DECIMAL(5,3) NOT NULL CHECK (valeur >= 0),
    date_debut DATE NOT NULL
);

-- Table principale des comptes de dépôt
CREATE TABLE compte_depot (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    numero_compte VARCHAR(20) UNIQUE NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    solde DECIMAL(15,2) DEFAULT 0.00 CHECK (solde >= 0),
    taux_interet_id BIGINT REFERENCES taux_interet(id)
);

-- Table des transactions (simplifiée)
CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    type_operation_id BIGINT NOT NULL REFERENCES type_operation(id),
    compte_depot_id BIGINT NOT NULL REFERENCES compte_depot(id),
    
    -- Montants essentiels
    montant DECIMAL(15,2) NOT NULL,
    solde_avant DECIMAL(15,2) NOT NULL,
    solde_apres DECIMAL(15,2) NOT NULL,
    
    -- Informations de base
    date_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interet_historique(
    id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL REFERENCES transaction(id),
    montant_interet DECIMAL(15,2) NOT NULL,
    date_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Types d'opération essentiels
INSERT INTO type_operation (libelle) VALUES 
('DEPOT'),
('RETRAIT');

-- Taux d'intérêt de base
INSERT INTO taux_interet (valeur, date_debut) VALUES 
(1.500, '2024-01-01'),
(2.000, '2024-06-01');
