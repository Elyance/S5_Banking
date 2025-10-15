
CREATE DATABASE compte_courant_db;

\c compte_courant_db;

CREATE TABLE IF NOT EXISTS statut_compte (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS type_operation (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS compte_courant (
    id BIGSERIAL PRIMARY KEY,
    numero_compte VARCHAR(20) UNIQUE NOT NULL,
    client_id BIGINT NOT NULL,
    solde DECIMAL(15,2) DEFAULT 0.00,
    decouvert_autorise DECIMAL(15,2) DEFAULT 0.00,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS statut_compte_courant_mvt (
    id BIGSERIAL PRIMARY KEY,
    compte_courant_id BIGINT NOT NULL,
    statut_compte_id BIGINT NOT NULL,
    date_mvt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    compte_courant_id BIGINT NOT NULL,
    type_operation_id BIGINT NOT NULL,
    montant DECIMAL(15,2) NOT NULL,
    description TEXT,
    date_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO statut_compte (libelle) VALUES 
('ACTIF'),
('SUSPENDU'),
('FERME'),
('SUPPRIME');

-- =====================================================
-- INSERTION DES TYPES D'OPÉRATION
-- =====================================================

INSERT INTO type_operation (libelle) VALUES 
('DEPOT'),
('RETRAIT');