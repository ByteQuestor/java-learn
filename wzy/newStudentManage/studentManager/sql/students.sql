/*
Navicat MySQL Data Transfer

Source Server         : cqrstudent
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : students

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2024-12-20 22:00:59
*/

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE students;
USE students;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `course_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `teacher_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'Java编程基础', '101', '李老师');
INSERT INTO `course` VALUES ('2', '数据结构与算法', '102', '王老师');
INSERT INTO `course` VALUES ('3', '数据库原理', '103', '赵老师');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `score` decimal(5,2) NOT NULL,
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1', '1', '1', '85.50');
INSERT INTO `grade` VALUES ('2', '1', '2', '78.00');
INSERT INTO `grade` VALUES ('3', '1', '3', '90.00');
INSERT INTO `grade` VALUES ('4', '2', '1', '88.00');
INSERT INTO `grade` VALUES ('5', '2', '2', '92.00');
INSERT INTO `grade` VALUES ('6', '2', '3', '80.50');
INSERT INTO `grade` VALUES ('7', '3', '1', '91.50');
INSERT INTO `grade` VALUES ('8', '3', '2', '84.00');
INSERT INTO `grade` VALUES ('9', '3', '3', '86.00');
INSERT INTO `grade` VALUES ('10', '4', '1', '76.50');
INSERT INTO `grade` VALUES ('11', '4', '2', '83.00');
INSERT INTO `grade` VALUES ('12', '4', '3', '88.50');
INSERT INTO `grade` VALUES ('13', '5', '1', '81.00');
INSERT INTO `grade` VALUES ('14', '5', '2', '79.00');
INSERT INTO `grade` VALUES ('15', '5', '3', '92.50');
INSERT INTO `grade` VALUES ('16', '6', '1', '89.00');
INSERT INTO `grade` VALUES ('17', '6', '2', '84.00');
INSERT INTO `grade` VALUES ('18', '6', '3', '87.00');
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
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES ('1', '蔡菁茹', '女', '2004-03-01', '13900000001', '北京市海淀区', '000000');
INSERT INTO `students` VALUES ('2', '任紫阳', '男', '2004-03-10', '13900000002', '上海市浦东新区', '000000');
INSERT INTO `students` VALUES ('3', '李超水', '女', '2003-05-15', '13900000003', '广州市天河区', '000000');
INSERT INTO `students` VALUES ('4', '王子阳', '男', '2002-07-20', '13900000004', '深圳市南山区', '000000');
INSERT INTO `students` VALUES ('5', '牛亚超', '男', '1998-08-30', '13900000005', '重庆市渝中区', '333333');
INSERT INTO `students` VALUES ('6', '韩家骏', '男', '2002-07-11', '13900000006', '河南省驻马店市', '000000');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '蔡菁茹', 'cjr', '000000', '1');
INSERT INTO `user` VALUES ('2', '任紫阳', 'rzy', '000000', '1');
INSERT INTO `user` VALUES ('3', '王子阳', 'wzy', '000000', '2');
