-- phpMyAdmin SQL Dump
-- version 2.8.1
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Feb 12, 2008 at 08:05 PM
-- Server version: 5.0.22
-- PHP Version: 5.1.4
-- 
-- Database: `progwt`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `application_cons`
-- 

DROP TABLE IF EXISTS `application_cons`;
CREATE TABLE IF NOT EXISTS `application_cons` (
  `application_id` bigint(20) NOT NULL,
  `sort_order` int(11) NOT NULL,
  `value` varchar(255) collate utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

-- 
-- Table structure for table `application_process_map`
-- 

DROP TABLE IF EXISTS `application_process_map`;
CREATE TABLE IF NOT EXISTS `application_process_map` (
  `id` bigint(20) NOT NULL auto_increment,
  `application_id` bigint(20) NOT NULL,
  `processtype_id` bigint(20) NOT NULL,
  `dueDate` datetime NOT NULL,
  `pctComplete` double NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=33 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `application_pros`
-- 

DROP TABLE IF EXISTS `application_pros`;
CREATE TABLE IF NOT EXISTS `application_pros` (
  `application_id` bigint(20) NOT NULL,
  `sort_order` int(11) NOT NULL,
  `value` varchar(255) collate utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

-- 
-- Table structure for table `application_ratings_map`
-- 

DROP TABLE IF EXISTS `application_ratings_map`;
CREATE TABLE IF NOT EXISTS `application_ratings_map` (
  `id` bigint(20) NOT NULL auto_increment,
  `application_id` bigint(20) NOT NULL,
  `ratingtype_id` bigint(20) NOT NULL,
  `rating` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=40 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `applications`
-- 

DROP TABLE IF EXISTS `applications`;
CREATE TABLE IF NOT EXISTS `applications` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) default '-1' COMMENT 'should be not null, but this errors on cascading deletes.',
  `sort_order` bigint(20) NOT NULL default '-1',
  `school_id` bigint(20) NOT NULL,
  `notes` mediumtext collate utf8_bin,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=209 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `bar`
-- 

DROP TABLE IF EXISTS `bar`;
CREATE TABLE IF NOT EXISTS `bar` (
  `id` bigint(20) NOT NULL auto_increment,
  `foo_id` bigint(20) NOT NULL,
  `sort_order` bigint(20) NOT NULL,
  `name` varchar(255) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `foo`
-- 

DROP TABLE IF EXISTS `foo`;
CREATE TABLE IF NOT EXISTS `foo` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=16 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `forumposts`
-- 

DROP TABLE IF EXISTS `forumposts`;
CREATE TABLE IF NOT EXISTS `forumposts` (
  `id` bigint(20) NOT NULL auto_increment,
  `postString` mediumtext collate utf8_bin NOT NULL,
  `date` datetime default NULL,
  `author_id` bigint(20) NOT NULL,
  `user_id` bigint(20) default NULL,
  `school_id` bigint(20) default NULL,
  `thread_id` bigint(20) default NULL,
  `postTitle` varchar(255) collate utf8_bin default NULL,
  `discriminator` varchar(20) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=152 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `mailing_list`
-- 

DROP TABLE IF EXISTS `mailing_list`;
CREATE TABLE IF NOT EXISTS `mailing_list` (
  `id` bigint(20) NOT NULL auto_increment,
  `email` varchar(255) character set latin1 default NULL,
  `userAgent` varchar(255) character set latin1 default NULL,
  `referer` varchar(255) character set latin1 default NULL,
  `host` varchar(255) character set latin1 default NULL,
  `inviter` bigint(20) default NULL,
  `signedUpUser` bigint(20) default NULL,
  `randomkey` varchar(20) collate utf8_bin default NULL,
  `sentEmailOk` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `processtypes`
-- 

DROP TABLE IF EXISTS `processtypes`;
CREATE TABLE IF NOT EXISTS `processtypes` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) collate utf8_bin NOT NULL,
  `useByDefault` tinyint(1) NOT NULL default '0',
  `status_order` int(11) NOT NULL default '0',
  `percentage` tinyint(1) NOT NULL default '0',
  `dated` tinyint(1) NOT NULL default '1',
  `imageName` varchar(15) collate utf8_bin default NULL,
  `duealert` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `ratingtypes`
-- 

DROP TABLE IF EXISTS `ratingtypes`;
CREATE TABLE IF NOT EXISTS `ratingtypes` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) collate utf8_bin NOT NULL,
  `useByDefault` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `schools`
-- 

DROP TABLE IF EXISTS `schools`;
CREATE TABLE IF NOT EXISTS `schools` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) collate utf8_bin NOT NULL,
  `address` varchar(40) collate utf8_bin NOT NULL,
  `city` varchar(30) collate utf8_bin NOT NULL,
  `state` varchar(20) collate utf8_bin NOT NULL,
  `zip` varchar(11) collate utf8_bin NOT NULL,
  `phone` varchar(20) collate utf8_bin NOT NULL,
  `website` varchar(55) collate utf8_bin NOT NULL,
  `schoolType` varchar(255) collate utf8_bin NOT NULL,
  `awards` varchar(255) collate utf8_bin NOT NULL,
  `campus` varchar(255) collate utf8_bin NOT NULL,
  `students` int(11) NOT NULL,
  `undergrads` int(11) NOT NULL,
  `varsity` varchar(255) collate utf8_bin NOT NULL,
  `ipedsid` int(11) NOT NULL,
  `opeid` int(11) NOT NULL,
  `housing` varchar(255) collate utf8_bin NOT NULL,
  `latitude` double NOT NULL default '-1',
  `longitude` double NOT NULL default '-1',
  `popularityCounter` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2375 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `user_priorities_map`
-- 

DROP TABLE IF EXISTS `user_priorities_map`;
CREATE TABLE IF NOT EXISTS `user_priorities_map` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) NOT NULL,
  `ratingtype_id` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `user_processtype`
-- 

DROP TABLE IF EXISTS `user_processtype`;
CREATE TABLE IF NOT EXISTS `user_processtype` (
  `id` bigint(20) NOT NULL auto_increment,
  `sort_order` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `process_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=161 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `user_ratingtype`
-- 

DROP TABLE IF EXISTS `user_ratingtype`;
CREATE TABLE IF NOT EXISTS `user_ratingtype` (
  `id` bigint(20) NOT NULL auto_increment,
  `sort_order` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `rating_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=107 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `users`
-- 

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint(20) NOT NULL auto_increment,
  `user_name` varchar(255) collate utf8_bin NOT NULL,
  `nickname` varchar(255) collate utf8_bin NOT NULL,
  `password` varchar(255) collate utf8_bin default NULL,
  `email` varchar(255) collate utf8_bin default NULL,
  `enabled` varchar(1) collate utf8_bin NOT NULL default '1',
  `supervisor` varchar(1) collate utf8_bin NOT NULL default '0',
  `invitations` int(11) NOT NULL default '0',
  `dateCreated` datetime default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=19 ;
