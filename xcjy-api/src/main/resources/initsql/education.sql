/*
Navicat MySQL Data Transfer

Source Server         : 于哥数据库
Source Server Version : 50718
Source Host           : www.qianhengnet.com:3306
Source Database       : education

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-08-06 19:32:58
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
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of apln_back_money
-- ----------------------------

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
  `status` varchar(64) DEFAULT NULL COMMENT '状态',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of counselor_student
-- ----------------------------

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '课程名称',
  `type` varchar(64) DEFAULT NULL COMMENT '课程类型',
  `student_num` int(11) DEFAULT NULL COMMENT '学生人数',
  `price` int(11) DEFAULT NULL COMMENT '课程价格',
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
INSERT INTO `course` VALUES ('0f7c99b0354f40a8a1def990987231e1', '', '高三化学（上）', 'NORMALGROUP', '36', '3888', '0', '0', '2017-08-05 20:41:07', '2017-08-05 20:50:55', '');
INSERT INTO `course` VALUES ('27a2d19b118d44c0bc2a80cce009ed96', '', '小学六年级数学', 'BOUTIQUEGROUP', '3', '2000', '0', '0', '2017-08-05 20:40:33', '2017-08-05 20:40:33', '');
INSERT INTO `course` VALUES ('3fb5ba7373a745cc9235af157343e796', '', '高三英语232', 'ONETOONE', '1', '3000', '0', '0', '2017-08-05 23:19:14', '2017-08-05 23:19:14', '');
INSERT INTO `course` VALUES ('953e096275aa4db6ae081c2d46169495', '', '高三英语22', 'ONETOONE', '1', '3000', '0', '0', '2017-08-05 21:10:35', '2017-08-05 21:10:35', '');
INSERT INTO `course` VALUES ('98208ca95f4f4becbe7ddb47c233d5b0', '', '高三物理', 'ONETOONE', '1', '3000', '0', '0', '2017-08-05 20:41:00', '2017-08-05 20:41:00', '');
INSERT INTO `course` VALUES ('c8100f3ef7fe4498b1df551b905c92c6', '', '小学一年级数学', 'NORMALGROUP', '20', '1200', '0', '0', '2017-08-05 20:40:04', '2017-08-05 20:40:04', '');

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
INSERT INTO `employee` VALUES ('35069c30fd9f43e7baa0979eb87f71b1', '', '涂图-人事专员出纳', 'MALE', '1998-09-09', '362329199809096666', '二本', '华东交通大学', '软件工程', '18810427666', '22222@qq.com', '涂潇晓', '18276765555', '福建省厦门市后坑前社', '人事专员出纳', '2017-08-06 12:14:29', '2017-08-06 12:14:29', '');
INSERT INTO `employee` VALUES ('3fd44fdd3fec47eabe77d82eb7245dff', '', '涂图-咨询主任', 'MALE', '1996-09-09', '362329199609096666', '二本', '华东交通大学', '软件工程', '18810427333', '22222@qq.com', '涂潇晓', '18276765555', '福建省厦门市后坑前社', '总校长', '2017-08-06 12:13:59', '2017-08-06 12:13:59', '');
INSERT INTO `employee` VALUES ('47685849c266471083b0b2e91dbdcd0a', '', '涂图-咨询师', 'MALE', '1997-09-09', '362329199709096666', '二本', '华东交通大学', '软件工程', '18810427311', '22222@qq.com', '涂潇晓', '18276765555', '福建省厦门市后坑前社', '咨询师', '2017-08-06 12:14:13', '2017-08-06 12:14:13', '');
INSERT INTO `employee` VALUES ('507d46d9c89d440ba3f44730aefdf027', '', '涂图-咨询总监', 'MALE', '1995-09-09', '362329199509096666', '二本', '华东交通大学', '软件工程', '18810427322', '22222@qq.com', '涂潇晓', '18276765555', '福建省厦门市后坑前社', '咨询总监', '2017-08-06 12:13:42', '2017-08-06 12:13:42', '');
INSERT INTO `employee` VALUES ('f688e2f2b267467ab25e7665fcbd9dbd', '', '老涂-总校长', 'MALE', '2017-08-06', 'string', '小学', '希望小学', '测试工程', '18888887777', '222666@qq.com', '老张', '17666666666', '北京昌平区', '修改后的信息', '2017-08-06 12:13:02', '2017-08-06 13:31:01', '');

-- ----------------------------
-- Table structure for `process_log`
-- ----------------------------
DROP TABLE IF EXISTS `process_log`;
CREATE TABLE `process_log` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL,
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
INSERT INTO `school` VALUES ('049f72dfbca54bb78ed6db48b6e4ad3c', '国家第一校区', '这就是个第一个校区，别说没听过', '2017-08-06 18:50:32', '2017-08-06 18:50:32', '');
INSERT INTO `school` VALUES ('1acff3982d714d0a920618f2df3bc916', '昌平校区', '这个校区在昌平', '2017-08-06 17:01:01', '2017-08-06 17:01:01', '');
INSERT INTO `school` VALUES ('4a1b3b1fac0041aaa78675d815106a3f', '湖里第一校区', '湖里第一校区', '2017-08-06 17:01:23', '2017-08-06 17:01:23', '');
INSERT INTO `school` VALUES ('ec1ea6c8c54b46838276f08bf6e8ef9d', '思明校区', '这个校区在厦门思明区', '2017-08-06 17:01:34', '2017-08-06 17:01:34', '');

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

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(128) NOT NULL,
  `school_id` varchar(128) NOT NULL COMMENT '校区ID',
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
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('3432ed631dd14aef9c93ab8717196204', '1acff3982d714d0a920618f2df3bc916', '李四3', '362329199809068888', 'MALE', '北京第二小学', '三年级', '1998-09-06', '', '慕名而来', '18810330394', '李思家长3', 'MALE', '362329197807126677', '18810330394', '北京市昌平区', '这个学生很聪明', '2017-08-06 18:58:06', '2017-08-06 18:58:06', 'NO', '');
INSERT INTO `student` VALUES ('3fa0304d2578477da59be57dcda88a92', '', '猴子哈哈', '36333199509081111', 'MALE', '昌平中学额', '高三1', null, '理科', '自己来报名的11', '13666666667', '猴子嗯嗯', 'MALE', '363433196812128888', '13655555556', '北京市昌平区1', '第2个学生', '2017-08-06 14:32:31', '2017-08-06 14:32:31', 'NO', '');
INSERT INTO `student` VALUES ('45a1ce1a95e64132a64df26b68fdae78', '1acff3982d714d0a920618f2df3bc916', '李四1', '362329199809048888', 'MALE', '北京第一小学', '一年级', '1998-09-04', '', '自主报名', '18810330394', '李思家长1', 'MALE', '362329197812126677', '18810330394', '北京市昌平区', '这个学生很调皮', '2017-08-06 18:58:04', '2017-08-06 18:58:04', 'NO', '');
INSERT INTO `student` VALUES ('461ae9a0ecfd4348829d341ea173f5d1', '1acff3982d714d0a920618f2df3bc916', '李四4', '362329199809078888', 'FEMALE', '北京第一小学', '一年级', '1998-09-07', '文科', '自主报名', '18810330399', '李思家长4', 'FEMALE', '362329197804126677', '18810330399', '北京市昌平区', '这个学生有点浪', '2017-08-06 18:58:07', '2017-08-06 18:58:07', 'NO', '');
INSERT INTO `student` VALUES ('b221d4b9682d406dbba2df0a0b1ba4cb', '1acff3982d714d0a920618f2df3bc916', '李四2', '362329199809058888', 'FEMALE', '北京第一小学', '二年级', '1998-09-05', '', '自主报名', '18810330395', '李思家长2', 'FEMALE', '362329197810126677', '18810330395', '北京市昌平区', '这个学生很优秀', '2017-08-06 18:58:05', '2017-08-06 18:58:05', 'NO', '');
INSERT INTO `student` VALUES ('cc31b2e3fd0641a49e5d860b9e99b356', '1acff3982d714d0a920618f2df3bc916', '李四6', '362329199809098888', 'FEMALE', '北京第一小学', '六年级', '1998-09-09', '文科', '自主报名', '18810339394', '李思家长6', 'FEMALE', '362329197801226677', '18810339394', '北京市昌平区', '这个学生很漂亮', '2017-08-06 18:58:10', '2017-08-06 18:58:10', 'NO', '');
INSERT INTO `student` VALUES ('d9cc38ce20034b539731e635b9e28e55', '', '猴子11', '36333199109081111', 'MALE', '昌平中学', '高三', null, '文科', '自己来报名的', '13666666666', '猴子', 'MALE', '363433197012128888', '13655555555', '北京市昌平区', '第2个学生', '2017-08-06 14:32:40', '2017-08-06 14:46:13', 'NO', '');
INSERT INTO `student` VALUES ('efc0b870c5f0439791519474493665e0', '1acff3982d714d0a920618f2df3bc916', '李四5', '362329199809088888', 'MALE', '英语中学', '高二', '1998-09-08', '文科', '咨询师介绍', '18810320394', '李思家长5', 'MALE', '362329197801236677', '18810320394', '北京市昌平区', '这个学生很有钱', '2017-08-06 18:58:08', '2017-08-06 18:58:08', 'NO', '');

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
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student_money
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student_pay_log
-- ----------------------------

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
INSERT INTO `user` VALUES ('189716898bce46f38d17ad871be09df5', null, '系统管理员', 'ADMIN', '', '18888888888', 'admin', '7c1af87b9eaaba2eda5f75f1f38e163db37cdff26440cd587f9565574979ad82', '0e9b530aa04344d3b5894c66637a12b4', 'SUPER_ADMIN', '0:0:0:0:0:0:0:1', '2017-08-06 18:59:02', '2017-08-05 20:31:56', '2017-08-06 18:59:02', '');
INSERT INTO `user` VALUES ('55ceb8a1d7cb42cfbb9e8085c450f7b4', '', '涂图-咨询师', 'EMPLOYEE', '47685849c266471083b0b2e91dbdcd0a', '18810427311', 'tutu_c', '5ffc5564060685bf5238c8157ebbedc888dfa347889443ef2e6d36335bc89926', '7993c326fe5e45bd8a97d03883fc1249', 'CONSULTANT,', '0.0.0.0', '2017-08-06 12:14:13', '2017-08-06 12:14:13', '2017-08-06 12:14:13', '');
INSERT INTO `user` VALUES ('6a200e6a1dfa4979b6ebd30b762a4053', '', '涂图-咨询总监', 'EMPLOYEE', '507d46d9c89d440ba3f44730aefdf027', '18810427322', 'tutu_cm', 'c18cf12dca48ad807db0f5747cad6764dd1b63db724cd45b600fbf706d6553ae', 'b5a4a7a6203943478197d92795da3147', 'CONSULTANT_MAIN,', '0.0.0.0', '2017-08-06 12:13:42', '2017-08-06 12:13:42', '2017-08-06 12:13:42', '');
INSERT INTO `user` VALUES ('6cb9ab368d0f4ad8a11f89be6f0e10de', '', '涂图-人事专员出纳', 'EMPLOYEE', '35069c30fd9f43e7baa0979eb87f71b1', '18810427666', 'tutu_pc', 'f533efeae55296d4f0c200c6f736f5e8f815a396a600a56526105c0e1c95c4e9', 'f682017daa5d4b41966a9d0694059274', 'PERSONNEL_CASHIER,', '0.0.0.0', '2017-08-06 12:14:29', '2017-08-06 12:14:29', '2017-08-06 12:14:29', '');
INSERT INTO `user` VALUES ('c1de0be2ae304df6b570f5c18cf9e8d9', '', '老涂-总校长', 'EMPLOYEE', 'f688e2f2b267467ab25e7665fcbd9dbd', '18888887777', 'tutu_shoolboss', '19bcd0a13e4031e157579f87f3d570739d257a2bf09c74fa60e0565a705839a8', '362398a119854259b015ad8adc1f125c', 'SCHOOLMASTER_BOSS,', '0.0.0.0', '2017-08-06 12:13:03', '2017-08-06 12:13:03', '2017-08-06 13:31:01', '');
INSERT INTO `user` VALUES ('d29329442af24e95b810d451a5e7dd7a', '', '涂图-咨询主任', 'EMPLOYEE', '3fd44fdd3fec47eabe77d82eb7245dff', '18810427333', 'tutu_cs', '544a5965a421799d143e67b1e3d20b79c9a4513887a66c4d4931aa9ad563b47e', '25811c8bd63f4d6897a7857b83192980', 'CONSULTANT_BOSS,', '0.0.0.0', '2017-08-06 12:13:59', '2017-08-06 12:13:59', '2017-08-06 12:13:59', '');
