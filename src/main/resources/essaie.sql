-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 02, 2024 at 06:36 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rental_agency`
--

-- --------------------------------------------------------

--
-- Table structure for table `agence`
--

CREATE TABLE `agence` (
                          `id` int(11) NOT NULL,
                          `adresse` varchar(255) DEFAULT NULL,
                          `cp` int(11) NOT NULL,
                          `email` varchar(255) DEFAULT NULL,
                          `image` varchar(255) DEFAULT NULL,
                          `nom` varchar(255) DEFAULT NULL,
                          `tel` varchar(255) DEFAULT NULL,
                          `ville` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client_user`
--

CREATE TABLE `client_user` (
                               `id` int(11) NOT NULL,
                               `enabled` bit(1) NOT NULL,
                               `password` varchar(255) DEFAULT NULL,
                               `roles` varchar(255) DEFAULT NULL,
                               `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client_user_seq`
--

CREATE TABLE `client_user_seq` (
                                   `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client_user_seq`
--

INSERT INTO `client_user_seq` (`next_val`) VALUES
    (1);

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE `location` (
                            `id` int(11) NOT NULL,
                            `date_debut` datetime(6) DEFAULT NULL,
                            `date_fin` datetime(6) DEFAULT NULL,
                            `date_reservation` date DEFAULT NULL,
                            `total` double NOT NULL,
                            `id_client` int(11) NOT NULL,
                            `id_vehicle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vehicule`
--


CREATE TABLE `comment` (
                           `id` int(11) NOT NULL,
                           `description` varchar(255) DEFAULT NULL,
                           `vehicule_id` int(11) NOT NULL,
                           `client_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agence`
--
ALTER TABLE `agence`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client_user`
--
ALTER TABLE `client_user`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK2f8ionxov4qgxv9uci9kw0byp` (`id_client`),
  ADD KEY `FK7sti1ct9rflubgfmuynfu5brt` (`id_vehicle`);


--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FK2f8ionxov4qgxv9uci9kw0bys` (`client_id`),
  ADD KEY `FK7sti1ct9rflubgfmuynfu5brq` (`vehicule_id`);

--

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agence`
--
ALTER TABLE `agence`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

CREATE TABLE `vehicule` (
                            `id` int(11) NOT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `image` varchar(255) DEFAULT NULL,
                            `marque` varchar(255) DEFAULT NULL,
                            `modele` varchar(255) DEFAULT NULL,
                            `type` varchar(255) DEFAULT NULL,
                            `prix_journalier` double DEFAULT NULL,
                            `agence_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Indexes for table `vehicule`
--
ALTER TABLE `vehicule`
    ADD PRIMARY KEY (`id`),
  ADD KEY `FKr2si5ad4hjubekoy3kuqd9dis` (`agence_id`);


--
-- AUTO_INCREMENT for table `vehicule`
--
ALTER TABLE `vehicule`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for table `vehicule`
--
ALTER TABLE `vehicule`
    ADD CONSTRAINT `FKr2si5ad4hjubekoy3kuqd9dis` FOREIGN KEY (`agence_id`) REFERENCES `agence` (`id`);
COMMIT;


--
-- Constraints for dumped tables
--

--
-- Constraints for table `location`
--
ALTER TABLE `location`
    ADD CONSTRAINT `FK2f8ionxov4qgxv9uci9kw0byp` FOREIGN KEY (`id_client`) REFERENCES `client_user` (`id`),
  ADD CONSTRAINT `FK7sti1ct9rflubgfmuynfu5brt` FOREIGN KEY (`id_vehicle`) REFERENCES `vehicule` (`id`);

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
    ADD CONSTRAINT `FK2f8ionxov4qgxv9uci9kw0bys` FOREIGN KEY (`client_id`) REFERENCES `client_user` (`id`),
  ADD CONSTRAINT `FK7sti1ct9rflubgfmuynfu5brq` FOREIGN KEY (`vehicule_id`) REFERENCES `vehicule` (`id`);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;