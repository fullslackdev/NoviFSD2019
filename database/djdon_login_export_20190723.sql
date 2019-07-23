-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for djdon_login
CREATE DATABASE IF NOT EXISTS `djdon_login` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `djdon_login`;

-- Dumping structure for table djdon_login.access
CREATE TABLE IF NOT EXISTS `access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create` tinyint(1) NOT NULL DEFAULT 0,
  `read` tinyint(1) NOT NULL DEFAULT 0,
  `update` tinyint(1) NOT NULL DEFAULT 0,
  `delete` tinyint(1) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_access_role_id` (`role_id`),
  CONSTRAINT `FK_access_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.access: ~0 rows (approximately)
/*!40000 ALTER TABLE `access` DISABLE KEYS */;
/*!40000 ALTER TABLE `access` ENABLE KEYS */;

-- Dumping structure for table djdon_login.group
CREATE TABLE IF NOT EXISTS `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.group: ~3 rows (approximately)
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` (`id`, `name`, `description`, `active`) VALUES
	(1, 'administrator', 'Can create, delete and modify roles, access and users.', 1),
	(2, 'producer', 'Can upload a demo and send message about their own demo\'s.', 1),
	(3, 'promoteam', 'Can listen, download and give feedback about demo\'s. Can also send internal messages about demo\'s.', 1);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;

