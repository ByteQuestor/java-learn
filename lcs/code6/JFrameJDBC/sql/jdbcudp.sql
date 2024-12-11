/*
Navicat MySQL Data Transfer

Source Server         : lcsbookinfo
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : jdbcudp

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2024-12-11 14:11:20
*/

SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE jdbcudp;

USE jdbcudp;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100)
);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('5', 'lcs', '000000');
