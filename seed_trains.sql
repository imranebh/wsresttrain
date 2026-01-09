-- Seed data for trains table with Moroccan cities
USE train_management;

INSERT INTO trains (nom, villeDepart, villeArrivee, heureDepart) VALUES
('Al Boraq 101', 'Tanger', 'Casablanca', '08:00'),
('Al Boraq 102', 'Casablanca', 'Tanger', '09:00'),
('Al Atlas 201', 'Marrakech', 'Casablanca', '07:30'),
('Al Atlas 202', 'Casablanca', 'Marrakech', '15:45'),
('TNR 301', 'Casablanca', 'Rabat', '07:15'),
('TNR 302', 'Rabat', 'Kenitra', '08:45'),
('Al Atlas 401', 'Fes', 'Oujda', '10:20'),
('Al Atlas 402', 'Oujda', 'Fes', '14:10'),
('Al Atlas 501', 'Casablanca', 'Fes', '11:00'),
('Al Atlas 502', 'Fes', 'Casablanca', '16:30'),
('Al Boraq 103', 'Tanger', 'Rabat', '12:00'),
('Al Boraq 104', 'Rabat', 'Tanger', '17:00'),
('TNR 303', 'Casablanca', 'Settat', '18:15'),
('Al Atlas 601', 'Marrakech', 'Fes', '06:00'),
('Al Atlas 602', 'Fes', 'Marrakech', '13:00'),
('Al Atlas 701', 'Meknes', 'Tanger', '09:15'),
('Al Atlas 702', 'Tanger', 'Meknes', '16:45'),
('TNR 304', 'El Jadida', 'Casablanca', '07:00'),
('TNR 305', 'Casablanca', 'El Jadida', '17:30'),
('Al Atlas 801', 'Nador', 'Fes', '08:50');
