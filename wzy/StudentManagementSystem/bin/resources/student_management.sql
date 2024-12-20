-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2024-12-18 15:50:36
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `student_management`
--

-- --------------------------------------------------------

--
-- 表的结构 `studend`
--

CREATE TABLE IF NOT EXISTS `studend` (
  `姓名` varchar(10) CHARACTER SET armscii8 NOT NULL,
  `ID` int(10) NOT NULL,
  `性别` varchar(1) CHARACTER SET armscii8 NOT NULL,
  `院系` varchar(20) CHARACTER SET armscii8 NOT NULL,
  `绩点` int(5) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `studend`
--

INSERT INTO `studend` (`姓名`, `ID`, `性别`, `院系`, `绩点`) VALUES
('zhangsan', 1, 'n', 'xinyuan', 4),
('lisi', 2, 'm', 'jiaowu', 3.5),
('wangwu', 3, 'm', 'meishu', 3.8),
('zhaoliu', 4, 'f', 'wuli', 3.2),
('qianqi', 5, 'f', 'jiaoxue', 3.9),
('sunba', 6, 'm', 'yixiangkan', 3.6),
('chenxiaoluo', 2210702022, 'n', 'yixiangkan', 4),
('zhoujielun', 7, 'f', 'lishi', 3.7);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
