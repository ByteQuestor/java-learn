/*
Navicat MySQL Data Transfer

Source Server         : cqrstudent
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : students

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2024-12-17 13:23:12
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE students;
USE students;
-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('1', '蔡青茹', '女', '2004-03-01', '13900000001', '北京市海淀区');
INSERT INTO `students` VALUES ('2', 'wzy', '男', '2004-03-10', '13900000002', '上海市浦东新区');
INSERT INTO `students` VALUES ('3', '王五', '男', '2001-05-15', '13900000003', '广州市天河区');
INSERT INTO `students` VALUES ('4', '赵六', '女', '2000-07-20', '13900000004', '深圳市南山区');
INSERT INTO `students` VALUES ('5', '周七', '男', '1998-08-30', '13900000005', '重庆市渝中区');
