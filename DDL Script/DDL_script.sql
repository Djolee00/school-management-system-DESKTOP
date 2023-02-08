/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.27 : Database - school_management_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`school_management_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `school_management_db`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `user_id` bigint unsigned NOT NULL,
  `employment_date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `administrator` */

insert  into `administrator`(`user_id`,`employment_date`) values 
(8,'2023-01-11');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(39) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `group_capacity` int unsigned NOT NULL,
  `language_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language` (`language_id`),
  CONSTRAINT `fk_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `end_date_check` CHECK ((`end_date` > `start_Date`)),
  CONSTRAINT `start_date_check` CHECK ((`start_Date` < `end_date`))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `course` */

insert  into `course`(`id`,`name`,`start_date`,`end_date`,`group_capacity`,`language_id`) values 
(1,'English FCE','2023-01-01','2023-03-07',3,1),
(3,'Italian FAST','2023-01-16','2023-05-16',9,6),
(4,'German Elementary','2023-01-08','2023-07-07',10,4),
(5,'France for Beginners','2023-01-20','2023-02-28',7,2),
(18,'France Advance','2023-01-29','2023-01-30',5,2);

/*Table structure for table `course_enrollment` */

DROP TABLE IF EXISTS `course_enrollment`;

CREATE TABLE `course_enrollment` (
  `student_id` bigint unsigned NOT NULL,
  `course_id` bigint unsigned NOT NULL,
  `enrollment_date` date NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`),
  KEY `course_fk` (`course_id`),
  CONSTRAINT `course_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_fk_course` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `course_enrollment` */

insert  into `course_enrollment`(`student_id`,`course_id`,`enrollment_date`) values 
(7,1,'2023-01-13'),
(7,3,'2023-01-18'),
(7,5,'2023-01-31'),
(7,18,'2023-01-31'),
(9,1,'2023-01-18'),
(9,3,'2023-01-13'),
(9,4,'2023-01-18'),
(10,1,'2023-01-13'),
(10,4,'2023-01-13'),
(10,5,'2023-02-08'),
(11,1,'2023-02-06'),
(11,4,'2023-01-13'),
(12,1,'2023-01-13'),
(14,3,'2023-01-13'),
(15,1,'2023-01-13'),
(15,4,'2023-01-13'),
(16,1,'2023-01-18'),
(16,3,'2023-01-18'),
(16,4,'2023-01-18'),
(17,1,'2023-01-14'),
(17,3,'2023-01-14'),
(17,4,'2023-01-14'),
(17,5,'2023-01-14');

/*Table structure for table `course_group` */

DROP TABLE IF EXISTS `course_group`;

CREATE TABLE `course_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `course_id` bigint unsigned NOT NULL,
  `name` varchar(30) NOT NULL,
  `number_of_students` int unsigned NOT NULL,
  PRIMARY KEY (`id`,`course_id`),
  KEY `fk_course` (`course_id`),
  CONSTRAINT `fk_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `course_group` */

insert  into `course_group`(`id`,`course_id`,`name`,`number_of_students`) values 
(1,1,'Group 1',2),
(2,1,'Group 2',2),
(3,3,'Group 1',2),
(4,4,'Group 1',1),
(5,4,'Group 2',2),
(6,1,'Group 3',0),
(7,18,'Group 1',0),
(8,18,'Group 1.2',1),
(9,18,'Group 1.3',0),
(16,5,'Group 1',0),
(17,5,'Group 2',1),
(18,3,'Group 2',2);

/*Table structure for table `group_enrollment` */

DROP TABLE IF EXISTS `group_enrollment`;

