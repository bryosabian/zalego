-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 05, 2018 at 06:25 PM
-- Server version: 10.0.34-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cloudsyn_zalego`
--

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `ID` int(11) NOT NULL,
  `image` text,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`ID`, `image`, `date_created`) VALUES
(0, 'http://cloudsync.co.ke/img/team/Shawdz.jpg', '2018-04-05 15:17:57'),
(1, 'http://cloudsync.co.ke/img/team/Shawdz.jpg', '2018-04-05 15:18:15'),
(2, 'http://cloudsync.co.ke/img/team/Daniel.jpg', '2018-04-05 15:18:43'),
(3, 'http://cloudsync.co.ke/img/team/Eliud.jpg', '2018-04-05 15:19:11');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `firstname` text,
  `email` text,
  `gender` text,
  `language` text,
  `dob` text,
  `password` text
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `firstname`, `email`, `gender`, `language`, `dob`, `password`) VALUES
(0, 'Brian', 'sab@ff.com', 'Male', 'Python', '12 dec 2017', '19ce7f197b929c3e9fb0cf9a4365d4b0'),
(0, 'Lisa', 'orio@gmail.com', 'Male', 'Python', 'hhh', '190e0fe686016645780d807f470c48d3'),
(0, 'Harry Origi', 'sabian@gmail.com', 'Male', 'Python', '12 07 1991', '19ce7f197b929c3e9fb0cf9a4365d4b0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
