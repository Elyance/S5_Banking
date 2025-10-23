
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

CREATE TABLE IF NOT EXISTS historique_devouvert_autorise (
    id BIGSERIAL PRIMARY KEY,
    value DECIMAL(15,2) NOT NULL,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

ALTER TABLE compte_courant
ADD COLUMN decouvert_autorise DECIMAL(15,2) DEFAULT 0.00;

INSERT INTO historique_devouvert_autorise (value, date_modification) VALUES 
(50000.00, '2024-01-01 00:00:00'),
(100000.00, '2024-06-01 00:00:00');


CREATE TABLE IF NOT EXISTS statut_transaction (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction_statut_mouvement (
    id BIGSERIAL PRIMARY KEY,
    id_statut_transaction BIGINT NOT NULL REFERENCES statut_transaction(id),
    id_transaction BIGINT NOT NULL REFERENCES transactions(id),
    date_mouvement TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO statut_transaction (libelle) VALUES 
('CREE'),
('VALIDE'),
('REJETE');

-- =====================================================
-- TABLES POUR LE SYSTÈME D'AUTHENTIFICATION ET AUTORISATION
-- =====================================================
-- Table Direction
CREATE TABLE IF NOT EXISTS direction (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    niveau INTEGER NOT NULL
);

-- Table Utilisateur
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    mdp VARCHAR(255) NOT NULL,
    id_direction BIGINT REFERENCES direction(id),
    role INTEGER NOT NULL
);

-- Table ActionRole
CREATE TABLE IF NOT EXISTS action_role (
    id BIGSERIAL PRIMARY KEY,
    nomTable VARCHAR(100) NOT NULL,
    action VARCHAR(50) NOT NULL,
    role INTEGER NOT NULL
);

-- =====================================================
-- INSERTIONS DE DONNÉES D'EXEMPLE
-- =====================================================

-- Directions
INSERT INTO direction (libelle, niveau) VALUES
('Direction Générale', 1),
('Direction Financière', 2),
('Direction Ressources Humaines', 2),
('Direction IT', 3);

-- Utilisateurs (mots de passe en clair pour test ; utiliser hash en prod)
INSERT INTO utilisateur (login, mdp, id_direction, role) VALUES
('admin', 'admin123', 1, 1),  -- Direction Générale, rôle 1 (admin)
('user1', 'pass1', 2, 2),     -- Direction Financière, rôle 2 (user)
('manager', 'manager123', 3, 3);  -- Direction RH, rôle 3 (manager)

-- Actions par rôle (rôle 1 = admin peut tout, rôle 2 = user limité, rôle 3 = manager intermédiaire)
INSERT INTO action_role (nomTable, action, role) VALUES
('transactions', 'CREATE', 1),
('transactions', 'VALIDATE', 1);