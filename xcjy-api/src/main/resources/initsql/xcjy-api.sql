/*
Navicat MySQL Data Transfer

Source Server         : 于哥数据库
Source Server Version : 50718
Source Host           : www.qianhengnet.com:3306
Source Database       : education

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-09 22:51:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `apln_back_money`
-- ----------------------------
DROP TABLE IF EXISTS `apln_back_money`;
CREATE TABLE `apln_back_money` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '所属校区ID',
  `student_id` varchar(128) NOT NULL COMMENT '退费学生ID',
  `application_user_id` varchar(128) NOT NULL COMMENT '发起申请人ID',
  `return_amount` int(11) NOT NULL COMMENT '退费金额',
  `application_status` varchar(64) NOT NULL COMMENT '申请状态',
  `application_time` datetime NOT NULL COMMENT '申请时间',
  `remark` varchar(1024) DEFAULT NULL COMMENT '退费申请描述',
  `back_money_type` varchar(32) DEFAULT NULL COMMENT '退费类型',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of apln_back_money
-- ----------------------------
INSERT INTO `apln_back_money` VALUES ('1504959801564', '1504327016935', '1504332406428', '1504330295620', '100', 'AUDIT_FAIL', '2017-09-09 20:30:14', 'test', 'COUNSELOR', '2017-09-09 20:23:22', '2017-09-09 20:30:14', '');
INSERT INTO `apln_back_money` VALUES ('1504961253676', '1504327016935', '1504340278591', '1504405787561', '1000', 'AUDIT_FAIL', '2017-09-09 20:48:07', '呵呵', 'COUNSELOR', '2017-09-09 20:47:34', '2017-09-09 20:48:07', '');
INSERT INTO `apln_back_money` VALUES ('1504961888797', '1504327016935', '1504340278591', '1504405787561', '1000', 'AUDIT_FAIL', '2017-09-09 21:19:07', '', 'COUNSELOR', '2017-09-09 20:58:09', '2017-09-09 21:19:07', '');
INSERT INTO `apln_back_money` VALUES ('1504963061664', '1504327016935', '1504962815131', '1504405787561', '1000', 'AUDIT_FAIL', '2017-09-09 21:20:15', '任性呗', 'COUNSELOR', '2017-09-09 21:17:42', '2017-09-09 21:20:15', '');
INSERT INTO `apln_back_money` VALUES ('1504963234093', '1504327016935', '1504962815131', '1504405787561', '1000', 'AUDIT_FAIL', '2017-09-09 21:59:57', '就是要退款', 'COUNSELOR', '2017-09-09 21:20:34', '2017-09-09 21:59:57', '');
INSERT INTO `apln_back_money` VALUES ('1504965111701', '1504327016935', '1504332588960', '1504330563343', '100', 'AUDIT_SUCCESS', '2017-09-09 22:30:30', '退吧', 'STMANAGER', '2017-09-09 21:51:52', '2017-09-09 22:30:30', '');

-- ----------------------------
-- Table structure for `apln_change_school`
-- ----------------------------
DROP TABLE IF EXISTS `apln_change_school`;
CREATE TABLE `apln_change_school` (
  `id` varchar(128) NOT NULL,
  `student_id` varchar(128) NOT NULL COMMENT '转校学生ID',
  `from_school_id` varchar(128) NOT NULL COMMENT '原校区ID',
  `to_school_id` varchar(128) NOT NULL COMMENT '新的校区ID',
  `application_user_id` varchar(128) NOT NULL COMMENT '申请用户ID',
  `application_status` varchar(64) NOT NULL COMMENT '申请状态',
  `application_time` datetime DEFAULT NULL COMMENT '最终审核时间',
  `remark` varchar(1024) DEFAULT NULL COMMENT '转校描述',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of apln_change_school
-- ----------------------------

-- ----------------------------
-- Table structure for `counselor_student`
-- ----------------------------
DROP TABLE IF EXISTS `counselor_student`;
CREATE TABLE `counselor_student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL COMMENT '校区ID',
  `employee_id` varchar(128) NOT NULL COMMENT '员工ID',
  `student_id` varchar(128) NOT NULL COMMENT '学生ID',
  `money` int(11) DEFAULT NULL COMMENT '金额',
  `has_back` int(11) DEFAULT '0' COMMENT '退费金额',
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of counselor_student
-- ----------------------------
INSERT INTO `counselor_student` VALUES ('1504336655840', '1504327016935', '1504330193825', '1504332406428', '10000', '0', 'HAS_PAY', '2017-09-02 15:17:36', '2017-09-02 16:03:48', '');
INSERT INTO `counselor_student` VALUES ('1504336923989', '1504327016935', '1504330193825', '1504332508879', '0', '0', 'NO_PAY', '2017-09-02 15:22:04', '2017-09-02 15:30:13', '');
INSERT INTO `counselor_student` VALUES ('1504337122803', '1504327016935', '1504330295611', '1504336120361', '0', '0', 'CONNECTION_NO', '2017-09-02 15:25:23', '2017-09-02 15:25:23', '');
INSERT INTO `counselor_student` VALUES ('1504337122805', '1504327016935', '1504330295611', '1504336486093', '0', '0', 'CONNECTION_NO', '2017-09-02 15:25:23', '2017-09-02 15:25:23', '');
INSERT INTO `counselor_student` VALUES ('1504337122808', '1504327016935', '1504330295611', '1504335614433', '0', '0', 'CONNECTION_NO', '2017-09-02 15:25:23', '2017-09-02 15:25:23', '');
INSERT INTO `counselor_student` VALUES ('1504337851257', '1504327016935', '1504330193825', '1504337851250', '0', '0', 'CONNECTION_NO', '2017-09-02 15:37:31', '2017-09-02 15:37:31', '');
INSERT INTO `counselor_student` VALUES ('1504338198382', '1504327016935', '1504330193825', '1504338198378', '0', '0', 'CONNECTION_NO', '2017-09-02 15:43:18', '2017-09-02 15:43:18', '');
INSERT INTO `counselor_student` VALUES ('1504338613673', '1504327016935', '1504330193825', '1504332588960', '1000', '0', 'HAS_PAY', '2017-09-02 15:50:14', '2017-09-03 12:34:53', '');
INSERT INTO `counselor_student` VALUES ('1504340300276', '1504327016935', '1504330193825', '1504340216219', '0', '0', 'CONNECTION_NO', '2017-09-02 16:18:20', '2017-09-02 16:18:20', '');
INSERT INTO `counselor_student` VALUES ('1504340439074', '1504327016935', '1504330295611', '1504339681838', '0', '0', 'NO_PAY', '2017-09-02 16:20:39', '2017-09-09 13:40:13', '');
INSERT INTO `counselor_student` VALUES ('1504404010762', '1504327016935', '1504330193825', '1504404010750', '0', '0', 'NO_PAY', '2017-09-03 10:00:11', '2017-09-03 10:00:26', '');
INSERT INTO `counselor_student` VALUES ('1504405934666', '1504327016935', '1504405787550', '1504405934662', '0', '0', 'CONNECTION_NO', '2017-09-03 10:32:15', '2017-09-03 10:32:15', '');
INSERT INTO `counselor_student` VALUES ('1504436969399', '1504327016935', '1504330193825', '1504436969394', '0', null, 'CONNECTION_NO', '2017-09-03 19:09:29', '2017-09-03 19:09:29', '');
INSERT INTO `counselor_student` VALUES ('1504717397143', '1504327016935', '1504330193825', '1504717397139', '0', null, 'CONNECTION_NO', '2017-09-07 01:03:17', '2017-09-07 01:03:17', '');
INSERT INTO `counselor_student` VALUES ('1504717514662', '1504327016935', '1504330193825', '1504717514659', '0', null, 'CONNECTION_NO', '2017-09-07 01:05:15', '2017-09-07 01:05:15', '');
INSERT INTO `counselor_student` VALUES ('1504717597490', '1504327016935', '1504330193825', '1504717597487', '0', null, 'CONNECTION_NO', '2017-09-07 01:06:37', '2017-09-07 01:06:37', '');
INSERT INTO `counselor_student` VALUES ('1504717652348', '1504327016935', '1504330193825', '1504717652345', '0', null, 'CONNECTION_NO', '2017-09-07 01:07:32', '2017-09-07 01:07:32', '');
INSERT INTO `counselor_student` VALUES ('1504948331543', '1504327016935', '1504405787550', '1504340278591', '8000', null, 'HAS_PAY', '2017-09-09 17:12:12', '2017-09-09 17:14:19', '');
INSERT INTO `counselor_student` VALUES ('1504962880547', '1504327016935', '1504405787550', '1504962815131', '12000', null, 'HAS_PAY', '2017-09-09 21:14:41', '2017-09-09 21:17:15', '');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL,
  `grade_id` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '课程名称',
  `type` varchar(64) DEFAULT NULL COMMENT '课程类型',
  `student_num` int(11) DEFAULT NULL COMMENT '学生人数',
  `price` int(11) DEFAULT NULL COMMENT '课程价格',
  `study_hour` int(11) DEFAULT NULL COMMENT '课时',
  `selected_num` int(11) DEFAULT NULL COMMENT '选择人数',
  `back_num` int(11) DEFAULT NULL COMMENT '退课人数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1504341809885', '1504327016935', '1504341718261', '英语特训班1号课程', 'ONETOONE', '20', '200', '15', '0', '0', '2017-09-02 16:43:30', '2017-09-02 16:43:30', '');
INSERT INTO `course` VALUES ('1504361477934', '1504327016935', '1504341718261', '数学高阶班', 'BOUTIQUEGROUP', '20', '200', '10', '0', '0', '2017-09-02 22:11:18', '2017-09-02 22:11:18', '');
INSERT INTO `course` VALUES ('1504407064902', '1504327016935', '1504341718261', '一对一', 'ONETOONE', '1', '200', '2', '0', '0', '2017-09-03 10:51:05', '2017-09-03 10:51:05', '');

-- ----------------------------
-- Table structure for `course_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `course_schedule`;
CREATE TABLE `course_schedule` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '校区ID',
  `course_id` varchar(128) NOT NULL COMMENT '课程ID',
  `employee_id` varchar(128) NOT NULL COMMENT '教师ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `study_time` int(11) NOT NULL COMMENT '课时',
  `finish` bit(1) NOT NULL COMMENT '是否完成',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_schedule
-- ----------------------------
INSERT INTO `course_schedule` VALUES ('1504361833731', '1504327016935', '1504341809885', '1504330804053', '2017-09-02 22:17:07', '2017-09-03 22:17:07', '10', '', '2017-09-02 22:17:14', '2017-09-02 22:17:14', '');
INSERT INTO `course_schedule` VALUES ('1504407278898', '1504327016935', '1504407064902', '1504330804053', '2017-09-03 10:54:24', '2017-09-04 10:54:24', '2', '', '2017-09-03 10:54:39', '2017-09-03 10:54:39', '');
INSERT INTO `course_schedule` VALUES ('1504408298550', '1504327016935', '1504407064902', '1504330804053', '2017-09-03 11:11:26', '2017-09-04 11:11:26', '2', '', '2017-09-03 11:11:39', '2017-09-03 11:11:39', '');

-- ----------------------------
-- Table structure for `course_schedule_student`
-- ----------------------------
DROP TABLE IF EXISTS `course_schedule_student`;
CREATE TABLE `course_schedule_student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL,
  `course_schedule_id` varchar(128) NOT NULL COMMENT '课表ID',
  `student_id` varchar(128) NOT NULL COMMENT '学生ID',
  `finish` bit(1) NOT NULL COMMENT '是否完成',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_schedule_student
-- ----------------------------
INSERT INTO `course_schedule_student` VALUES ('1504361833743', '1504327016935', '1504361833731', '1504332406428', '', '2017-09-02 22:17:14', '2017-09-02 22:17:14', '');
INSERT INTO `course_schedule_student` VALUES ('1504407278903', '1504327016935', '1504407278898', '1504332406428', '', '2017-09-03 10:54:39', '2017-09-03 10:55:07', '');
INSERT INTO `course_schedule_student` VALUES ('1504408298554', '1504327016935', '1504408298550', '1504332406428', '', '2017-09-03 11:11:39', '2017-09-03 11:11:53', '');

-- ----------------------------
-- Table structure for `course_student`
-- ----------------------------
DROP TABLE IF EXISTS `course_student`;
CREATE TABLE `course_student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '校区ID',
  `student_id` varchar(128) NOT NULL COMMENT '学生ID',
  `course_id` varchar(128) NOT NULL COMMENT '课程ID',
  `buy_hour` int(11) NOT NULL COMMENT '购买的课时数',
  `used_hour` int(11) NOT NULL COMMENT '已使用课时',
  `score` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_student
-- ----------------------------
INSERT INTO `course_student` VALUES ('1504342688177', '1504327016935', '1504332406428', '1504341809885', '10', '10', '990', '2017-09-02 16:58:08', '2017-09-03 10:47:19', '');
INSERT INTO `course_student` VALUES ('1504407227042', '1504327016935', '1504332406428', '1504407064902', '4', '4', '81', '2017-09-03 10:53:47', '2017-09-03 11:18:56', '');

-- ----------------------------
-- Table structure for `course_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `course_teacher`;
CREATE TABLE `course_teacher` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '校区ID',
  `course_id` varchar(128) NOT NULL COMMENT '课程ID',
  `teacher_id` varchar(128) NOT NULL COMMENT '教师ID',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_teacher
-- ----------------------------
INSERT INTO `course_teacher` VALUES ('1504341887728', '1504327016935', '1504341809885', '1504330804053', '2017-09-02 16:44:48', '2017-09-02 16:44:48', '');
INSERT INTO `course_teacher` VALUES ('1504361567345', '1504327016935', '1504361477934', '1504330804053', '2017-09-02 22:12:47', '2017-09-02 22:12:47', '');
INSERT INTO `course_teacher` VALUES ('1504407075620', '1504327016935', '1504407064902', '1504330804053', '2017-09-03 10:51:16', '2017-09-03 10:51:16', '');

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `id_card` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `education` varchar(64) DEFAULT NULL COMMENT '学历',
  `graduation_school` varchar(128) DEFAULT NULL COMMENT '毕业学校',
  `specialty` varchar(128) DEFAULT NULL COMMENT '专业',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `clamant_name` varchar(128) DEFAULT NULL COMMENT '紧急联系人名称',
  `clamant_phone` varchar(32) DEFAULT NULL COMMENT '紧急联系人电话',
  `address` varchar(256) DEFAULT NULL COMMENT '家庭地址',
  `remark` varchar(2048) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1504327355065', null, '人事经理', 'MALE', '1982-09-09', '362329198209098888', null, null, '人力资源', '18818181818', 'yjh2332@163.com', null, null, '北京市昌平区', '这个人是个教师', '2017-09-02 12:42:35', '2017-09-03 16:41:55', '');
INSERT INTO `employee` VALUES ('1504330074531', '', '总校长', 'MALE', '1991-12-12', '362626199112129999', '', '', '', '18818181111', '2222@163.com', '', '', '北京昌平', '', '2017-09-02 13:27:55', '2017-09-02 13:27:55', '');
INSERT INTO `employee` VALUES ('1504330193825', '1504327016935', '咨询师', 'MALE', '1988-12-12', '362626198812127777', '', '', '', '18881726666', '333@qq.com', '', '', '北京昌平', '', '2017-09-02 13:29:54', '2017-09-03 10:27:08', '');
INSERT INTO `employee` VALUES ('1504330295611', '1504327016935', '咨询主任', 'MALE', '1978-09-09', '353535197809098888', '', '', '', '18716166666', '333@qq.com', '', '', '北京昌平', '', '2017-09-02 13:31:36', '2017-09-02 13:31:36', '');
INSERT INTO `employee` VALUES ('1504330362790', '', '咨询总监', 'MALE', '1987-07-18', '362662198707181111', '', '', '', '13818189976', '2212@qq.com', '', '', '北京昌平', '', '2017-09-02 13:32:43', '2017-09-02 13:32:43', '');
INSERT INTO `employee` VALUES ('1504330563334', '1504327016935', '学管师', 'MALE', '1988-11-11', '362327198811110000', '', '', '', '13836361781', '231@qq.com', '', '', '北京昌平', '', '2017-09-02 13:36:03', '2017-09-02 13:36:03', '');
INSERT INTO `employee` VALUES ('1504330735316', '1504327016935', '教管主任', 'MALE', '1987-08-08', '362329198708089999', '', '', '', '13919199999', 'asd@qq.com', '', '', '北京昌平', '', '2017-09-02 13:38:55', '2017-09-02 13:38:55', '');
INSERT INTO `employee` VALUES ('1504330804053', '1504327016935', '老师', 'MALE', '1997-07-23', '362222199707231198', '', '', '', '18817776565', '222@qq.com', '', '', '北京昌平', '', '2017-09-02 13:40:04', '2017-09-02 13:40:04', '');
INSERT INTO `employee` VALUES ('1504330859110', '1504327016935', '教研主任', 'MALE', '1978-11-30', '362329197811300000', '', '', '', '18765652413', '2222@qq.com', '', '', '北京昌平', '', '2017-09-02 13:40:59', '2017-09-02 13:40:59', '');
INSERT INTO `employee` VALUES ('1504330950321', '1504327016935', '昌平校区分校长', 'MALE', '1977-01-23', '362329197701239999', '', '', '', '18976761616', '212@qq.com', '', '', '北京昌平', '', '2017-09-02 13:42:30', '2017-09-02 13:42:30', '');
INSERT INTO `employee` VALUES ('1504331045585', '', '财务', 'FEMALE', '1990-09-22', '362329199009227777', '', '', '', '13851885188', '222@qq.com', '', '', '北京昌平', '', '2017-09-02 13:44:06', '2017-09-02 16:34:07', '');
INSERT INTO `employee` VALUES ('1504331141043', '', '人事专员出纳', 'FEMALE', null, '362329199109090909', '', '', '', '18919199999', '222ww@qq.com', '', '', '北京昌平', '', '2017-09-02 13:45:41', '2017-09-02 13:46:37', '');
INSERT INTO `employee` VALUES ('1504342193415', '1504327016935', '测试咨询总监1', 'MALE', '1991-12-22', '311222199112226515', '', '', '', '18876223123', 'yjh2332@163.com', '', '', 'saodfh', '', '2017-09-02 16:49:53', '2017-09-03 17:06:43', '');
INSERT INTO `employee` VALUES ('1504342321740', null, '测试咨询总监2', 'MALE', '1991-12-22', '331112199112226515', '', '', '', '18822223832', 'uads', '', '', 'apfohb', '', '2017-09-02 16:52:02', '2017-09-03 17:06:40', '');
INSERT INTO `employee` VALUES ('1504405787550', '1504327016935', '子寻', 'MALE', '1988-08-22', '230230198808220030', '', '', '', '15555556666', '58782620@qq.com', '', '', '烟台开发区', '', '2017-09-03 10:29:48', '2017-09-03 10:29:48', '');
INSERT INTO `employee` VALUES ('1504431099107', '1504330158156', '测试员工', 'MALE', '1999-12-22', '350521199912227777', '', '', '', '18876551111', 'yjh2332@163.com', '', '', 'sldhalkjasf', '', '2017-09-03 17:31:39', '2017-09-03 17:35:44', '');
INSERT INTO `employee` VALUES ('1504431376318', '1504327016935', '测试员工', 'MALE', '1992-12-22', '390521199212227777', '', '', '', '18850447761', 'yjh2332@163.com', '', '', 'sadlj', '', '2017-09-03 17:36:16', '2017-09-03 17:36:16', '');
INSERT INTO `employee` VALUES ('1504448315614', '1504327016935', 'Mr.Wang', 'MALE', '2000-12-22', '351222200012226515', '', '', '', '18821122222', 'yjh2332@163.com', '', '', '阿松地发挥', '', '2017-09-03 22:18:36', '2017-09-03 22:19:33', '');
INSERT INTO `employee` VALUES ('1504448448755', '1504327016935', '教师小鲜肉', 'MALE', '1991-12-22', '311222199112229999', '', '', '', '18854773855', 'yjh2332@gmail.com', '', '', 'sadiofjlk', '', '2017-09-03 22:20:49', '2017-09-03 22:20:49', '');

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '年级名称',
  `price` int(11) NOT NULL COMMENT '价格',
  `remark` varchar(1024) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('1504341718261', '1504327016935', '英语特训班', '200', '200块一节课', '2017-09-02 16:41:58', '2017-09-03 20:51:01', '');
INSERT INTO `grade` VALUES ('1504361543301', '1504327016935', '数学特训班', '1200', '只收数学80分以上的学员', '2017-09-02 22:12:23', '2017-09-02 22:12:23', '');

-- ----------------------------
-- Table structure for `process_log`
-- ----------------------------
DROP TABLE IF EXISTS `process_log`;
CREATE TABLE `process_log` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL,
  `student_id` varchar(128) NOT NULL,
  `application_id` varchar(128) NOT NULL COMMENT '申请ID',
  `type` varchar(64) NOT NULL COMMENT '申请类型',
  `process_num` tinyint(4) NOT NULL COMMENT '当前是第几步审核',
  `handler_user_id` varchar(128) NOT NULL COMMENT '处理人ID',
  `handler_status` varchar(64) NOT NULL COMMENT '处理状态',
  `handler_time` datetime DEFAULT NULL COMMENT '处理时间',
  `remark` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of process_log
-- ----------------------------
INSERT INTO `process_log` VALUES ('1504959807044', '1504327016935', '1504332406428', '1504959801564', 'BACK_MONEY', '0', '1504330295620', 'AUDIT_SUCCESS', '2017-09-09 20:25:57', '这个试一下能过不', '2017-09-09 20:23:27', '2017-09-09 20:25:57', '');
INSERT INTO `process_log` VALUES ('1504959960581', '1504327016935', '1504332406428', '1504959801564', 'BACK_MONEY', '1', '1504330950330', 'AUDIT_SUCCESS', '2017-09-09 20:27:41', '这个试一下能过不撒大声地所', '2017-09-09 20:26:01', '2017-09-09 20:27:41', '');
INSERT INTO `process_log` VALUES ('1504960060697', null, '1504332406428', '1504959801564', 'BACK_MONEY', '2', '1504330362809', 'AUDIT_FAIL', '2017-09-09 20:30:14', '失败了，不审核了，这个猪，100块也退', '2017-09-09 20:27:41', '2017-09-09 20:30:14', '');
INSERT INTO `process_log` VALUES ('1504961253693', '1504327016935', '1504340278591', '1504961253676', 'BACK_MONEY', '0', '1504330295620', 'AUDIT_FAIL', '2017-09-09 20:48:07', '不行', '2017-09-09 20:47:34', '2017-09-09 20:48:07', '');
INSERT INTO `process_log` VALUES ('1504961888803', '1504327016935', '1504340278591', '1504961888797', 'BACK_MONEY', '0', '1504330295620', 'AUDIT_SUCCESS', '2017-09-09 20:58:44', null, '2017-09-09 20:58:09', '2017-09-09 20:58:44', '');
INSERT INTO `process_log` VALUES ('1504961923849', '1504327016935', '1504340278591', '1504961888797', 'BACK_MONEY', '1', '1504330950330', 'AUDIT_SUCCESS', '2017-09-09 20:59:19', '分校长通过', '2017-09-09 20:58:44', '2017-09-09 20:59:19', '');
INSERT INTO `process_log` VALUES ('1504961959148', null, '1504340278591', '1504961888797', 'BACK_MONEY', '2', '1504330362809', 'AUDIT_FAIL', '2017-09-09 21:19:07', null, '2017-09-09 20:59:19', '2017-09-09 21:19:07', '');
INSERT INTO `process_log` VALUES ('1504963061669', '1504327016935', '1504962815131', '1504963061664', 'BACK_MONEY', '0', '1504330295620', 'AUDIT_SUCCESS', '2017-09-09 21:18:02', '去吧 皮卡丘', '2017-09-09 21:17:42', '2017-09-09 21:18:02', '');
INSERT INTO `process_log` VALUES ('1504963082010', '1504327016935', '1504962815131', '1504963061664', 'BACK_MONEY', '1', '1504330950330', 'AUDIT_SUCCESS', '2017-09-09 21:18:22', '去吧 皮卡丘', '2017-09-09 21:18:02', '2017-09-09 21:18:22', '');
INSERT INTO `process_log` VALUES ('1504963101507', null, '1504962815131', '1504963061664', 'BACK_MONEY', '2', '1504330362809', 'AUDIT_FAIL', '2017-09-09 21:20:15', null, '2017-09-09 21:18:22', '2017-09-09 21:20:15', '');
INSERT INTO `process_log` VALUES ('1504963234100', '1504327016935', '1504962815131', '1504963234093', 'BACK_MONEY', '0', '1504330295620', 'AUDIT_SUCCESS', '2017-09-09 21:23:38', '好的', '2017-09-09 21:20:34', '2017-09-09 21:23:38', '');
INSERT INTO `process_log` VALUES ('1504963417971', '1504327016935', '1504962815131', '1504963234093', 'BACK_MONEY', '1', '1504330950330', 'AUDIT_SUCCESS', '2017-09-09 21:23:53', 'OK', '2017-09-09 21:23:38', '2017-09-09 21:23:53', '');
INSERT INTO `process_log` VALUES ('1504963433030', null, '1504962815131', '1504963234093', 'BACK_MONEY', '2', '1504330362809', 'AUDIT_SUCCESS', '2017-09-09 21:24:19', null, '2017-09-09 21:23:53', '2017-09-09 21:24:19', '');
INSERT INTO `process_log` VALUES ('1504963459283', null, '1504962815131', '1504963234093', 'BACK_MONEY', '3', '1504330074547', 'AUDIT_SUCCESS', '2017-09-09 21:25:59', '去吧 劈落秋', '2017-09-09 21:24:19', '2017-09-09 21:25:59', '');
INSERT INTO `process_log` VALUES ('1504963558737', null, '1504962815131', '1504963234093', 'BACK_MONEY', '4', '1504331045594', 'AUDIT_FAIL', '2017-09-09 21:59:57', '不行', '2017-09-09 21:25:59', '2017-09-09 21:59:57', '');
INSERT INTO `process_log` VALUES ('1504965111722', '1504327016935', '1504332588960', '1504965111701', 'BACK_MONEY', '0', '1504330735325', 'AUDIT_SUCCESS', '2017-09-09 21:58:18', '去吧 皮卡丘', '2017-09-09 21:51:52', '2017-09-09 21:58:18', '');
INSERT INTO `process_log` VALUES ('1504965498135', '1504327016935', '1504332588960', '1504965111701', 'BACK_MONEY', '1', '1504330950330', 'AUDIT_SUCCESS', '2017-09-09 21:58:35', '去吧', '2017-09-09 21:58:18', '2017-09-09 21:58:35', '');
INSERT INTO `process_log` VALUES ('1504965514959', null, '1504332588960', '1504965111701', 'BACK_MONEY', '2', '1504330074547', 'AUDIT_SUCCESS', '2017-09-09 21:58:48', '去吧', '2017-09-09 21:58:35', '2017-09-09 21:58:48', '');
INSERT INTO `process_log` VALUES ('1504965528122', null, '1504332588960', '1504965111701', 'BACK_MONEY', '3', '1504331045594', 'AUDIT_SUCCESS', '2017-09-09 22:30:30', '去吧 去吧', '2017-09-09 21:58:48', '2017-09-09 22:30:30', '');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(128) NOT NULL COMMENT '唯一标示',
  `school_id` varchar(128) DEFAULT NULL,
  `name` varchar(128) NOT NULL COMMENT '名称',
  `remark` varchar(1024) DEFAULT NULL COMMENT '描述信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` varchar(128) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '校区名称',
  `remark` varchar(2048) DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1504327016935', '昌平校区', '这是今年新加的校区哈哈哈', '2017-09-02 12:36:57', '2017-09-02 12:36:57', '');
INSERT INTO `school` VALUES ('1504330158156', '校区测试1', '新建校区测试\n', '2017-09-02 13:29:18', '2017-09-02 13:29:18', '');
INSERT INTO `school` VALUES ('1504330627723', '测试校区2', '测试新增校区立即显示', '2017-09-02 13:37:08', '2017-09-02 13:37:08', '');
INSERT INTO `school` VALUES ('1504330670483', '测试校区3', '测试立即显示新增校区', '2017-09-02 13:37:50', '2017-09-02 13:37:50', '');
INSERT INTO `school` VALUES ('1504330726331', '测试校区4', '立即显示校区测试4', '2017-09-02 13:38:46', '2017-09-02 13:38:46', '');

-- ----------------------------
-- Table structure for `stmanager_student`
-- ----------------------------
DROP TABLE IF EXISTS `stmanager_student`;
CREATE TABLE `stmanager_student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL COMMENT '学校id',
  `student_id` varchar(128) DEFAULT NULL COMMENT '学生id',
  `employee_id` varchar(128) DEFAULT NULL COMMENT '员工id',
  `renew_money` int(11) NOT NULL DEFAULT '0' COMMENT '续费金额',
  `back_money` int(11) NOT NULL DEFAULT '0' COMMENT '退费金额',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stmanager_student
-- ----------------------------
INSERT INTO `stmanager_student` VALUES ('1504340679669', '1504327016935', '1504332588960', '1504330563334', '0', '100', '2017-09-02 16:24:40', '2017-09-09 22:30:30', '');
INSERT INTO `stmanager_student` VALUES ('1504340679671', '1504327016935', '1504336120361', '1504330563334', '0', '0', '2017-09-02 16:24:40', '2017-09-02 16:24:40', '');
INSERT INTO `stmanager_student` VALUES ('1504340679673', '1504327016935', '1504335614433', '1504330563334', '0', '0', '2017-09-02 16:24:40', '2017-09-02 16:24:40', '');
INSERT INTO `stmanager_student` VALUES ('1504340679675', '1504327016935', '1504332508879', '1504330563334', '0', '0', '2017-09-02 16:24:40', '2017-09-02 16:24:40', '');
INSERT INTO `stmanager_student` VALUES ('1504340679678', '1504327016935', '1504332406428', '1504330563334', '0', '2600', '2017-09-02 16:24:40', '2017-09-03 11:09:48', '');
INSERT INTO `stmanager_student` VALUES ('1504358497618', '1504327016935', '1504336486093', '1504330563334', '0', '0', '2017-09-02 21:21:38', '2017-09-02 21:21:38', '');
INSERT INTO `stmanager_student` VALUES ('1504360403009', '1504327016935', '1504337851250', '1504330563334', '0', '0', '2017-09-02 21:53:23', '2017-09-02 21:53:23', '');
INSERT INTO `stmanager_student` VALUES ('1504360403011', '1504327016935', '1504338198378', '1504330563334', '2000', '0', '2017-09-02 21:53:23', '2017-09-03 11:39:37', '');
INSERT INTO `stmanager_student` VALUES ('1504406944416', '1504327016935', '1504405934662', '1504330563334', '0', '0', '2017-09-03 10:49:04', '2017-09-03 10:49:04', '');
INSERT INTO `stmanager_student` VALUES ('1504406944418', '1504327016935', '1504404010750', '1504330563334', '0', '0', '2017-09-03 10:49:04', '2017-09-03 10:49:04', '');
INSERT INTO `stmanager_student` VALUES ('1504409840370', '1504327016935', '1504340216219', '1504330563334', '0', '0', '2017-09-03 11:37:20', '2017-09-03 11:37:20', '');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL COMMENT '校区ID',
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(64) DEFAULT NULL COMMENT '身份证号码',
  `sex` varchar(32) DEFAULT NULL COMMENT '性别',
  `orign_school` varchar(256) DEFAULT NULL COMMENT '学校',
  `grade` varchar(64) DEFAULT NULL COMMENT '年级',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `subject` varchar(32) DEFAULT NULL COMMENT '科目',
  `source` varchar(128) DEFAULT NULL COMMENT '来源',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机',
  `parent_name` varchar(64) DEFAULT NULL COMMENT '家长姓名',
  `parent_sex` varchar(32) DEFAULT NULL COMMENT '家长性别',
  `parent_id_card` varchar(64) DEFAULT NULL COMMENT '家长身份证号码',
  `parent_phone` varchar(32) DEFAULT NULL COMMENT '家长手机',
  `address` varchar(256) DEFAULT NULL COMMENT '家长地址',
  `remark` varchar(2048) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `already_paid` varchar(64) DEFAULT NULL COMMENT '缴费状态',
  `distribution_type` varchar(64) DEFAULT NULL COMMENT '当前分配状态',
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1504332406428', '1504327016935', '张三', '362329200111110000', 'MALE', null, '初三', '2001-11-11', null, '明察暗访', '13666767677', null, null, null, null, '北京市昌平区', null, '2017-09-02 14:06:46', '2017-09-02 16:24:40', 'YES', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504332508879', '1504327016935', '刘德华', '362329200712170000', 'MALE', '昌平小学', '二年级', '2007-12-17', null, '自己来找我的 ', '18767624333', null, null, null, null, '北京市昌平区', null, '2017-09-02 14:08:29', '2017-09-02 16:24:40', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504332588960', '1504327016935', '张柏芝', '362320198812121111', 'FEMALE', '昌平高中', '高三', '1988-12-12', '文科', '我们找来的', '18720081109', null, null, null, null, '北京市', '这个学生很漂亮的', '2017-09-02 14:09:49', '2017-09-03 12:34:53', 'YES', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504335614433', '1504327016935', '陈奕迅', '362399199909090000', 'MALE', '第一中学', '高三', '1999-09-09', '理科', '我去学校咨询的', '18977787777', null, null, null, null, '北京市', null, '2017-09-02 15:00:14', '2017-09-02 16:24:40', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504336120361', '1504327016935', '周杰伦', '366666199907230987', 'MALE', null, '二年级', '1999-07-23', null, '哈哈哈', '18876765434', null, null, null, null, '北京', null, '2017-09-02 15:08:40', '2017-09-02 16:24:40', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504336486093', '1504327016935', '薛之谦', '362322200412129987', 'MALE', null, '三年级', '2004-12-12', null, '他自己找来的', '13688763520', null, null, null, null, '北京市', null, '2017-09-02 15:14:46', '2017-09-02 21:21:38', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504337851250', '1504327016935', 'Anthony测试学生', '350521199112226516', 'FEMALE', null, '三年纪', '1991-12-22', null, '啊啊手动阀你就', '18877772231', null, null, null, null, '啊骚得很发哦老师的办法了', null, '2017-09-02 15:37:31', '2017-09-02 21:53:23', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504338198378', '1504327016935', 'Anthony测试学生2', '350521199112222235', 'MALE', null, '二年级', '1991-12-22', null, '哦爱泛红', '18877323232', null, null, null, null, '有撒地方红蓝', null, '2017-09-02 15:43:18', '2017-09-03 11:39:37', 'YES', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504338737388', '', 'Anthony测试学生3', '342371199812221111', 'MALE', null, '二年级', '1998-12-22', null, '骚的符号是的愤怒', '18850322882', null, null, null, null, '哦啊是电话发雷神副本', null, '2017-09-02 15:52:17', '2017-09-02 15:52:17', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504338845179', '', 'Anthony学生测试4', '350521188112223421', 'FEMALE', null, '一年级', '1881-12-22', null, '奥斯丁发货呢', '18877662232', null, null, null, null, '阿松地哈佛', null, '2017-09-02 15:54:05', '2017-09-02 15:54:05', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504338905617', '', 'Anthony测试学生5', '350521199111111111', 'MALE', '', '一年级', '1991-11-11', null, '哦i啊DHL是否能', '18876223212', null, null, null, null, '阿松地方弄', null, '2017-09-02 15:55:06', '2017-09-02 15:55:06', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504339681838', '1504327016935', 'Anthony测试学生6', '350321199112133211', 'MALE', null, '一年级', '1991-12-13', null, '阿松地哈佛了', '18872376481', null, null, null, null, '到i发咯', null, '2017-09-02 16:08:02', '2017-09-02 16:20:39', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504340216219', '1504327016935', 'Anthony测试学生6', '350521199112232222', 'MALE', null, 'sania', '1991-12-23', null, 'oashf', '17782323212', null, null, null, null, 'saoifh', null, '2017-09-02 16:16:56', '2017-09-03 11:37:20', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504340278591', '1504327016935', 'Anthony测试学生7', '344331199112226666', 'FEMALE', 'saodfha', 'sanian', '1991-12-22', 'aofhali', 'asodhf', '17783289322', 'asufdh', null, 'asdif', 'afsd', 'asodfh', 'asidfaoufhnasol', '2017-09-02 16:17:59', '2017-09-09 17:14:20', 'YES', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504404010750', '1504327016935', '爱学成', '370601198808185088', 'MALE', '烟台二中', '高三', '1988-08-18', '语文数学英语', '微信', '15963580532', '爱学', null, null, null, '芝罘区环山路116号', null, '2017-09-03 10:00:11', '2017-09-03 10:49:04', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504405934662', '1504327016935', '也爱学成', '370621200905090020', 'MALE', null, '初二', '2009-05-09', null, '外呼', '13355556666', null, null, null, null, '烟台开发区', null, '2017-09-03 10:32:15', '2017-09-03 10:49:04', 'NO', 'STMANAGER_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504436969394', '1504327016935', '叶家洪', '370521199112222111', 'MALE', null, '一年级', '1991-12-22', null, '撒旦', '13850337777', '啥地方了', null, '350521199112222221', null, '十大', null, '2017-09-03 19:09:29', '2017-09-03 19:09:29', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504717397139', '1504327016935', '测试7', '35092211991122222X', 'MALE', null, '三年级', '1199-11-22', null, '阿迪斯', '18877667782', 'admin', null, '350322197922121232', null, '士大夫', null, '2017-09-07 01:03:17', '2017-09-07 01:03:17', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504717514659', '1504327016935', 'hehheeh', '350521199111111111', 'MALE', null, 'sannianji', '1991-11-11', null, 'asofdh', '18872263232', 'admon', null, '350521199112227777', null, 'aosfdh1aodsfh', null, '2017-09-07 01:05:15', '2017-09-07 01:05:15', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504717597487', '1504327016935', 'asdfdsf', '388888199112222222', 'MALE', null, 'asdf', '1991-12-22', null, 'asd', '18822223444', 'asdf', null, '308888199112222222', null, 'sadf', null, '2017-09-07 01:06:37', '2017-09-07 01:06:37', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504717652345', '1504327016935', 'adminguanl', '350444199111112222', 'MALE', null, 'dsn', '1991-11-11', null, 'faf', '18829222221', 'sdaf', null, '380921199112229990', null, 'd', null, '2017-09-07 01:07:32', '2017-09-07 01:07:32', 'NO', 'COUNSELOR_DISTRIBUTION', '');
INSERT INTO `student` VALUES ('1504962815131', '1504327016935', 'hehehe', '350521111111113567', 'MALE', null, '三年级', '1111-11-11', null, '啊', '18877777772', 'yjh', null, '350528198812112121', null, '撒法好了', null, '2017-09-09 21:13:35', '2017-09-09 21:17:15', 'YES', 'COUNSELOR_DISTRIBUTION', '');

-- ----------------------------
-- Table structure for `student_money`
-- ----------------------------
DROP TABLE IF EXISTS `student_money`;
CREATE TABLE `student_money` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '学校ID',
  `student_id` varchar(128) DEFAULT NULL COMMENT '学生ID',
  `has_pay` int(11) NOT NULL DEFAULT '0' COMMENT '已经缴费金额',
  `has_back` int(11) NOT NULL DEFAULT '0' COMMENT '已经退费金额',
  `has_used` int(11) NOT NULL DEFAULT '0' COMMENT '已经使用的金额',
  `total_hour` int(11) NOT NULL COMMENT '总课时',
  `used_hour` int(11) NOT NULL COMMENT '已经使用课时',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student_money
-- ----------------------------
INSERT INTO `student_money` VALUES ('1504339428311', '1504327016935', '1504332406428', '10000', '2600', '7400', '37', '14', '2017-09-02 16:03:48', '2017-09-03 11:11:39', '');
INSERT INTO `student_money` VALUES ('1504409977422', '1504327016935', '1504338198378', '2000', '0', '0', '0', '0', '2017-09-03 11:39:37', '2017-09-03 11:39:37', '');
INSERT INTO `student_money` VALUES ('1504413292942', '1504327016935', '1504332588960', '1000', '100', '0', '0', '0', '2017-09-03 12:34:53', '2017-09-09 22:30:30', '');
INSERT INTO `student_money` VALUES ('1504948459499', '1504327016935', '1504340278591', '8000', '0', '0', '0', '0', '2017-09-09 17:14:20', '2017-09-09 17:14:20', '');
INSERT INTO `student_money` VALUES ('1504963034793', '1504327016935', '1504962815131', '12000', '0', '0', '0', '0', '2017-09-09 21:17:15', '2017-09-09 21:17:15', '');

-- ----------------------------
-- Table structure for `student_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `student_pay_log`;
CREATE TABLE `student_pay_log` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL,
  `student_id` varchar(128) DEFAULT NULL,
  `employee_id` varchar(128) DEFAULT NULL,
  `op_pay_type` varchar(64) DEFAULT NULL COMMENT '操作类型：缴费，退费',
  `money` int(11) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student_pay_log
-- ----------------------------
INSERT INTO `student_pay_log` VALUES ('1504339428364', '1504327016935', '1504332406428', '1504330193825', 'COUNSELOR_PAY', '10000', '看看缴费成功不成功', '2017-09-02 16:03:48', '2017-09-02 16:03:48', '');
INSERT INTO `student_pay_log` VALUES ('1504408187532', null, '1504332406428', null, 'STUDENTMANAGER_BACK', '2600', null, '2017-09-03 11:09:48', '2017-09-03 11:09:48', '');
INSERT INTO `student_pay_log` VALUES ('1504409977425', '1504327016935', '1504338198378', '1504330563334', 'STUDENTMANAGER_PAY', '2000', '', '2017-09-03 11:39:37', '2017-09-03 11:39:37', '');
INSERT INTO `student_pay_log` VALUES ('1504413292945', '1504327016935', '1504332588960', '1504330193825', 'COUNSELOR_PAY', '1000', '交费了', '2017-09-03 12:34:53', '2017-09-03 12:34:53', '');
INSERT INTO `student_pay_log` VALUES ('1504948459503', '1504327016935', '1504340278591', '1504405787550', 'COUNSELOR_PAY', '8000', '哈哈第一单', '2017-09-09 17:14:20', '2017-09-09 17:14:20', '');
INSERT INTO `student_pay_log` VALUES ('1504963034796', '1504327016935', '1504962815131', '1504405787550', 'COUNSELOR_PAY', '12000', '这小伙子腻害', '2017-09-09 21:17:15', '2017-09-09 21:17:15', '');
INSERT INTO `student_pay_log` VALUES ('1504967430176', null, '1504332588960', null, 'STUDENTMANAGER_BACK', '100', null, '2017-09-09 22:30:30', '2017-09-09 22:30:30', '');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(128) NOT NULL COMMENT '唯一标示',
  `school_id` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '用户名称',
  `user_type` varchar(128) DEFAULT NULL COMMENT '用户类型',
  `entity_id` varchar(128) DEFAULT NULL COMMENT '实体id',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `salt` varchar(128) NOT NULL COMMENT '盐值',
  `role_id` varchar(128) DEFAULT NULL COMMENT '角色id',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `deleted` bit(1) NOT NULL COMMENT '状态：0=正常，1=删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1504327355078', null, '人事经理', 'EMPLOYEE', '1504327355065', '18818181818', 'personmanager', '6e7433b1ee10d31c2bfe13618a1c79a3e50aa38ffc8d37972956a508a4589619', 'AEDC139BF3734099A73EE1A261E8F29C', 'PERSONNEL_MANAGER', null, '2017-09-03 11:28:49', '2017-09-02 12:42:35', '2017-09-03 16:41:55', '');
INSERT INTO `user` VALUES ('1504330074547', '', '总校长', 'EMPLOYEE', '1504330074531', '18818181111', 'zongxiaozhang', 'e48fa6c23292cff3bf6c406a34917c8fee825d0bd370ab1c5dca24254f5895d5', 'A003D92C112B4112BC4D2F197E644D8F', 'SCHOOLMASTER_BOSS', null, '2017-09-03 11:31:07', '2017-09-02 13:27:55', '2017-09-03 11:31:07', '');
INSERT INTO `user` VALUES ('1504330193835', '1504327016935', '咨询师', 'EMPLOYEE', '1504330193825', '18881726666', 'zixunshi', '9bbb72bfdcb09dffa1560bff3e0417c4e7ddced37bda2280dfa79ef15e2ed642', 'B2C1D88CFE6D4CDBB72BC1F77F9D1622', 'CONSULTANT', null, '2017-09-09 17:02:08', '2017-09-02 13:29:54', '2017-09-09 17:02:08', '');
INSERT INTO `user` VALUES ('1504330295620', '1504327016935', '咨询主任', 'EMPLOYEE', '1504330295611', '18716166666', 'zixunzhuren', 'afbe06c0c1aff8b282f52f7c5bbe943fe3af3e82d2714d801f821ce1b3ff29d3', '95109945D46D432EBDC4A47FF73F502C', 'CONSULTANT_BOSS', null, '2017-09-09 20:26:18', '2017-09-02 13:31:36', '2017-09-09 20:26:18', '');
INSERT INTO `user` VALUES ('1504330362809', '', '咨询总监', 'EMPLOYEE', '1504330362790', '13818189976', 'zixunzongjian', '11fa90ab704d8d8719ff32fc34b0c202982caaf06d098cc4f98c2127f7c91e9d', '209252621CC241DD8F31649147E588FF', 'CONSULTANT_MAIN', null, '2017-09-09 20:30:20', '2017-09-02 13:32:43', '2017-09-09 20:30:20', '');
INSERT INTO `user` VALUES ('1504330563343', '1504327016935', '学管师', 'EMPLOYEE', '1504330563334', '13836361781', 'xueguanshi', '10055e79036d465dc52c37dcb2f76426943d357476fe9544d387fa80dffd65f3', '334FCF4D758C42568295E947298A1744', 'STUDENTMANAGER', null, '2017-09-09 13:03:41', '2017-09-02 13:36:03', '2017-09-09 13:03:41', '');
INSERT INTO `user` VALUES ('1504330735325', '1504327016935', '教管主任', 'EMPLOYEE', '1504330735316', '13919199999', 'jiaoguanzhuren', '272a958f293c1c04a916bb751273f6cc29e12578d6cc8b8b01be9910655f2465', '60F4D13C040D4908B2E18323E5F9A82E', 'STUDENTMANAGER_BOSS', null, '2017-09-09 22:26:30', '2017-09-02 13:38:55', '2017-09-09 22:26:30', '');
INSERT INTO `user` VALUES ('1504330804062', '1504327016935', '老师', 'EMPLOYEE', '1504330804053', '18817776565', 'laoshi', '77bc76988d6a12f87a90d14cd2cd33d26732ca98c9a7ec89d87f742439ffa206', '9374A949733740D5A1D531E39AEC3C77', 'TEACHER', null, '2017-09-03 11:31:09', '2017-09-02 13:40:04', '2017-09-08 14:50:24', '');
INSERT INTO `user` VALUES ('1504330859120', '1504327016935', '教研主任', 'EMPLOYEE', '1504330859110', '18765652413', 'jiaoyanzhuren', '7b45d40e1ca6a249980067b0f192ca266d95dff9c997f2176c8addd899beba5d', '2E86E42C38E749EFA1CA497376B57148', 'TEACHER_DIRECTOR', null, '2017-09-03 21:10:17', '2017-09-02 13:40:59', '2017-09-03 21:10:17', '');
INSERT INTO `user` VALUES ('1504330950330', '1504327016935', '昌平校区分校长', 'EMPLOYEE', '1504330950321', '18976761616', 'fenxiaozhang', '0fc93d20b1740397645f3c10c50608ebc91208e9408a78ffb4efa8ae03bf9d34', '3C2FFF3F81DC417CBEFB1778741FA73D', 'SCHOOLMASTER', null, '2017-09-09 20:28:15', '2017-09-02 13:42:30', '2017-09-09 20:28:15', '');
INSERT INTO `user` VALUES ('1504331045594', '', '财务', 'EMPLOYEE', '1504331045585', '13851885188', 'caiwu', '911466cb752e0783acb5c3637f4b4a249e31f63f35a69472114ec7c5c7e9317f', '8FC5DA458C214E719A13D1CFCC38BF2D', 'FINANCE', null, '2017-09-03 11:15:26', '2017-09-02 13:44:06', '2017-09-03 11:15:26', '');
INSERT INTO `user` VALUES ('1504331141051', '', '人事专员出纳', 'EMPLOYEE', '1504331141043', '18919199999', 'renshizhuanyuan', '876f264b836f425776014c0fa090c81ae0b26fa312e083202f30cecb77994cba', '1A7AEBEB59184CF6BAD5E6283FF17A07', 'PERSONNEL_CASHIER', null, '2017-09-03 11:33:38', '2017-09-02 13:45:41', '2017-09-03 11:33:38', '');
INSERT INTO `user` VALUES ('1504342193458', '1504327016935', '测试咨询总监1', 'EMPLOYEE', '1504342193415', '18876223123', 'zongjian', '22c17d5907e588d994684c64df8a16a5669c0f97712b5810435259aba2bfad45', 'FE5FF33C633145A3BBF839B1556871D2', 'CONSULTANT_MAIN', '0.0.0.0', '2017-09-02 16:49:53', '2017-09-02 16:49:53', '2017-09-03 17:06:43', '');
INSERT INTO `user` VALUES ('1504342321749', null, '测试咨询总监2', 'EMPLOYEE', '1504342321740', '18822223832', 'zngjian2', '1cf276dde61f355d126cf7dbed9f3f059de0858e8a5d7c42928726026558cea6', '3328E391AE4A4247B049F62908A4EAF9', 'CONSULTANT_MAIN', '0.0.0.0', '2017-09-02 16:52:02', '2017-09-02 16:52:02', '2017-09-03 17:06:40', '');
INSERT INTO `user` VALUES ('1504405787561', '1504327016935', '子寻', 'EMPLOYEE', '1504405787550', '15555556666', 'zixun', 'a1702bf8a8c12425b3ad7f8a9f5a2b5afc95f694995bab43916dc08b1b40e4ba', '8B3D5544C4114C00B3B1FF0411A03D42', 'CONSULTANT', null, '2017-09-03 10:36:26', '2017-09-03 10:29:48', '2017-09-09 17:11:46', '');
INSERT INTO `user` VALUES ('1504431099124', '1504330158156', '测试员工', 'EMPLOYEE', '1504431099107', '18876551111', 'test1', '4b301324992247776066af2e37913ac8a9658a4df47fd1f3f04757e8c0ec36d6', '5FE946C2C2B34BEF9ECAEA868870F290', 'CONSULTANT', '0.0.0.0', '2017-09-03 17:31:39', '2017-09-03 17:31:39', '2017-09-03 17:35:44', '');
INSERT INTO `user` VALUES ('1504431376329', '1504327016935', '测试员工', 'EMPLOYEE', '1504431376318', '18850447761', 'test1', 'a618e942334c1854381666b6c78f3ccbc374efd1ceb451ed0b78d93984fd525c', '20AB1530D9654730B4E8787E3B5D71C6', 'CONSULTANT', '0.0.0.0', '2017-09-03 17:36:16', '2017-09-03 17:36:16', '2017-09-03 17:36:16', '');
INSERT INTO `user` VALUES ('1504448315630', '1504327016935', 'Mr.Wang', 'EMPLOYEE', '1504448315614', '18821122222', 'jiaoshiwang', 'ddf65256d49eaedf99fbeae8f2b50d681c70c07443a2b8da564c4919a00c343c', 'CF26E50CCCD545EB99962A68ABF75328', 'CONSULTANT', '0.0.0.0', '2017-09-03 22:18:36', '2017-09-03 22:18:36', '2017-09-03 22:19:33', '');
INSERT INTO `user` VALUES ('1504448448766', '1504327016935', '教师小鲜肉', 'EMPLOYEE', '1504448448755', '18854773855', '呵呵呵', '691fa64b00e6c1feac1223219ab23d69341e28624dac24fec12002faf612fa26', '73716247E442446A88B144C8B43EA4C1', 'TEACHER', '0.0.0.0', '2017-09-03 22:20:49', '2017-09-03 22:20:49', '2017-09-03 22:20:49', '');
INSERT INTO `user` VALUES ('189716898bce46f38d17ad871be09df5', null, '系统管理员', 'ADMIN', '', '18888888888', 'admin', '849cb022fee0a567680827d78e4e7c9c21aad103f2ed710d9a1ca81fb702b502', 'c3061864a9ba49ecb3fba8f5d2ee5d37', 'SUPER_ADMIN', null, '2017-09-03 09:52:06', '2017-08-05 20:31:56', '2017-09-03 09:52:06', '');
