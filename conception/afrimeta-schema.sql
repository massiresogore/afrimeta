
-- SCHEMA DAPTER LE VRAI

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

drop database if exists `afrimeta`;
create database `afrimeta`;
use `afrimeta`;



CREATE TABLE if not exists `client_user`(
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(30) unique not null ,
    role varchar(50) not null,
    email varchar(100) not null unique,
    password varchar(100) not null ,
    enable bit(1) default 1,
    profile_id int default null,
    key `FK_profile_id`(`profile_id`),
    constraint `FK_profile_id` foreign key (`profile_id`) references `profile`(`profile_id`)
    on delete cascade on update cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- start
create table if not exists taille(
                                     taille_id int primary key auto_increment,
                                     nom varchar(50) not null
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists magasin(
                                      magasin_id int primary key auto_increment,
                                      libele varchar(50) not null,
    logo_url varchar(200),
    description TEXT not null ,
    user_id int  not null,
    key `Fk_user_id` (`user_id`),
    constraint `Fk_user_id` foreign key (`user_id`) references `client_user`(`user_id`)
    on delete no action on update no action
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists website(
                                      website_id int primary key auto_increment,
                                      website_url varchar(100) not null,
    magasin_id int not null,
    key `FK_magasin_id` (`magasin_id`),
    constraint `FK_magasin_id` foreign key (`magasin_id`) references magasin(`magasin_id`)
    on delete no action on update no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists `type_produit`(
                                             type_produit_id int primary key auto_increment,
                                             nom varchar(50)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists categorie(
                                        categorie_id int primary key auto_increment,
                                        nom varchar(100)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists produit(
                                      produit_id int primary key auto_increment,
                                      tite varchar(50),
    description TEXT,
    quantite_stock int(11),
    image_url varchar (200),
    prix numeric(10,2) not null,
    data_ajout datetime DEFAULT current_timestamp not null,
    categorie_id int not null,
    type_produit_id int not null,
    website_id int not null,
    key `FK_categorie_id`(`categorie_id`),
    key `FK_type_produit_id`(`type_produit_id`),
    key `FK_website_id`(`website_id`),
    constraint `FK_categorie_id` foreign key (`categorie_id`) references `categorie`(`categorie_id`)
    on delete no action on update no action,
    constraint `FK_type_produit_id` foreign key (`type_produit_id`) references `type_produit`(`type_produit_id`)
    on delete no action on update no action,
    constraint `FK_website_id` foreign key (`website_id`) references `website`(`website_id`)
    on delete no action on update no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists commentaire(
                                          commentaire_id int primary key auto_increment,
                                          commentaire_date datetime default current_timestamp not null,
                                          contenu TEXT not null,
                                          user_id int not null,
                                          key `FKc_user_id`(`user_id`),
    constraint `Fkc_user_id` foreign key (`user_id`) references `client_user`(`user_id`)
    on delete no action on update no action
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists couleur(
                                      couleur_id int primary key auto_increment,
                                      nom varchar(50)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists image(
                                    image_id int primary key auto_increment,
                                    name varchar(100) not null,
    type varchar(100) not null,
    file_path varchar(150) not null,
    produit_id int,
    magasin_id int,
    key `FK_produit_id`(`produit_id`),
    key `FKm_magasin_id`(`magasin_id`),
    constraint `FK_produit_id` foreign key (`produit_id`) references `produit`(`produit_id`)
    on delete no action on update no action,
    constraint `FKm_magasin_id` foreign key (`magasin_id`) references `magasin`(`magasin_id`)
    on delete no action on update no action
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists facture(
                                      facture_id int primary key auto_increment,
                                      facture_date datetime not null,
                                      total_hors_taxe numeric(10,2) not null,
    total_tout_taxe_comprise numeric(10,2) not null,
    total_tva numeric(10,2) not null
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists commande(
                                       commande_id int primary key auto_increment,
                                       commande_date datetime default current_timestamp not null,
                                       commande_total numeric(10,2) not null,
    adresse varchar(200) not null,
    prix_total numeric(10,2) not null,
    nombre_produits int not null,
    user_id int not null,
    facture_id int not null,
    key `FKcc_user_id`(`user_id`),
    key `facture_id`(`facture_id`),
    constraint `FKcc_facture_id` foreign key (`facture_id`) references `facture`(`facture_id`)
    on delete no action on update no action,
    constraint `FKcc_user_id` foreign key (`user_id`) references `client_user`(`user_id`)
    on delete no action on update no action
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- COMPOSITE TABLE

-- TYPE PRODUIT ET TAILL TABLE
create table if not exists liee(
                                   type_produit_id int not null,
                                   taille_id int not null,
                                   quantite int not null,
                                   key `FK_type_produit_id`(`type_produit_id`),
    key `FK_taille_id`(`taille_id`),
    primary key (`type_produit_id`, `taille_id`),
    constraint `FKl_type_produit_id` foreign key  (`type_produit_id`) references `type_produit`(`type_produit_id`)
    on update no action on delete no action,
    constraint `FKl_taille_id` foreign key (`taille_id`) references `taille`(`taille_id`)
    on update no action on delete no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- PRODUIT ET COULEUR TABLE
create table if not exists faconne(
                                      produit_id int not null,
                                      couleur_id int not null,
                                      quantite int not null,
                                      key `FKf_produit_id`(`produit_id`),
    key `FK_couleur_id`(`couleur_id`),
    primary key (`produit_id`, `couleur_id`),
    constraint `FKf_produit_id` foreign key  (`produit_id`) references `produit`(`produit_id`)
    on update no action on delete no action,
    constraint `FK_couleur_id` foreign key (`couleur_id`) references `couleur`(`couleur_id`)
    on update no action on delete no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- produit_commade relation table
create table if not exists contenir(
                                       produit_id int not null,
                                       commande_id int not null,
                                       quantite int not null,
                                       key `FKc_produit_id`(`produit_id`),
    key `FK_commande_id`(`commande_id`),
    primary key (`produit_id`, `commande_id`),
    constraint `FKc_produit_id` foreign key  (`produit_id`) references `produit`(`produit_id`)
    on update no action on delete no action,
    constraint `FK_commande_id` foreign key (`commande_id`) references `commande`(`commande_id`)
    on update no action on delete no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- EN COMPOSITE TABLE

create table if not exists mode_livraison(
                                             mode_livraison_id int primary key auto_increment,
                                             nom varchar(150) not null
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists livraison(
                                        livraison_id int primary key auto_increment,
                                        livraison_date datetime not null,
                                        description varchar(150) not null,
    mode_livraison_id int not null,
    commande_id int not null,
    key `FK_mode_livraison_id`(`mode_livraison_id`),
    key `FKl_commande_id`(`commande_id`),
    constraint `FK_mode_livraison_id` foreign key (`mode_livraison_id`) references `mode_livraison` (`mode_livraison_id`)
    on delete no action on update no action,
    constraint `FKl_commande_id` foreign key (`commande_id`) references `commande`(`commande_id`)
    on delete no action on update no action
    )ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists mode_paiement(
                                            mode_paiement_id int primary key auto_increment,
                                            nom varchar(100) not null
    )ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists paiment(
                                      paiment_id int primary key auto_increment,
                                      paiment_date datetime default current_timestamp not null,
                                      description varchar(100) not null,
    mode_paiment_id int not null,
    commande_id int not null,
    key `FK_mode_paiment_id`(`mode_paiment_id`),
    key `FKp_commande_id`(`commande_id`),
    constraint `FK_mode_paiment_id` foreign key (`mode_paiment_id`) references `mode_paiement` (`mode_paiement_id`)
    on delete no action on update no action,
    constraint `FKp_commande_id` foreign key (`commande_id`) references `commande` (`commande_id`)
    on delete no action on update no action
    )ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE profile (
                         profile_id INT AUTO_INCREMENT PRIMARY KEY,
                         user_id INT NOT NULL,
                         nom VARCHAR(50),
                         prenom VARCHAR(50),
                         numero_telephone VARCHAR(15),
                         date_naissance DATE,
                         genre ENUM('male', 'female', 'other'),
                         addresse TEXT,
                         ville VARCHAR(100),
                         code_postal VARCHAR(10),
                         pays VARCHAR(50),
                         profile_picture_url VARCHAR(255),
                         bio TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES `client_user` (user_id) ON DELETE CASCADE
);