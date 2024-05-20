-- insert profile
INSERT INTO profile (profile_id, nom, prenom, numero_telephone, date_naissance, genre, addresse, ville, code_postal, pays, profile.profile_picture_url, bio)
VALUES
    (null, 'Dupont', 'Jean', '0612345678', '1985-05-15', 'male', '12 Rue des Fleurs', 'Paris', '75001', 'France', 'http://example.com/pictures/jean.jpg', 'Ingénieur logiciel passionné par la technologie et les voyages.'),
    (null, 'Martin', 'Sophie', '0623456789', '1990-08-22', 'female', '34 Avenue des Champs', 'Lyon', '69002', 'France', 'http://example.com/pictures/sophie.jpg', 'Designer graphique aimant lart et la photographie.'),
    (null, 'Bernard', 'Luc', '0634567890', '1978-11-30', 'male', '56 Boulevard du Général', 'Marseille', '13003', 'France', 'http://example.com/pictures/luc.jpg', 'Architecte avec une passion pour les bâtiments écologiques.'),
    (null, 'Dubois', 'Emma', '0645678901', '1992-03-14', 'female', '78 Rue de la Liberté', 'Bordeaux', '33000', 'France', 'http://example.com/pictures/emma.jpg', 'Étudiante en biologie aimant les animaux et la nature.'),
    (null, 'Lefevre', 'Hugo', '0656789012', '1980-07-09', 'male', '90 Chemin des Vignes', 'Nice', '06000', 'France', 'http://example.com/pictures/hugo.jpg', 'Chef cuisinier adorant les saveurs méditerranéennes.'),
    (null, 'Moreau', 'Julie', '0667890123', '1983-12-05', 'female', '21 Impasse des Cerisiers', 'Toulouse', '31000', 'France', 'http://example.com/pictures/julie.jpg', 'Professeure de yoga et méditation.'),
    (null, 'Simon', 'Alexandre', '0678901234', '1975-01-20', 'male', '123 Route de la Plage', 'Montpellier', '34000', 'France', 'http://example.com/pictures/alexandre.jpg', 'Photographe professionnel spécialisé dans les paysages marins.'),
    (null, 'Laurent', 'Isabelle', '0689012345', '1987-09-25', 'female', '45 Rue des Oliviers', 'Strasbourg', '67000', 'France', 'http://example.com/pictures/isabelle.jpg', 'Avocate passionnée par les droits de lhomme.'),
    (null, 'Roux', 'Thomas', '0690123456', '1995-06-18', 'male', '67 Allée des Roses', 'Nantes', '44000', 'France', 'http://example.com/pictures/thomas.jpg', 'Développeur web et amateur de jeux vidéo.'),
    (null, 'Petit', 'Claire', '0601234567', '1982-10-11', 'female', '89 Quai des Chartrons', 'Brest', '29200', 'France', 'http://example.com/pictures/claire.jpg', 'Auteure de romans et de nouvelles, passionnée par lécriture.');
-- end insert profile