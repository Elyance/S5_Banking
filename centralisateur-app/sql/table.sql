
-- creation de la base de donnees
CREATE DATABASE centralisateur_db;

\c centralisateur_db;

CREATE TABLE IF NOT EXISTS admin_banque (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) UNIQUE NOT NULL,
    adresse TEXT,
    telephone VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS statut_client (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    id BIGSERIAL PRIMARY KEY,
    numero_client VARCHAR(20) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    email VARCHAR(255) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse TEXT,
    profession VARCHAR(100),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mvt_client (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    statut_client_id BIGINT NOT NULL,
    date_mvt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO admin_banque (nom, adresse, telephone, email, mot_de_passe)
VALUES ('SuperAdmin', '123 Avenue Banque', '0123456789', 'admin@banque.com', 'motdepasse123');

INSERT INTO statut_client (libelle) VALUES ('ACTIF');
INSERT INTO statut_client (libelle) VALUES ('BLOQUE');
INSERT INTO statut_client (libelle) VALUES ('INACTIF');
