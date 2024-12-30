-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2024 at 03:32 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stellarvest`
--

-- --------------------------------------------------------

--
-- Table structure for table `eventmodel`
--

CREATE TABLE `eventmodel` (
  `event_id` char(5) NOT NULL,
  `event_name` varchar(100) DEFAULT NULL,
  `event_date` varchar(50) DEFAULT NULL,
  `event_location` varchar(100) DEFAULT NULL,
  `event_description` varchar(100) DEFAULT NULL,
  `organizer_id` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `eventmodel`
--

INSERT INTO `eventmodel` (`event_id`, `event_name`, `event_date`, `event_location`, `event_description`, `organizer_id`) VALUES
('ev001', 'test', '22-08-2024', 'dimana', 'deskripsi', 'og001'),
('ev002', 'test2', '2024-10-30', 'ALHAMDULILLAH', 'bismillah', 'og002'),
('ev003', 'asdasd', '2024-12-05', 'aokeokawe', 'cok, kok iso', 'og003'),
('ev004', 'bernyanyi bernadya', '2025-12-26', 'nanyi ja', 'Jakarta', 'og004');

-- --------------------------------------------------------

--
-- Table structure for table `eventorganizer`
--

CREATE TABLE `eventorganizer` (
  `event_created` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `eventorganizer`
--

INSERT INTO `eventorganizer` (`event_created`) VALUES
('Created'),
('Created');

-- --------------------------------------------------------

--
-- Table structure for table `invitation`
--

CREATE TABLE `invitation` (
  `invitation_id` char(5) NOT NULL,
  `event_id` char(5) DEFAULT NULL,
  `user_id` char(5) DEFAULT NULL,
  `invitation_status` varchar(10) DEFAULT NULL,
  `invitation_role` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invitation`
--

INSERT INTO `invitation` (`invitation_id`, `event_id`, `user_id`, `invitation_status`, `invitation_role`) VALUES
('iv001', 'ev002', 'us006', 'pending', 'Guest'),
('iv002', 'ev002', 'us007', 'pending', 'Vendor'),
('iv003', 'ev004', 'us006', 'accept', 'Guest'),
('iv004', 'ev004', 'us007', 'accept', 'Vendor');

-- --------------------------------------------------------

--
-- Table structure for table `usermodel`
--

CREATE TABLE `usermodel` (
  `user_id` char(5) NOT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_role` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usermodel`
--

INSERT INTO `usermodel` (`user_id`, `user_email`, `user_name`, `user_password`, `user_role`) VALUES
('us001', 'test@email', 'test123', 'testing', 'Admin'),
('us002', 'budi@email', 'budi', 'budi123', 'Admin'),
('us003', 'eo@email', 'EO', 'eo123', 'Event Organizer'),
('us006', 'guest1@email', 'guest', 'guest', 'Guest'),
('us007', 'vendor@email', 'vendor', 'vendor', 'Vendor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `eventmodel`
--
ALTER TABLE `eventmodel`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `invitation`
--
ALTER TABLE `invitation`
  ADD PRIMARY KEY (`invitation_id`),
  ADD KEY `event_id` (`event_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `usermodel`
--
ALTER TABLE `usermodel`
  ADD PRIMARY KEY (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `invitation`
--
ALTER TABLE `invitation`
  ADD CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `eventmodel` (`event_id`),
  ADD CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `usermodel` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