-- Dumping structure for table djdon_login.login_info
CREATE TABLE IF NOT EXISTS `login_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `ip_address` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `header_info` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_login_info_user_id` (`user_id`),
  CONSTRAINT `FK_login_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.login_info: ~0 rows (approximately)
/*!40000 ALTER TABLE `login_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_info` ENABLE KEYS */;

-- Dumping structure for table djdon_login.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_role_group_id` (`group_id`),
  CONSTRAINT `FK_role_group_id` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.role: ~0 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table djdon_login.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `salt` varchar(75) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `secret` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_user_group_id` (`group_id`),
  CONSTRAINT `FK_user_group_id` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.user: ~10 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `group_id`, `username`, `password`, `salt`, `secret`, `active`) VALUES
	(1, 1, 'admin', '$argon2id$v=19$m=102400,t=8,p=2$Rty9dXviU16o36w4to6a4Q$iSLfJpHm/A6K+t1xhjbfDEqCz4WsieetaIZ+yyqbZYs', 'dK09XhczjxVvFBkBuXrF2oLdoBLQrm8y7WnAckj6VC8=', 'oEiOgaldsR/Tuor6Mfzkh7fHIfntefDVZ8s5sMHiQARS+5bSdBrbLNPxKFu7ZjAN+WMqJSRXPCQ4AIl9', 1),
	(2, 2, 'bart', '$argon2id$v=19$m=102400,t=8,p=2$BKnsx3ALYVbF3eXbnKFIoA$aVJnjpSm50pjVkCahoOyX4GeAm+g08A6uau38AGz6zc', NULL, NULL, 1),
	(3, 2, 'danny', '$argon2id$v=19$m=102400,t=8,p=2$NDHFd0+OWjeo+olpKJGt8A$VMhmiCAfyV2hukBjKv1MWd1TWYbHcckyCehdFDMoibA', 'xagTVI45+AqchBRAv4kwwme0zGxx1felnBeJ+mvFs5U=', 'GRqOw85dH/IL36DQZLREBRc91+ySLNnOdzbM4kBZlm6FIjqGBwVtQ+jFVDOd3YvzSkMSC/g5RPSE+R81', 1),
	(4, 3, 'chaya', '$argon2id$v=19$m=102400,t=8,p=2$snCsMsJzhozAgflceuDqbw$C/tFs2fP9jPvfg0xAoH28TW6zNSyw63fuv4PDA1zIN4', '/3M7avZJzx0wYI4bwSOnZNokqViHqwPCwL4kxmrKfuU=', '5zIrtlmw9NgE5m/Mll4mSm4t9g5UO8WRq9rB4yNUcVDK4ButnQtROGCZKkyAT+XGsKhwH04sV7tWzCkq', 0),
	(5, 3, 'joris', '$argon2id$v=19$m=102400,t=8,p=2$uoznmNqdRX7POWExp/RdzQ$tLCpM8acvSrkb2H2+r3mpYEMzFh/WLVtspWthInfjIY', 'RzFEFmgp6tLm6QpmEBl2PeUPFY6KMwCDdD6nOVzo1vM=', 'UUInaGQ3lutzeCB7eIVM4+5zzvBEmfC4n8hcq7ha5ZiLKGlxH1rtkgEYJJzArvkzBokSC6Bibfsi2d81', 1),
	(6, 2, 'mark', '$argon2id$v=19$m=102400,t=8,p=2$09X6SYrkh20vrDlf25Av9Q$HtxtQK+izuefRp1NczbzVA4XlaVdPpI3vk/AideJ0O0', 'nz/SPKmtfUR11zSLcuI1zHRFz0U0J4C/Ich8aJm6nxA=', 'euG2xJPlgOi5/hbUcXO51kANvcYNQTe7SUSap/woBJ5IKO8j5RYUBwl8vvBzhuqINNIGaJMJ0FMum5Tm', 1),
	(7, 2, 'anton', '$argon2id$v=19$m=102400,t=8,p=2$V/FDGXIIo+ELEWaXO72D7Q$HeD1ojFfrhfW/4LNlOcllHGtBiVSOXidy1YAxeRjrgM', 'hCdEX6VfhcYg4oCHCfDZ6iAFDdpdQn0KWqVQ/7dkjS0=', 'HEqdQVtC6/VriWXMf0kplS4okQjyjMwM4YQBjJMJfVsh4f7+ZRozd3Mqmwl+JXVAKtVWGW5sAL6UswBR', 1),
	(8, 3, 'kitty', '$argon2id$v=19$m=102400,t=8,p=2$rWXxN16/1c2wJRiBvudCSw$mhILxtFlGwZhUc3wD+1GYYi3SjGSPDYnp8kR0Z/BIQU', 'TFi2as3QTBDbl4gXIa/JhpQGUsuduiVwjhG+WqHeKl4=', 'pryk5FDexCQc6AESNhn9j86LRxvj6eXTMpb+MzKmijcbdNvjWerhhIM1bZE3rKD7Ae6A1sOiJCYPLTkC', 1),
	(9, 2, 'henk', '$argon2id$v=19$m=102400,t=8,p=2$4DdRw6c8Jzy47SDOVD+dhg$puLY9mSyrQV9aVQHdA1zR42qXB8jkWhgVytFh4Aua9U', 'zyMJM87cPAnikK5zh2grfR9DgSoKD1qAuqjQiT7Z04U=', 'j1EzXsPKSRFIW/dEGRyFkhmI2eQJeMHs1e5id5xLogwkRnTZt2yvqBaBvkBNHFgApBRq/nl0ggu7I1ZB', 0),
	(10, 3, 'henk', '$argon2id$v=19$m=102400,t=8,p=2$4DdRw6c8Jzy47SDOVD+dhg$puLY9mSyrQV9aVQHdA1zR42qXB8jkWhgVytFh4Aua9U', 'zyMJM87cPAnikK5zh2grfR9DgSoKD1qAuqjQiT7Z04U=', 'j1EzXsPKSRFIW/dEGRyFkhmI2eQJeMHs1e5id5xLogwkRnTZt2yvqBaBvkBNHFgApBRq/nl0ggu7I1ZB', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table djdon_login.user_info
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `firstname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `facebook` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `instagram` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_info_user_id` (`user_id`),
  CONSTRAINT `FK_user_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table djdon_login.user_info: ~8 rows (approximately)
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` (`id`, `user_id`, `firstname`, `lastname`, `country`, `email`, `facebook`, `instagram`) VALUES
	(1, 1, 'Hertog', 'Jan', 'Pilsener', 'koning.pintenman@onze.god', NULL, NULL),
	(2, 2, 'Bart', 'Loeffen', 'Nederland', 'bart@loeffen.com', NULL, NULL),
	(3, 3, 'Danny', 'Sukdeo', 'Aruba', 'danny.sukdeo@gmail.com', NULL, NULL),
	(4, 4, 'Chaya', 'Kanhai', 'Suriname', 'chayakanhai@msn.com', NULL, NULL),
	(5, 5, 'Joris', 'Kalkhoven', 'Nederland', 'joris.k@live.nl', NULL, NULL),
	(6, 7, 'Anton', 'Vu', 'Vietnam', 'antonvu580@hotmail.com', NULL, NULL),
	(7, 6, 'Mark', 'Vlek', 'Nederland', 'vlekmark@gmail.com', NULL, NULL),
	(8, 8, 'Kitty', 'Oost', 'Nederland', 'kyko.me@gmail.com', NULL, NULL),
	(9, 9, 'Henk', '9', 'Duitsland', 'henk@henk.de', NULL, NULL),
	(10, 10, 'Henk', '10', 'Rusland', 'henk@henk.cccp', NULL, NULL);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
