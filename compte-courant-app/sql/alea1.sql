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
('transactions', 'VALIDATE', 3);