CREATE TABLE `group_enrollment` (
  `student_id` bigint unsigned NOT NULL,
  `course_id` bigint unsigned NOT NULL,
  `course_group_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`,`course_group_id`),
  KEY `group_fk` (`course_group_id`,`course_id`),
  CONSTRAINT `group_fk` FOREIGN KEY (`course_group_id`, `course_id`) REFERENCES `course_group` (`id`, `course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `group_enrollment` */

insert  into `group_enrollment`(`student_id`,`course_id`,`course_group_id`) values 
(9,1,1),
(10,1,1),
(12,1,2),
(15,1,2),
(16,3,3),
(17,3,3),
(11,4,4),
(10,4,5),
(15,4,5),
(7,18,8),
(17,5,17),
(9,3,18),
(14,3,18);

/*Table structure for table `language` */

DROP TABLE IF EXISTS `language`;

CREATE TABLE `language` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `level` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `language` */

insert  into `language`(`id`,`name`,`level`) values 
(1,'English','B2'),
(2,'France','B1'),
(3,'English','C1'),
(4,'German','A2'),
(5,'German','B2'),
(6,'Italian','A2');

/*Table structure for table `language_tutor` */

DROP TABLE IF EXISTS `language_tutor`;

CREATE TABLE `language_tutor` (
  `tutor_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `language_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`tutor_id`,`language_id`),
  KEY `fk_language_agreggation` (`language_id`),
  CONSTRAINT `fk_language_agreggation` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_tutor` FOREIGN KEY (`tutor_id`) REFERENCES `tutor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `language_tutor` */

insert  into `language_tutor`(`tutor_id`,`language_id`) values 
(1,1),
(2,1),
(8,1),
(3,2),
(4,2),
(9,2),
(1,3),
(2,3),
(8,3),
(5,4),
(5,5),
(6,6),
(7,6);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `user_id` bigint unsigned NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `student` */

insert  into `student`(`user_id`,`first_name`,`last_name`,`birthdate`,`creation_date`) values 
(7,'Djordje','Ivanovic','2000-04-19','2022-12-19 23:37:15'),
(9,'Marko','Markovic','1999-05-08','2023-01-13 13:37:13'),
(10,'Danilo','Jankovic','1998-01-01','2023-01-13 13:37:41'),
(11,'Marija','Jokic','2001-05-05','2023-01-13 13:38:07'),
(12,'Jovana','Jovic','2002-06-05','2023-01-13 13:38:25'),
(13,'Milos','Milikic','2003-04-04','2023-01-13 13:38:46'),
(14,'Petar','Peric','2000-09-08','2023-01-13 13:39:04'),
(15,'Aleksa','Aleksic','2000-09-09','2023-01-13 13:39:22'),
(16,'Marko','Maric','2000-09-06','2023-01-13 20:08:22'),
(17,'Vukasin','Simic','1998-07-19','2023-01-14 22:50:42'),
(18,'Miljan','Popovic','1997-07-07','2023-01-20 00:08:04'),
(20,'Danilo','Mikic','2000-01-12','2023-01-22 01:06:23'),
(24,'Mihajlo','Mihajlovic','2023-02-08','2023-02-08 10:26:29');

/*Table structure for table `tutor` */

DROP TABLE IF EXISTS `tutor`;

CREATE TABLE `tutor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tutor` */

insert  into `tutor`(`id`,`first_name`,`last_name`) values 
(1,'Milica','Jovanovic'),
(2,'Jovan','Ivanovic'),
(3,'Marko','Milenovic'),
(4,'Katarina','Milutinovic'),
(5,'Lazar','Antic'),
(6,'Radovan','Radovanovic'),
(7,'Jana','Stanic'),
(8,'Ivona','Milic'),
(9,'Predrag','Rajic');

/*Table structure for table `tutor_assignment` */

DROP TABLE IF EXISTS `tutor_assignment`;

CREATE TABLE `tutor_assignment` (
  `tutor_id` bigint unsigned NOT NULL,
  `course_id` bigint unsigned NOT NULL,
  `course_group_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`tutor_id`,`course_id`,`course_group_id`),
  KEY `course_group_fk` (`course_group_id`),
  KEY `course_gr_fk` (`course_id`,`course_group_id`),
  CONSTRAINT `course_gr_fk` FOREIGN KEY (`course_id`, `course_group_id`) REFERENCES `course_group` (`course_id`, `id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tutor_fk` FOREIGN KEY (`tutor_id`) REFERENCES `tutor` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tutor_assignment` */

insert  into `tutor_assignment`(`tutor_id`,`course_id`,`course_group_id`) values 
(1,1,1),
(2,1,1),
(1,1,2),
(8,1,2),
(6,3,3),
(7,3,3),
(5,4,4),
(5,4,5),
(3,18,8),
(9,18,8),
(3,5,16),
(9,5,16),
(4,5,17),
(7,3,18);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `USERNAME` (`username`),
  CONSTRAINT `min_length_password` CHECK ((length(`username`) >= 5)),
  CONSTRAINT `min_length_username` CHECK ((length(`password`) >= 5))
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`) values 
(7,'djole123','Djole123!'),
(8,'admin','admin'),
(9,'marko123','Marko123$'),
(10,'janko1','Janko123!'),
(11,'marija2','Marija2$'),
(12,'jovana4','Jovana123!'),
(13,'milos321','Milos321!'),
(14,'pera123','Pera123*!'),
(15,'aleksa3','Aleksa32!'),
(16,'maki3','Maki321!'),
(17,'vukan1','Vukan1234!'),
(18,'miljan123','Miljan321!'),
(20,'danilo123','Danilo123!'),
(21,'milena321','Milena321$'),
(22,'janko123','Janko123!'),
(23,'dragan123','Dragan123!'),
(24,'miha123','Mihajlo1!');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
