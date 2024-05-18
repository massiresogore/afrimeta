
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


create table if not exists `ville`(
    ville_id int not null primary key auto_increment,
    nom varchar(50) not null
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists  `adresse`(
    adresse_id int primary key not null auto_increment,
    numero varchar(50) not null,
    cp int(50) not null,
    ville_id int not null,
    key `FK_ville_id`(`ville_id`),
    constraint `FK_ville_id` foreign key (`ville_id`) references `ville` (`ville_id`)
    on DELETE NO ACTION on UPDATE NO ACTION
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE if not exists `client_user`(
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom varchar(30) unique not null ,
    prenom varchar(30),
    role varchar(50) not null,
    email varchar(100) not null unique ,
    password varchar(100) not null ,
    rason_social varchar(100) not null ,
    enable bit(1) not null ,
    adresse_id int not null,
    key `FK_adresse_id` (`adresse_id`),
    constraint `FK_adresse_id` foreign key (`adresse_id`) references `adresse` (`adresse_id`)
    on DELETE NO ACTION on UPDATE NO ACTION
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists `magasin`(
    magasin_id int not null primary key auto_increment,
    libele varchar(20) not null,
    logo varchar(100),
    description TEXT not null,
    user_id int not null,
    key `FK_user_id` (`user_id`),
    constraint `FK_user_id` foreign key (`user_id`) references `client_user` (`user_id`)
    on DELETE NO ACTION on UPDATE NO ACTION
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


create table `website` (
    website_url int not null primary key auto_increment,
    url varchar(200) unique not null,
    magasin_id int not null,
    key `FK_magasin_id` (`magasin_id`),
    CONSTRAINT `FK_magasin_id` foreign key (`magasin_id`) references `magasin`(`magasin_id`)
    on DELETE NO ACTION on UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


