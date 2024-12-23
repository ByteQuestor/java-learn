/*
Navicat MySQL Data Transfer

Source Server         : cqrstudent
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : studentmanage

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2024-12-11 11:56:04
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE studentmanage
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

use studentmanage;
-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(100) NOT NULL,
  `teacher_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES ('1', 'Java 编程', '李老师');
INSERT INTO `courses` VALUES ('2', '数据库原理', '王老师');
INSERT INTO `courses` VALUES ('3', '数据结构', '赵老师');
INSERT INTO `courses` VALUES ('4', '操作系统', '周老师');
INSERT INTO `courses` VALUES ('5', '软件工程', '孙老师');

-- ----------------------------
-- Table structure for grades
-- ----------------------------
DROP TABLE IF EXISTS `grades`;
CREATE TABLE `grades` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `grade` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of grades
-- ----------------------------
INSERT INTO `grades` VALUES ('1', '1', '1', '85.50');
INSERT INTO `grades` VALUES ('2', '1', '2', '90.00');
INSERT INTO `grades` VALUES ('3', '2', '1', '78.00');
INSERT INTO `grades` VALUES ('4', '2', '3', '88.50');
INSERT INTO `grades` VALUES ('5', '3', '4', '92.00');
INSERT INTO `grades` VALUES ('6', '4', '2', '81.00');
INSERT INTO `grades` VALUES ('7', '5', '5', '70.50');
INSERT INTO `grades` VALUES ('8', '1', '3', '94.50');
INSERT INTO `grades` VALUES ('9', '3', '5', '85.00');
INSERT INTO `grades` VALUES ('10', '4', '1', '77.00');

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
INSERT INTO `students` VALUES ('1', '王子阳', '男', '2000-01-01', '13900000001', '北京市海淀区');
INSERT INTO `students` VALUES ('2', '蔡青茹', '女', '2004-03-10', '13900000002', '上海市浦东新区');
INSERT INTO `students` VALUES ('3', '王五', '男', '2001-05-15', '13900000003', '广州市天河区');
INSERT INTO `students` VALUES ('4', '赵六', '女', '2000-07-20', '13900000004', '深圳市南山区');
INSERT INTO `students` VALUES ('5', '周七', '男', '1998-08-30', '13900000005', '重庆市渝中区');
