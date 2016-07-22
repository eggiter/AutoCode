-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.13-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 selection 的数据库结构
DROP DATABASE IF EXISTS `selection`;
CREATE DATABASE IF NOT EXISTS `selection` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `selection`;


-- 导出  表 selection.course 结构
DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_name` varchar(64) DEFAULT NULL COMMENT '课程名称',
  `course_number` varchar(32) DEFAULT NULL COMMENT '课程编码',
  `credit` double DEFAULT NULL COMMENT '学分',
  `period` int(11) DEFAULT NULL COMMENT '学时',
  `course_type` int(11) DEFAULT NULL COMMENT '课程类别',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `meta_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `meta_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_mark` int(11) DEFAULT NULL COMMENT '标记删除',
  `meta_deleted` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `course_number` (`course_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程';

-- 正在导出表  selection.course 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


-- 导出  表 selection.selection 结构
DROP TABLE IF EXISTS `selection`;
CREATE TABLE IF NOT EXISTS `selection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_number` char(10) DEFAULT NULL COMMENT '学生学号',
  `student_name` varchar(64) DEFAULT NULL COMMENT '学生姓名',
  `course_number` varchar(32) DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(64) DEFAULT NULL COMMENT '课程名称',
  `score` double DEFAULT NULL COMMENT '分数',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `meta_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `meta_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_mark` int(11) DEFAULT NULL COMMENT '标记删除',
  `meta_deleted` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课';

-- 正在导出表  selection.selection 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `selection` DISABLE KEYS */;
/*!40000 ALTER TABLE `selection` ENABLE KEYS */;


-- 导出  表 selection.student 结构
DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `student_number` char(10) DEFAULT NULL COMMENT '学号',
  `school` varchar(128) DEFAULT NULL COMMENT '学院',
  `major` varchar(128) DEFAULT NULL COMMENT '专业',
  `enrollment_date` datetime DEFAULT NULL COMMENT '入学日期',
  `length_of_schooling` int(11) DEFAULT NULL COMMENT '学制',
  `student_state` int(11) DEFAULT NULL COMMENT '状态（1：已注册；2：未注册）',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `meta_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `meta_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_mark` int(11) DEFAULT NULL COMMENT '标记删除',
  `meta_deleted` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_number` (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生';

-- 正在导出表  selection.student 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
