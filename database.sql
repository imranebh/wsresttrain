CREATE DATABASE IF NOT EXISTS train_management;
USE train_management;

CREATE TABLE IF NOT EXISTS trains (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    villeDepart VARCHAR(255),
    villeArrivee VARCHAR(255),
    heureDepart VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bookNumber VARCHAR(255) UNIQUE,
    trainId BIGINT,
    numberPlaces INT,
    FOREIGN KEY (trainId) REFERENCES trains(id) ON DELETE CASCADE
);
