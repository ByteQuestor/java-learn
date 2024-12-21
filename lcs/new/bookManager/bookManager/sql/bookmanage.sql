/*
Navicat MySQL Data Transfer

Source Server         : cqrstudent
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : bookmanage

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2024-12-19 11:23:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- 创建数据库
CREATE DATABASE bookmanage;

-- 使用数据库
USE bookmanage;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bno` int(11) NOT NULL AUTO_INCREMENT,
  `bname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `bstore` int(11) NOT NULL,
  `bsale` int(11) NOT NULL,
  `author` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `publisher` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `publication_year` year(4) DEFAULT NULL,
  PRIMARY KEY (`bno`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想', '100', '50', 'Bruce Eckel', '电子工业出版社', '编程', '99.99', '2006');
INSERT INTO `book` VALUES ('2', '算法导论', '150', '75', 'Thomas H. Cormen', '人民邮电出版社', '计算机科学', '129.50', '2009');
INSERT INTO `book` VALUES ('3', '数据结构与算法分析', '200', '100', 'Mark Allen Weiss', '清华大学出版社', '数据结构', '79.00', '2012');
INSERT INTO `book` VALUES ('4', '人工智能：一种现代的方法', '80', '30', 'Stuart Russell', '机械工业出版社', '人工智能', '89.90', '2015');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_role` ENUM('admin', 'reader') COLLATE utf8_unicode_ci NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,  -- 自动填充当前时间
  `last_login` TIMESTAMP NULL DEFAULT NULL,                -- 默认空
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'wzy', '000000', 'admin', '2024-12-19 10:37:32', null);
INSERT INTO `users` VALUES ('2', '李超水', '000000', 'admin', '2024-12-19 10:37:32', null);
INSERT INTO `users` VALUES ('3', 'test', '000000', 'reader', '2024-12-19 11:10:40', null);
INSERT INTO `users` VALUES ('4', 'lcs', '000000', 'reader', '2024-12-19 11:10:40', null);