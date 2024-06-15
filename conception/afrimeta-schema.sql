
-- SCHEMA DAPTER LE VRAI

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

drop database if exists `afrimeta`;
create database `afrimeta`;
use `afrimeta`;


create table if not exists profile (
                                       profile_id INT AUTO_INCREMENT PRIMARY KEY,
                                       nom VARCHAR(50),
                                       prenom VARCHAR(50),
                                       numero_telephone VARCHAR(15),
                                       date_naissance DATE,
                                       genre ENUM('homme', 'femme', 'autre'),
                                       addresse TEXT,
                                       ville VARCHAR(100),
                                       code_postal VARCHAR(10),
                                       pays VARCHAR(50),
                                       profile_picture_url VARCHAR(255),
                                       bio TEXT,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE if not exists `client_user`(
                                            user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                            username varchar(30) unique not null ,
                                            role varchar(50) default 'USER' not null ,
                                            email varchar(100) not null unique,
                                            password varchar(100) not null ,
                                            enable bit(1) default 1 not null ,
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
                                      description TEXT not null ,
                                      user_id int  not null,
                                      key `Fk_user_id` (`user_id`),
                                      create_at datetime default current_timestamp,
                                      constraint `Fk_user_id` foreign key (`user_id`) references `client_user`(`user_id`)
                                          on delete no action on update no action
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists website(
                                      website_id int primary key auto_increment,
                                      website_url varchar(100) default null,
                                      magasin_id int default null,
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
                                      titre varchar(50) not null ,
                                      description TEXT,
                                      quantite_stock int(11),
                                      prix numeric(10,2) not null,
                                      date_ajout datetime DEFAULT current_timestamp not null ,
                                      categorie_id int default null,
                                      type_produit_id int default null,
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
create table if not exists facture(
                                      facture_id int primary key auto_increment,
                                      facture_date datetime not null,
                                      total_hors_taxe numeric(10,2) not null,
                                      total_tout_taxe_comprise numeric(10,2) not null,
                                      total_tva numeric(10,2) not null
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists commande(
                                       commande_id int primary key auto_increment,
                                       created_at datetime default current_timestamp not null ,
                                       updated_at datetime default null ,
                                       commande_total numeric not null ,
                                       adresse varchar(200) not null ,
                                       prix_total numeric(10,2) not null ,
                                       nombre_produit int not null ,
                                       user_id int not null ,
                                       key `FK_user_idc`(`user_id`),
                                       constraint `FK_user_idc` foreign key (`user_id`) references afrimeta.client_user(`user_id`)
                                           on delete no action  on update no action

)ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_general_ci;


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
                                      nom varchar(100)  not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists image(
                                    image_id int primary key auto_increment,
                                    name varchar(100) default null,
                                    type varchar(100) default null,
                                    file_path varchar(150) default null,
                                    produit_id int,
                                    key `FK_produit_id`(`produit_id`),
                                    constraint `FK_produit_id` foreign key (`produit_id`) references `produit`(`produit_id`)
                                        on delete no action on update no action
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- COMPOSITE TABLE

-- TYPE PRODUIT ET TAILL TABLE
create table if not exists taille_type_produit(
                                                  type_produit_id int not null,
                                                  taille_id int not null,
                                                  key `FK_type_produit_id`(`type_produit_id`),
                                                  key `FK_taille_id`(`taille_id`),
                                                  primary key (`type_produit_id`, `taille_id`),
                                                  constraint `FKl_type_produit_id` foreign key  (`type_produit_id`) references `type_produit`(`type_produit_id`)
                                                      on update no action on delete no action,
                                                  constraint `FKl_taille_id` foreign key (`taille_id`) references `taille`(`taille_id`)
                                                      on update no action on delete no action
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- PRODUIT ET COULEUR TABLE
create table if not exists couleur_produit(
                                              produit_id int not null,
                                              couleur_id int not null,
                                              key `FKf_produit_id`(`produit_id`),
                                              key `FK_couleur_id`(`couleur_id`),
                                              primary key (`produit_id`, `couleur_id`),
                                              constraint `FKf_produit_id` foreign key  (`produit_id`) references `produit`(`produit_id`)
                                                  on update no action on delete no action,
                                              constraint `FK_couleur_id` foreign key (`couleur_id`) references `couleur`(`couleur_id`)
                                                  on update no action on delete no action
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


create table if not exists facture(
                                      facture_id int primary key auto_increment,
                                      facture_date datetime default current_timestamp not null,
                                      total_hors_taxe int,
                                      total_tout_taxe_comprise int,
                                      total_tva int
)ENGINE=InnoDB default CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

create table  if not exists logo(
                                    file_path varchar(200) not null,
                                    logo_name varchar(200) unique not null,
                                    magasin_id int not null
)ENGINE=InnoDB default CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



SET FOREIGN_KEY_CHECKS = 1;
