

DROP TABLE IF EXISTS `app_params`;
CREATE TABLE `app_params` (
  `carId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `type` int(11) DEFAULT NULL COMMENT '0：android1：IOS',
  `paramValues` varchar(200) DEFAULT NULL COMMENT '参数 逗号隔开',
  PRIMARY KEY (`carId`),
  KEY `Index_1` (`carId`)
) ENGINE=InnoDB AUTO_INCREMENT=27982 DEFAULT CHARSET=utf8 COMMENT='APP参数';



DROP TABLE IF EXISTS `hy_account`;
CREATE TABLE `hy_account` (
  `ACCOUNT_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务主键(地区编号+guid生成规则)',
  `DISTRICT` int(11) NOT NULL COMMENT '服务分区编码',
  `ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '账号名',
  `ACCOUNT_NICKNAME` varchar(200) DEFAULT NULL COMMENT '昵称',
  `ACCOUNT_PWD` varchar(50) DEFAULT NULL COMMENT '密码(MD5加密)',
  `ACCOUNT_DATE` bigint(20) DEFAULT NULL COMMENT '日期',
  `ACCOUNT_STATE` varchar(50) DEFAULT NULL COMMENT '账号状态(0：停用；1：正常)',
  `ACCOUNT_ORG_ID` bigint(20) DEFAULT NULL COMMENT '组织ID',
  `ACCOUNT_LINKMAN` varchar(200) DEFAULT NULL COMMENT '账号联系人',
  `ACCOUNT_DESCRIBE` varchar(2000) DEFAULT NULL COMMENT '账号描述',
  `ACCOUNT_LINKTEL` varchar(50) DEFAULT NULL COMMENT '账号联系电话',
  `ACCOUNT_COMPANY` varchar(200) DEFAULT NULL COMMENT '账号所属公司',
  `DEL_FLAG` int(11) DEFAULT NULL COMMENT '逻辑删除标志',
  `ACCOUNT_TYPE` int(11) DEFAULT NULL COMMENT '用户类型',
  `create_account` bigint(20) DEFAULT NULL COMMENT '创建者',
  `JOB_TYPE` int(11) DEFAULT NULL COMMENT '车厂岗位',
  `LOCK_ACCOUNT` varchar(100) DEFAULT '0' COMMENT '锁定状态',
  `ERROR_COUNT` bigint(20) DEFAULT '0' COMMENT '错误次数',
  `TERM_VILIDATE` bigint(20) DEFAULT NULL COMMENT '有效时间',
  `LOGIN_FAILED_TIME` bigint(20) DEFAULT NULL COMMENT '登录失败时间',
  PRIMARY KEY (`ACCOUNT_ID`),
  KEY `Index_1` (`ACCOUNT_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='账号信息表';



DROP TABLE IF EXISTS `hy_account_business`;
CREATE TABLE `hy_account_business` (
  `ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `BUSINESS_ID` bigint(20) DEFAULT NULL COMMENT '客户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户用户关联表';



DROP TABLE IF EXISTS `hy_account_individual_setting`;
CREATE TABLE `hy_account_individual_setting` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ACCOUNT_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `INDIVIDUAL_SETTING` varchar(2000) DEFAULT NULL COMMENT '个性化设置内容\r\n            以$符号分割，3个为一组，分别代表报警ID/报警优先级/是否开启声音	\r\n            报警优先级分：\r\n            Low：关闭（关闭弹框和流水）\r\n            Normal：报文（流水）\r\n            High：报警（弹框）商定是否按照对象格式存储？\r\n            ',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ACCOUNT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户个性化设置';


DROP TABLE IF EXISTS `hy_account_platform_mapping`;
CREATE TABLE `hy_account_platform_mapping` (
  `account_id` bigint(20) NOT NULL COMMENT '用户ID',
  `platform_id` bigint(20) NOT NULL COMMENT '融资平台ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hy_account_role_mapping`;
CREATE TABLE `hy_account_role_mapping` (
  `ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '账号标识符',
  `R_CODE` int(11) DEFAULT NULL COMMENT '角色功能编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户角色映射关系表';



DROP TABLE IF EXISTS `hy_account_station`;
CREATE TABLE `hy_account_station` (
  `ACCOUNT_ID` bigint(20) NOT NULL,
  `STATION_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户服务站关联表';



DROP TABLE IF EXISTS `hy_action`;
CREATE TABLE `hy_action` (
  `A_CODE` int(11) NOT NULL COMMENT '功能编码(先按之前编号',
  `A_NAME` varchar(200) DEFAULT NULL COMMENT '功能名称',
  `A_DESC` varchar(2000) DEFAULT NULL COMMENT '功能描述',
  `A_PARENT_CODE` int(11) DEFAULT NULL COMMENT '上级功能',
  PRIMARY KEY (`A_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单功能表';



DROP TABLE IF EXISTS `hy_activity`;
CREATE TABLE `hy_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '活动标题',
  `activity_no` varchar(20) DEFAULT NULL COMMENT '活动期号',
  `start_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `activity_state` tinyint(1) DEFAULT '0' COMMENT '1:未投放 7:已投放 2:未开始 3:进行中 6:活动结束 4:活动暂停 5:活动过期',
  `advert_object` tinyint(1) DEFAULT '0' COMMENT '1:个人APP 2:企业APP 3:全部',
  `advert_type` tinyint(1) DEFAULT '0' COMMENT '1:广告 2:抽奖',
  `activity_url` varchar(256) DEFAULT NULL COMMENT '活动链接地址',
  `advert_pic` varchar(256) DEFAULT NULL COMMENT '活动宣传图',
  `prize_set` varchar(1024) DEFAULT NULL COMMENT '奖品设置【json字符串】',
  `prize_amount_day` int(11) DEFAULT '0' COMMENT '每天出奖数量',
  `take_count_set` varchar(256) DEFAULT NULL COMMENT '参与次数设置【json字符串】',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_activity_win_record`;
CREATE TABLE `hy_activity_win_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL COMMENT '活动ID',
  `win_no` varchar(20) DEFAULT NULL COMMENT '中奖号码',
  `prize_item` varchar(20) DEFAULT NULL COMMENT '奖项',
  `prize_type` tinyint(1) DEFAULT '0' COMMENT '1:虚拟 2:实物',
  `win_time` bigint(20) DEFAULT NULL COMMENT '中奖时间',
  `win_account` varchar(20) DEFAULT NULL COMMENT '中奖账号 -- APP登录账号',
  `cash_state` tinyint(1) DEFAULT '0' COMMENT '兑奖状态 1未兑奖，2已兑奖',
  `win_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `win_phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `win_address` varchar(100) DEFAULT NULL COMMENT '地址',
  `postal_code` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `express_company` varchar(50) DEFAULT NULL COMMENT '快递公司',
  `express_no` varchar(20) DEFAULT NULL COMMENT '快递单号',
  `receive_prize_type` tinyint(1) DEFAULT '0' COMMENT '领奖方式 1、网上领取（实物为邮寄），2现场领取',
  `prize_validate_code` varchar(30) DEFAULT NULL COMMENT '兑奖验证码',
  `update_time` bigint(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_alarm_info`;
CREATE TABLE `hy_alarm_info` (
  `VEH_NUM` varchar(50) DEFAULT NULL,
  `VEH_COLOR` int(11) DEFAULT NULL,
  `SIM` bigint(20) DEFAULT NULL,
  `G_DATE` bigint(20) DEFAULT NULL,
  `LNG` bigint(20) DEFAULT NULL,
  `LAT` bigint(20) DEFAULT NULL,
  `SPEED1` double DEFAULT NULL,
  `SPEED2` double DEFAULT NULL,
  `MILEAGE` double DEFAULT NULL,
  `DIRECTION` bigint(20) DEFAULT NULL,
  `ELEVATION` bigint(20) DEFAULT NULL,
  `ACC` int(11) DEFAULT NULL,
  `VEH_STATE` bigint(20) DEFAULT NULL,
  `ALARM_TYPE` int(11) DEFAULT NULL,
  `RECEIVE_DATE` bigint(20) DEFAULT NULL,
  `SOURCE` int(11) DEFAULT NULL,
  `CONTENT` varchar(200) DEFAULT NULL,
  `ALARM` varchar(70) DEFAULT NULL,
  KEY `Index_1` (`G_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警';



DROP TABLE IF EXISTS `hy_alarmremind_cache`;
CREATE TABLE `hy_alarmremind_cache` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `ALARMDATE` bigint(20) DEFAULT NULL COMMENT '报警时间',
  `ALARMTIMES` bigint(20) DEFAULT NULL COMMENT '报警持续时间',
  `ALARMLAT` bigint(20) DEFAULT NULL COMMENT '报警纬度',
  `ALARMLOG` bigint(20) DEFAULT NULL COMMENT '报警经度',
  `OLDTERCODE` varchar(30) DEFAULT NULL COMMENT '旧终端id',
  `NEWTERCODE` varchar(30) DEFAULT NULL COMMENT '新终端id',
  `ALARMTYPE` int(11) DEFAULT NULL COMMENT '报警类型（1:出区域限速 2:滞留超时3:一体机拆出报警4:车辆锁车报警5:ID不匹配报警）',
  `ALARMOVERTYPE` int(11) DEFAULT NULL COMMENT '报警结束标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_answers`;
CREATE TABLE `hy_answers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `answer` varchar(200) DEFAULT NULL COMMENT '答案',
  `q_id` bigint(20) DEFAULT NULL COMMENT '问题主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问下发答案';



DROP TABLE IF EXISTS `hy_appointment`;
CREATE TABLE `hy_appointment` (
  `appointment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预约id',
  `station_id` bigint(20) DEFAULT NULL COMMENT '服务站id',
  `appointment_type` int(11) DEFAULT NULL COMMENT '预约类型',
  `car_id` bigint(20) DEFAULT NULL COMMENT '车辆id',
  `service_content` varchar(400) DEFAULT NULL COMMENT '服务内容',
  `parts_content` varchar(400) DEFAULT NULL COMMENT '配件明细',
  `appointment_comment` varchar(400) DEFAULT NULL COMMENT '补充说明',
  `appointment_name` varchar(200) DEFAULT NULL COMMENT '预约人',
  `appointment_time` bigint(20) DEFAULT NULL COMMENT '预约时间',
  `appointment_phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `station_name` varchar(200) DEFAULT NULL COMMENT '服务站名称',
  `appo_submit_time` bigint(20) DEFAULT NULL COMMENT '预约提交时间',
  `appointment_num` varchar(60) DEFAULT NULL COMMENT '预约流水号',
  `APPOINTMENT_STATUS` int(11) DEFAULT NULL COMMENT '预约状态0:预约待确认，1:已确认未服务，2:已取消，3:正在服务,4:完成服务待评价,5:完成评价,6:已过期',
  `tool_content` varchar(400) DEFAULT NULL COMMENT '提升工具明细',
  `inbound_time` bigint(20) DEFAULT NULL COMMENT '入站时间',
  `outbound_time` bigint(20) DEFAULT NULL COMMENT '出站时间',
  PRIMARY KEY (`appointment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_atc_mapping`;
CREATE TABLE `hy_atc_mapping` (
  `ATC_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务主键(地区编号+guid生成规则)',
  `CAR_TEAM_ID` bigint(20) DEFAULT NULL COMMENT '0车队ID  1车辆ID',
  `ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '账号ID',
  `ATC_TYPE` int(11) DEFAULT NULL COMMENT '0账号和车队\r\n            1账号和车辆',
  PRIMARY KEY (`ATC_ID`),
  KEY `Index_1` (`ATC_ID`),
  KEY `ACCOUNT_ID_INDEX` (`ACCOUNT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=79296 DEFAULT CHARSET=utf8 COMMENT='账号和车组、车辆映射关系表';



DROP TABLE IF EXISTS `hy_basic_mapping`;
CREATE TABLE `hy_basic_mapping` (
  `CODE` int(11) NOT NULL COMMENT '行政区编号',
  `PARENT_CODE` int(11) DEFAULT NULL COMMENT '上级编码',
  `BASIC_TYPE` int(11) DEFAULT NULL COMMENT '字典类型',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型';



DROP TABLE IF EXISTS `hy_basicdata`;
CREATE TABLE `hy_basicdata` (
  `DATA_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '基础数据ID',
  `DATA_CODE` varchar(20) DEFAULT NULL COMMENT '数据编码',
  `DATA_VALUE` varchar(200) DEFAULT NULL COMMENT '数据名称',
  `DATA_TYPE` int(11) DEFAULT NULL COMMENT '字典类型',
  PRIMARY KEY (`DATA_ID`),
  KEY `Index_1` (`DATA_ID`),
  KEY `DATA_TYPE_INDEX` (`DATA_TYPE`)
) ENGINE=InnoDB AUTO_INCREMENT=5884 DEFAULT CHARSET=utf8 COMMENT='基础数据字典表';


DROP TABLE IF EXISTS `hy_basictype`;
CREATE TABLE `hy_basictype` (
  `T_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `T_NAME` varchar(200) DEFAULT NULL COMMENT '类型名称',
  `T_COMMON` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`T_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='数据字典类型表';



DROP TABLE IF EXISTS `hy_business`;
CREATE TABLE `hy_business` (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_name` varchar(200) DEFAULT NULL COMMENT '企业名称',
  `business_code` varchar(50) DEFAULT NULL COMMENT '企业许可证号',
  `address_code` varchar(50) DEFAULT NULL COMMENT '省市编码',
  `link_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `link_telpone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `address_common` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `common` varchar(200) DEFAULT NULL COMMENT '备注',
  `c_type` smallint(6) DEFAULT '0' COMMENT '客户类型 1-企业 2-个人',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_car`;
CREATE TABLE `hy_car` (
  `CAR_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `DISTRICT_ID` int(11) NOT NULL COMMENT '服务分区编码(内蒙：国标码或者自定义编码)',
  `CAR_CPH` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `CAR_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色（数据字典）',
  `CAR_TERMINAL` bigint(20) DEFAULT NULL COMMENT '终端ID',
  `CAR_TEAM_ID` bigint(20) DEFAULT NULL COMMENT '车组ID',
  `CAR_STATE` int(11) DEFAULT NULL COMMENT '服务状态（数据字典）',
  `CAR_PW` varchar(50) DEFAULT NULL COMMENT '车辆查询密码(要设置默认值：888888；密文MD5加密字符串)',
  `CAR_ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '注册车辆账户',
  `CAR_AUTO_NUMBER` varchar(50) DEFAULT NULL COMMENT '车辆自编号',
  `CAR_DATE` bigint(20) DEFAULT NULL COMMENT '入网时间',
  `CAR_PLACE` varchar(200) DEFAULT NULL COMMENT '车籍地',
  `CAR_COMPANY` varchar(200) DEFAULT NULL COMMENT '车辆所属公司',
  `DEL_FLAG` int(11) DEFAULT '0' COMMENT '逻辑删除标记(1:删除，0：正常，default：0)',
  `CAR_TYPE` int(11) DEFAULT NULL COMMENT '车辆类型（数据字典）',
  `CAR_TRADE` int(11) DEFAULT NULL COMMENT '运输行业类型(数据字典)',
  `CAR_SERVICE_STOP` int(11) DEFAULT NULL COMMENT '是否服务到期停止',
  `SERVICE_BEGIN` bigint(20) DEFAULT NULL COMMENT '服务期开始时间',
  `SERVICE_END` bigint(20) DEFAULT NULL COMMENT '服务期结束时间',
  `NETTING_TIME` bigint(20) DEFAULT NULL COMMENT '入网时间',
  `NETTING_LOG` bigint(20) DEFAULT NULL COMMENT '入网位置-经度',
  `NETTING_LAT` bigint(20) DEFAULT NULL COMMENT '入网位置-纬度',
  `CHASSIS_NUM` varchar(100) DEFAULT NULL COMMENT '底盘号',
  `STRUCTURE_NUM` varchar(100) DEFAULT NULL COMMENT '结构号',
  `OIL_CAPACITY` varchar(100) DEFAULT NULL COMMENT '油箱容量',
  `LOCK_STAUTS` int(11) DEFAULT NULL COMMENT '锁车状态：0（未激活未锁车00）1（未激活锁车01）2（激活未锁车10）3（激活锁车11）',
  `CAR_TERMINAL_ID` bigint(20) DEFAULT NULL COMMENT '北斗终端ID',
  `AUTO_FLAG` int(11) DEFAULT NULL COMMENT '1,平台录入，其他：DMS',
  `TAMPER_STATUE` int(11) DEFAULT NULL COMMENT '防拆方案',
  `OPERATE_COMMON` varchar(200) DEFAULT NULL COMMENT '操作备注',
  `OPERATE_USER` varchar(200) DEFAULT NULL COMMENT '操作人',
  `OPERATE_DATE` bigint(20) DEFAULT NULL COMMENT '操作时间',
  `tamper_notice_status` int(20) DEFAULT '0' COMMENT '位置云防拆通知状\r\n\r\n态',
  `OFFLINE_TIME` bigint(20) DEFAULT NULL COMMENT '下线时间',
  `REMOVAL_TIME` bigint(20) DEFAULT NULL COMMENT '出库时间',
  `REGISTER_TIME` bigint(20) DEFAULT NULL COMMENT '末次注册时间',
  `OPERATE_IP` varchar(100) DEFAULT NULL COMMENT '操作人IP',
  `CAR_FKDATE` bigint(100) DEFAULT NULL COMMENT '防控时效',
  `BATTERY_TYPE` int(11) DEFAULT NULL COMMENT '电池类型',
  `BATTERY_BATCHES` int(11) DEFAULT NULL COMMENT '电池批次',
  `car_model_code` varchar(20) DEFAULT NULL COMMENT '车型码',
  `online_time` bigint(20) DEFAULT NULL COMMENT '上线时间',
  `CAR_MODEL` varchar(100) DEFAULT NULL COMMENT '车辆型号',
  `WAREHOUSE_LOG` bigint(20) DEFAULT NULL COMMENT '入库位置-经度',
  `WAREHOUSE_LAT` bigint(20) DEFAULT NULL COMMENT '入库位置-纬度',
  `WAREHOUSE_TIME` bigint(20) DEFAULT NULL COMMENT '入库时间',
  `order_number` varchar(60) DEFAULT NULL COMMENT '订单号',
  `sync_time` datetime DEFAULT NULL COMMENT '同步时间',
  `lock_method` varchar(20) DEFAULT NULL COMMENT '锁车方案',
  `qr_code` varchar(50) DEFAULT NULL COMMENT '整车二维码',
  `financing_company` bigint(30) DEFAULT '0' COMMENT '所属金融公司',
  `pay_type` bigint(30) DEFAULT '1' COMMENT '付款方式',
  `CREATE_ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  PRIMARY KEY (`CAR_ID`),
  UNIQUE KEY `CHASSIS_NUM` (`CHASSIS_NUM`),
  KEY `Index_1` (`CAR_ID`),
  KEY `CAR_TERMINAL_INDEX` (`CAR_TERMINAL`),
  KEY `CAR_TERMINAL_ID_INDEX` (`CAR_TERMINAL_ID`),
  KEY `CAR_TEAM_ID_INDEX` (`CAR_TEAM_ID`),
  KEY `FINANCING_COMPANY_INDEX` (`financing_company`)
) ENGINE=InnoDB AUTO_INCREMENT=2960 DEFAULT CHARSET=utf8 COMMENT='车辆基础信息';



DROP TABLE IF EXISTS `hy_car_detail`;
CREATE TABLE `hy_car_detail` (
  `CAR_ID` bigint(20) NOT NULL COMMENT '表主键',
  `TRANSPORT_LICENSE_CODE` varchar(200) DEFAULT NULL COMMENT '道路运输证编码',
  `BUSINESS_CERTIFICATE_CODE` varchar(200) DEFAULT NULL COMMENT '经营许可编码',
  `VEHICLE_NUMBER` varchar(200) DEFAULT NULL COMMENT '车辆厂牌型号',
  `CAR_OWNER` varchar(50) DEFAULT NULL COMMENT '车主',
  `ENGINE_NUMBER` varchar(200) DEFAULT NULL COMMENT '车辆号',
  `CHECK_VALIDITY` bigint(20) DEFAULT NULL COMMENT '年检有效期',
  `BEFORE_MAINTEN_TIME` bigint(20) DEFAULT NULL COMMENT '前次保养时间',
  `INSURANCE_TIME` bigint(20) DEFAULT NULL COMMENT '保险到期时间',
  `BEFORE_MAINTEN_MILEAGE` varchar(50) DEFAULT NULL COMMENT '前次保养里程',
  `CHECK_SEAT` varchar(50) DEFAULT NULL COMMENT '核定座位',
  `BUSINESS_AREA` varchar(50) DEFAULT NULL COMMENT '经营区域编码',
  `CLASS_LINE_TYPE` int(11) DEFAULT NULL COMMENT '班线类型(数据字典)',
  `CHECK_TON` varchar(50) DEFAULT NULL COMMENT '核定吨位',
  `MOBILE` varchar(50) DEFAULT NULL COMMENT '车主联系电话',
  `QUALITY` int(11) DEFAULT NULL COMMENT '准牵引总质量(计量单位：吨)',
  `PLATE` varchar(50) DEFAULT NULL COMMENT '挂车车牌号',
  `DANGEROUS_GOODS` int(11) DEFAULT NULL COMMENT '危险品货物分类(数据字典)',
  `SALES_STATUS` int(11) DEFAULT NULL COMMENT '销售状态',
  `SALES_DATE` bigint(20) DEFAULT NULL COMMENT '销售日期',
  `CAR_AMOUNT` double DEFAULT NULL COMMENT '购车总金额（万元）',
  `LOAN_AMOUNT` double DEFAULT NULL COMMENT '贷款总金额（万元）',
  `SURPLUS` double DEFAULT NULL COMMENT '剩余未还款（万元）',
  `ENGINE_TYPE` varchar(50) DEFAULT NULL COMMENT '发动机编码',
  `invoice_number` varchar(20) DEFAULT NULL COMMENT '发票号',
  UNIQUE KEY `CAR_ID` (`CAR_ID`),
  KEY `Index_1` (`CAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆明细信息表';



DROP TABLE IF EXISTS `hy_car_employees_mapping`;
CREATE TABLE `hy_car_employees_mapping` (
  `CEM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `EMPLOYEES_ID` bigint(20) DEFAULT NULL COMMENT '从业人员ID',
  `EMPLOYEES_TYPE` int(11) DEFAULT NULL COMMENT '从业人员类型（数据字典）',
  PRIMARY KEY (`CEM_ID`),
  KEY `Index_1` (`CEM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='5.2.1.7	车辆和从业人员关联表';



DROP TABLE IF EXISTS `hy_car_message`;
CREATE TABLE `hy_car_message` (
  `MSG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '终端短信息ID',
  `DISTRICT_ID` bigint(20) NOT NULL COMMENT '服务分区编码(内蒙：国标码或者自定义编码)',
  `CAR_ID` int(11) DEFAULT NULL COMMENT '车辆ID',
  `MSG_DATE` bigint(20) DEFAULT NULL COMMENT '短信息日期',
  `MSG_CONTENT` varchar(2000) DEFAULT NULL COMMENT '短信息内容',
  `MSG_TYPE` int(11) DEFAULT NULL COMMENT '短信息类型(数据字典)',
  `MSG_STATE` int(11) DEFAULT NULL COMMENT '短信息状态(数据字典)',
  `MS_ACCOUNT_NAME` varchar(200) DEFAULT NULL COMMENT '发送信息账户名称',
  PRIMARY KEY (`MSG_ID`),
  KEY `Index_1` (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端短信息存储';



DROP TABLE IF EXISTS `hy_car_param`;
CREATE TABLE `hy_car_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `LCXM` varchar(100) DEFAULT NULL COMMENT '老车型码',
  `CXM` varchar(100) DEFAULT NULL COMMENT '车型码',
  `SJXH` varchar(100) DEFAULT NULL COMMENT '设计型号',
  `GG` varchar(200) DEFAULT NULL COMMENT '公告',
  `JSS` varchar(100) DEFAULT NULL COMMENT '驾驶室',
  `FDJ` varchar(100) DEFAULT NULL COMMENT '发动机',
  `BSX` varchar(100) DEFAULT NULL COMMENT '变速箱',
  `LHQ` varchar(100) DEFAULT NULL COMMENT '离合器',
  `HQ` varchar(100) DEFAULT NULL COMMENT '后桥',
  `CJ` varchar(100) DEFAULT NULL COMMENT '车架',
  `JGL` varchar(100) DEFAULT NULL COMMENT '净功率',
  `EDGL` varchar(100) DEFAULT NULL COMMENT '额定功率',
  `ZJ` varchar(100) DEFAULT NULL COMMENT '轴距',
  `FA` varchar(100) DEFAULT NULL COMMENT '方案',
  `LT` varchar(100) DEFAULT NULL COMMENT '轮胎',
  `QT` varchar(1000) DEFAULT NULL COMMENT '其他',
  `CX` varchar(100) DEFAULT NULL COMMENT '车型',
  `BJSJ` datetime DEFAULT NULL COMMENT '编辑时间',
  `QZ` varchar(100) DEFAULT NULL COMMENT '前轴',
  `CXIANG` varchar(100) DEFAULT NULL COMMENT '车厢',
  `COLORCODE` varchar(100) DEFAULT NULL COMMENT '颜色码',
  `COLOR` varchar(100) DEFAULT NULL COMMENT '颜色',
  `SFTGG` varchar(100) DEFAULT NULL COMMENT '是否套公告',
  `ZZZ` varchar(100) DEFAULT NULL COMMENT '总质量',
  `ZAIZZ` varchar(100) DEFAULT NULL COMMENT '载重量',
  `ZBZL` varchar(100) DEFAULT NULL COMMENT '装备质量',
  `WKCC` varchar(100) DEFAULT NULL COMMENT '外扩尺寸',
  `NKCC` varchar(100) DEFAULT NULL COMMENT '内扩尺寸',
  `ZZCS` varchar(100) DEFAULT NULL COMMENT '最高车速',
  `VINQ8` varchar(100) DEFAULT NULL COMMENT 'vin前8位',
  `BZ` varchar(100) DEFAULT NULL COMMENT '备注',
  `SFCYCX` varchar(100) DEFAULT NULL COMMENT '是否常用车型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27576 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hy_car_picture`;
CREATE TABLE `hy_car_picture` (
  `CP_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识符',
  `DISTRICT_ID` int(11) NOT NULL COMMENT '服务分区编码(内蒙：国标码或者自定义编码)',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆标识',
  `M_ID` bigint(20) DEFAULT NULL COMMENT '多媒体ID(终端上传)',
  `CP_PIC_DIRECTORY` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `CP_PIC_DATE` bigint(20) DEFAULT NULL COMMENT '拍照日期',
  `CP_SIGN` int(11) DEFAULT NULL COMMENT '拍照状态来源(数据字典)',
  `CP_CAMERA` int(11) DEFAULT NULL COMMENT '摄像头编号',
  `CP_MARK` int(11) DEFAULT NULL COMMENT '图像标记(数据字典)',
  `GPS_CODE` bigint(20) DEFAULT NULL COMMENT 'GPS数据ID（外键）HY_DATA_ACTION',
  `CP_ACCOUNT_NAME` varchar(200) DEFAULT NULL COMMENT '最后更新用户名称',
  `CP_REMARK` varchar(2000) DEFAULT NULL COMMENT '备注',
  `CP_MEDIATYPE` int(11) DEFAULT NULL COMMENT '类型(数据字典)',
  `CP_DRIVERNAME` varchar(64) DEFAULT NULL COMMENT '驾驶员',
  `CP_DRIVERCODE` varchar(64) DEFAULT NULL COMMENT '从业证号',
  `CP_DRIVERTIME` bigint(20) DEFAULT NULL COMMENT '刷卡时间',
  PRIMARY KEY (`CP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_collect_car_info`;
CREATE TABLE `hy_collect_car_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CC_ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `CC_VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='车辆收藏表';



DROP TABLE IF EXISTS `hy_collect_station`;
CREATE TABLE `hy_collect_station` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COLLECT_ID` bigint(20) DEFAULT NULL COMMENT '收藏对象ID',
  `STATION_ID` bigint(20) DEFAULT NULL COMMENT '服务站',
  `COLLECT_TYPE` int(11) DEFAULT NULL COMMENT '0车辆1用户',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='服务站收藏表';


DROP TABLE IF EXISTS `hy_command_cache`;
CREATE TABLE `hy_command_cache` (
  `CAR_ID` bigint(20) NOT NULL DEFAULT '0' COMMENT '车辆ID',
  `CONTENT` varchar(500) DEFAULT NULL COMMENT '内容',
  `PASSWORD` varchar(50) DEFAULT NULL COMMENT '密码',
  `REV` varchar(30) DEFAULT NULL COMMENT '转速',
  `SIGN` int(11) DEFAULT NULL COMMENT '标志',
  `ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '操作人',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型',
  `ACCOUNT_IP` varchar(50) DEFAULT NULL COMMENT '操作账户IP',
  `lock_method` varchar(20) DEFAULT NULL COMMENT '锁车方案',
  `send_flag` varchar(10) DEFAULT NULL COMMENT '是否已发出',
  PRIMARY KEY (`CAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下发指令缓存表';




DROP TABLE IF EXISTS `hy_comment_station`;
CREATE TABLE `hy_comment_station` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `COMMENT_NAME` varchar(100) DEFAULT NULL COMMENT '点评对象名称',
  `STATION_ID` bigint(20) DEFAULT NULL COMMENT '服务站ID',
  `COMMENT` varchar(200) DEFAULT NULL COMMENT '点评内容',
  `COMMENT_TYPE` int(11) DEFAULT NULL COMMENT '点评对象类型 0.车辆 1.用户',
  `comment_time` bigint(20) DEFAULT NULL COMMENT '点评时间',
  `COMMENT_TECHNOLOGY_LEVEL` int(11) DEFAULT NULL COMMENT '技术质量星级',
  `COMMENT_SERVICE_LEVEL` int(11) DEFAULT NULL COMMENT '服务质量星级',
  `COMMENT_TOTAL_LEVEL` int(11) DEFAULT NULL COMMENT '总体评价星级',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `APPOINTMENT_ID` bigint(20) DEFAULT NULL COMMENT '预约ID',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `APPOINTMENT_ID` (`APPOINTMENT_ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务站点评';



DROP TABLE IF EXISTS `hy_data_action`;
CREATE TABLE `hy_data_action` (
  `GPS_CODE` bigint(20) NOT NULL COMMENT '主键\r\n            高8位车辆ID，低10位为UTC时间\r\n            单位：秒',
  `VEHICLE_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `ALARM_INFO` bigint(20) DEFAULT NULL COMMENT '报警状态',
  `STATIS_INFO` bigint(20) DEFAULT NULL COMMENT '车辆状态',
  `LONGITUDE` int(11) DEFAULT NULL COMMENT '经度',
  `LATITUDE` int(11) DEFAULT NULL COMMENT '纬度',
  `HEIGHT` int(11) DEFAULT NULL COMMENT '高度',
  `VELOCITY` int(11) DEFAULT NULL COMMENT '速度',
  `DIRECTION` int(11) DEFAULT NULL COMMENT '方向',
  `GPS_TIME` bigint(20) DEFAULT NULL COMMENT 'GPS时间',
  `REMARK` varchar(2000) DEFAULT NULL COMMENT '附加信息(拆开后封装JOSN字符串)',
  `ORIGINALLNG` int(11) DEFAULT NULL COMMENT '原始经度',
  `ORIGINALLAT` int(11) DEFAULT NULL COMMENT '原始纬度',
  PRIMARY KEY (`GPS_CODE`),
  KEY `Index_1` (`GPS_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='拍照、报警等触发产生的位置数据';


DROP TABLE IF EXISTS `hy_draw_map`;
CREATE TABLE `hy_draw_map` (
  `DM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT_ID` int(11) NOT NULL COMMENT '服务分区编码(内蒙：国标码或者自定义编码)',
  `DM_NAME` varchar(200) DEFAULT NULL COMMENT '名称',
  `DM_TYPE` int(11) DEFAULT NULL COMMENT '类型（数据字典）',
  `DM_IMG` int(11) DEFAULT NULL COMMENT '图标编号（数据字典）',
  `DM_IS_SHARE` int(11) DEFAULT NULL COMMENT '是否共享(数据字典)',
  `DM_ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '创建账户',
  `DM_GROUP_ID` bigint(20) DEFAULT NULL COMMENT '所属车组ID',
  `DM_DATE` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `DM_UDATE` bigint(20) DEFAULT NULL COMMENT '修改日期',
  `DM_DESCRIBE` varchar(2000) DEFAULT NULL COMMENT '描述',
  `DM_NUMBER` varchar(100) NOT NULL COMMENT '区域标识(时间到秒-系统唯一)',
  `DM_ADDRESS` varchar(200) DEFAULT NULL COMMENT '地址',
  `DM_LINK` varchar(200) DEFAULT NULL COMMENT '链接',
  `DM_PARENT` bigint(20) DEFAULT NULL,
  `DM_FLAG` int(11) DEFAULT NULL,
  `DM_WIDTH` int(11) DEFAULT NULL COMMENT '路线宽度',
  PRIMARY KEY (`DM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='区域信息基础表';



DROP TABLE IF EXISTS `hy_draw_map_data`;
CREATE TABLE `hy_draw_map_data` (
  `DMD_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DM_ID` bigint(20) DEFAULT NULL COMMENT '区域信息表主键',
  `DM_LNG` bigint(20) DEFAULT NULL COMMENT '经度',
  `DM_LAT` bigint(20) DEFAULT NULL COMMENT '纬度',
  `DMD_ROAD_NUM` int(11) DEFAULT NULL COMMENT '半径',
  `DMD_INFLECTION_NUM` bigint(20) DEFAULT NULL COMMENT '路段编号',
  `DMD_SORT` bigint(20) DEFAULT NULL COMMENT '拐点编号',
  PRIMARY KEY (`DMD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='区域信息数据明细表';



DROP TABLE IF EXISTS `hy_ecustatue`;
CREATE TABLE `hy_ecustatue` (
  `TERMINAL_ID` bigint(20) NOT NULL COMMENT '终端通信号',
  `ECUSTATUE` blob COMMENT 'ecu数据',
  PRIMARY KEY (`TERMINAL_ID`),
  KEY `Index_1` (`TERMINAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ECU锁车状态';



DROP TABLE IF EXISTS `hy_employees`;
CREATE TABLE `hy_employees` (
  `E_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT` int(11) NOT NULL COMMENT '服务分区编号',
  `E_NAME` varchar(50) DEFAULT NULL COMMENT '姓名',
  `E_CARD_TYPE` int(11) DEFAULT NULL COMMENT '证件类型（数据字典）',
  `E_CARD` varchar(50) DEFAULT NULL COMMENT '证件编号',
  `E_TEAM_ID` bigint(20) DEFAULT NULL COMMENT '车队ID',
  `E_CYZGHM` varchar(50) DEFAULT NULL COMMENT '从业资格证号码',
  `E_DRIVER_NUMBER` varchar(50) DEFAULT NULL COMMENT '驾驶员号码',
  `E_CARD_DQK` varchar(100) DEFAULT NULL COMMENT '登签卡号',
  `DEL_FLAG` int(11) DEFAULT NULL COMMENT '逻辑删除标志',
  PRIMARY KEY (`E_ID`),
  KEY `Index_1` (`E_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='从业人员信息表(驾驶员、押运员）';


DROP TABLE IF EXISTS `hy_employees_detail`;
CREATE TABLE `hy_employees_detail` (
  `E_ID` bigint(20) NOT NULL COMMENT '人员ID(业务主键)(地区编号+guid生成规则)',
  `E_CARD_FZJG` varchar(200) DEFAULT NULL COMMENT '发证机构',
  `E_SEX` int(11) DEFAULT NULL COMMENT '性别（数据字典）',
  `E_NATION` int(11) DEFAULT NULL COMMENT '民族（数据字典）',
  `E_COMPANY` varchar(200) DEFAULT NULL COMMENT '单位',
  `E_ZIPCODE` varchar(50) DEFAULT NULL COMMENT '邮编',
  `E_LINKTEL` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `E_ADDRESS` varchar(200) DEFAULT NULL COMMENT '家庭地址',
  `E_DATE` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `E_ACCOUNT` varchar(50) DEFAULT NULL COMMENT '创建人',
  `E_DESCRIBE` varchar(2000) DEFAULT NULL COMMENT '备注',
  `E_EMAIL` varchar(50) DEFAULT NULL COMMENT '邮件地址',
  KEY `Index_1` (`E_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='从业人员明细信息表（驾驶员、押运员）';



DROP TABLE IF EXISTS `hy_eventsend`;
CREATE TABLE `hy_eventsend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `carNo` varchar(40) DEFAULT NULL COMMENT '车牌号',
  `carColor` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `date` bigint(20) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL COMMENT '内容',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `even_id` bigint(20) DEFAULT NULL COMMENT '事件ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`id`),
  KEY `Index_2` (`carNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件下发';



DROP TABLE IF EXISTS `hy_financing_platform`;
CREATE TABLE `hy_financing_platform` (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `platform_name` varchar(200) DEFAULT NULL COMMENT '平台名称',
  `organization_code` varchar(50) DEFAULT NULL COMMENT '组织机构代码证号',
  `address_code` varchar(50) DEFAULT NULL COMMENT '省市编码',
  `link_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `link_telpone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `address_common` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `common` varchar(200) DEFAULT NULL COMMENT '备注',
  `del_flag` int(10) DEFAULT '0' COMMENT '删除标识：0可用 1删除 默认0可用',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_gov_org`;
CREATE TABLE `hy_gov_org` (
  `GOV_CODE` int(11) NOT NULL COMMENT '行政区域',
  `GOV_NAME` varchar(128) NOT NULL COMMENT '行政区名称',
  `GOV_PARENT_CODE` int(11) NOT NULL COMMENT '行政区编号',
  `GOV_CENTER_ID` int(11) DEFAULT NULL COMMENT '行政区上级ID',
  `GOV_WEATHER_CODE` varchar(128) DEFAULT NULL COMMENT '天气编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区域表';


DROP TABLE IF EXISTS `hy_gps_info`;
CREATE TABLE `hy_gps_info` (
  `VEH_NUM` varchar(50) DEFAULT NULL,
  `VEH_COLOR` int(11) DEFAULT NULL,
  `SIM` bigint(20) DEFAULT NULL,
  `G_DATE` bigint(20) DEFAULT NULL,
  `PLATFORM` int(11) DEFAULT NULL,
  `LONGITUDE` bigint(20) DEFAULT NULL,
  `LATITUDE` bigint(20) DEFAULT NULL,
  `SPEED1` double DEFAULT NULL,
  `SPEED2` double DEFAULT NULL,
  `MILEAGE` double DEFAULT NULL,
  `DIRECTION` bigint(20) DEFAULT NULL,
  `ELEVATION` bigint(20) DEFAULT NULL,
  `ACC` int(11) DEFAULT NULL,
  `VEH_STATE` varchar(70) DEFAULT NULL,
  `VEH_ALARM` varchar(70) DEFAULT NULL,
  `STATE1` varchar(70) DEFAULT NULL,
  `STATE2` varchar(70) DEFAULT NULL,
  `ALARM1` varchar(70) DEFAULT NULL,
  `ALARM2` varchar(70) DEFAULT NULL,
  `OIL` double DEFAULT NULL,
  `PATCH` int(11) DEFAULT NULL,
  `LNG_CORRENT` bigint(20) DEFAULT NULL,
  `LAT_CORRENT` bigint(20) DEFAULT NULL,
  `STAR_STATUS` int(11) DEFAULT NULL,
  `STAR_NUMBER` int(11) DEFAULT NULL,
  `RECEIVE_DATE` bigint(20) DEFAULT NULL,
  KEY `Index_1` (`G_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hy_iod`;
CREATE TABLE `hy_iod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `carNo` varchar(40) DEFAULT NULL COMMENT '车牌号',
  `carColor` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `date` bigint(20) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL COMMENT '问题',
  `iod_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_1` (`id`),
  KEY `Index_2` (`carNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='information on demand 信息点播';



DROP TABLE IF EXISTS `hy_last_gps`;
CREATE TABLE `hy_last_gps` (
  `TERMINAL_ID` bigint(20) NOT NULL COMMENT '车辆ID',
  `GPS_DATA` blob COMMENT 'GPS数据',
  PRIMARY KEY (`TERMINAL_ID`),
  KEY `Index_1` (`TERMINAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='末次位置';



DROP TABLE IF EXISTS `hy_lsreport`;
CREATE TABLE `hy_lsreport` (
  `CHASSIS_NO` varchar(11) NOT NULL COMMENT '底盘号',
  `WORK_CENTER` varchar(256) DEFAULT NULL COMMENT '工作中心',
  `MATERIALS_ID` varchar(20) DEFAULT NULL COMMENT '物资编码',
  `MATERIALS_NAME` varchar(256) DEFAULT NULL COMMENT '物资名称',
  `PLAN_TIME` varchar(30) DEFAULT NULL COMMENT '计划时间',
  `INSTOCK_TIME` varchar(30) DEFAULT NULL COMMENT '入库时间',
  `QUALIFIED_TIME` varchar(30) DEFAULT NULL COMMENT '合格时间',
  `REMOVAL_TIME` varchar(30) DEFAULT NULL COMMENT '出库时间',
  `STORAGE_LOCATION` varchar(256) DEFAULT NULL COMMENT '库位',
  PRIMARY KEY (`CHASSIS_NO`),
  KEY `CHASSIS_NO` (`CHASSIS_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆生产线漏扫报表';



DROP TABLE IF EXISTS `hy_mes_temp`;
CREATE TABLE `hy_mes_temp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `VIN` varchar(100) DEFAULT '' COMMENT 'VIN码',
  `DIPAN` varchar(100) DEFAULT '' COMMENT '底盘号',
  `CXM` varchar(50) DEFAULT '' COMMENT '车型码',
  `SBID` varchar(50) DEFAULT '' COMMENT '东风设备id',
  `online_time` bigint(20) DEFAULT NULL COMMENT '上线时间',
  `order_number` varchar(30) DEFAULT '' COMMENT '订单号',
  `team_name` varchar(200) DEFAULT '' COMMENT '经销商名称',
  `ZCEWM` varchar(50) DEFAULT NULL COMMENT '整车二维码',
  `DAT_TIME` bigint(20) DEFAULT NULL COMMENT '整车二维码扫码时间',
  PRIMARY KEY (`id`),
  KEY `primary_key_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `hy_not_sold_out_of_dealer_library_exception_report`;
CREATE TABLE `hy_not_sold_out_of_dealer_library_exception_report` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `CHASSIS_NUM` varchar(100) DEFAULT NULL COMMENT '底盘号',
  `CAR_CPH` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `CAR_TYPE` int(11) DEFAULT NULL COMMENT '车辆类型（数据字典）',
  `car_model_code` varchar(20) DEFAULT NULL COMMENT '车型码',
  `TEAM_ID` bigint(20) DEFAULT NULL COMMENT '经销商ID',
  `TEAM_DEALER_CODE` varchar(50) DEFAULT NULL COMMENT '经销商编码',
  `TEAM_NAME` varchar(200) DEFAULT NULL COMMENT '经销商名称',
  `SECD_NAME_LATLON` text COMMENT '二级网点信息',
  `WAREHOUSE_LOG` bigint(20) DEFAULT NULL COMMENT '入库位置-经度',
  `WAREHOUSE_LAT` bigint(20) DEFAULT NULL COMMENT '入库位置-纬度',
  `WAREHOUSE_TIME` bigint(20) DEFAULT NULL COMMENT '入库时间',
  `OUT_OF_LIBRARY_LOG` bigint(20) DEFAULT NULL COMMENT '出库位置-经度',
  `OUT_OF_LIBRARY_LAT` bigint(20) DEFAULT NULL COMMENT '出库位置-纬度',
  `OUT_OF_LIBRARY_TIME` bigint(20) DEFAULT NULL COMMENT '出库时间',
  `CREATE_TIME` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='未售车辆出经销商库异常报表';


DROP TABLE IF EXISTS `hy_organization`;
CREATE TABLE `hy_organization` (
  `O_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT` int(11) NOT NULL COMMENT '服务分区编码',
  `O_NAME` varchar(200) DEFAULT NULL COMMENT '组织名称',
  `O_PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级组织ID',
  `O_LINKMAN` varchar(200) DEFAULT NULL COMMENT '组织联系人',
  `O_LINKTEL` varchar(50) DEFAULT NULL COMMENT '组织联系电话',
  `O_COMPANY` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `O_CDATE` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `O_DESCRIBE` varchar(2000) DEFAULT NULL COMMENT '详细描述',
  PRIMARY KEY (`O_ID`),
  KEY `Index_1` (`O_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织关系表';



DROP TABLE IF EXISTS `hy_phonebook`;
CREATE TABLE `hy_phonebook` (
  `PHONE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PHONE_PERSON` varchar(100) NOT NULL COMMENT '联系人',
  `PHONE_TEL` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `PHONE_TYPE` int(11) DEFAULT NULL COMMENT '接听策略',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `DISTRICT_ID` bigint(20) DEFAULT NULL COMMENT '分区',
  PRIMARY KEY (`PHONE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hy_platform_log`;
CREATE TABLE `hy_platform_log` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOG_NAME` varchar(50) DEFAULT NULL COMMENT '操作名称',
  `LOG_VALUE` int(11) DEFAULT NULL COMMENT '操作结果',
  `LOG_TYPE` int(11) DEFAULT NULL COMMENT '操作日志类型',
  `LOG_CONTENT` varchar(2000) DEFAULT NULL COMMENT '操作内容描述',
  `LOG_DATE` bigint(20) DEFAULT NULL COMMENT '操作日期',
  `LOG_ACCOUNT_IP` varchar(50) DEFAULT NULL COMMENT '操作账户IP',
  `LOG_ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '操作账户名称',
  `LOG_OPEROBJ` varchar(100) DEFAULT NULL COMMENT '操作对象',
  PRIMARY KEY (`LOG_ID`),
  KEY `Index_1` (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=406 DEFAULT CHARSET=utf8 COMMENT='平台日志表';



DROP TABLE IF EXISTS `hy_producer`;
CREATE TABLE `hy_producer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCER_NAME` varchar(200) DEFAULT NULL COMMENT '制造商',
  `PRODUCER_CODE` int(11) DEFAULT NULL COMMENT '制造商编码',
  `LINKMAN` varchar(50) DEFAULT NULL COMMENT '联系人',
  `TELPHONE` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `ADDRESS` varchar(200) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hy_question`;
CREATE TABLE `hy_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `carNo` varchar(40) DEFAULT NULL COMMENT '车牌号',
  `carColor` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `qdate` bigint(20) DEFAULT NULL,
  `question` varchar(200) DEFAULT NULL COMMENT '问题',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`id`),
  KEY `Index_2` (`carNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问下发';



DROP TABLE IF EXISTS `hy_ra_mapping`;
CREATE TABLE `hy_ra_mapping` (
  `R_CODE` int(11) DEFAULT NULL COMMENT '角色功能编码',
  `A_CODE` int(11) DEFAULT NULL COMMENT '权限功能编码',
  UNIQUE KEY `ra_uni` (`R_CODE`,`A_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色功能映射关系表';



DROP TABLE IF EXISTS `hy_repair`;
CREATE TABLE `hy_repair` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `car_num` varchar(200) DEFAULT NULL COMMENT '车牌号',
  `car_owner` bigint(20) DEFAULT NULL COMMENT '车主主键',
  `team` bigint(20) DEFAULT NULL COMMENT '车组主键',
  `repair_type` int(11) DEFAULT NULL COMMENT '维保类型',
  `service_station` bigint(20) DEFAULT NULL COMMENT '服务站',
  `repair_content` varchar(500) DEFAULT NULL COMMENT '维保内容',
  `repair_date` bigint(20) DEFAULT NULL COMMENT '维保日期',
  `repair_reason` varchar(200) DEFAULT NULL COMMENT '维保原因',
  `repair_cost` varchar(50) DEFAULT NULL COMMENT '维保费用',
  `repair_mileage` varchar(50) DEFAULT NULL COMMENT '维保里程',
  `repair_num` varchar(50) DEFAULT NULL COMMENT '维保编号',
  `brokerage` varchar(50) DEFAULT NULL COMMENT '经手人',
  `service_manager` varchar(50) DEFAULT NULL COMMENT '服务经理',
  `validate` int(11) DEFAULT NULL COMMENT '是否有效',
  `repair_times` bigint(20) DEFAULT NULL COMMENT '维保时长',
  `CHASSIS_NUM` varchar(100) DEFAULT NULL COMMENT '底盘号',
  `car_id` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='维保管理';


DROP TABLE IF EXISTS `hy_role`;
CREATE TABLE `hy_role` (
  `R_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT` int(11) DEFAULT NULL COMMENT '服务分区编码',
  `R_NAME` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `R_CODE` int(11) DEFAULT NULL COMMENT '角色功能编码',
  `R_DESCRIBE` varchar(2000) DEFAULT NULL COMMENT '角色描述',
  `ORG_ID` bigint(20) DEFAULT NULL COMMENT '组织ID',
  `R_DEFAULT` int(11) DEFAULT NULL COMMENT '定义方式0初始化1自定义',
  `create_account` bigint(20) DEFAULT NULL COMMENT '创建者',
  `role_type` bigint(30) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`R_ID`),
  KEY `Index_1` (`R_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色信息表';



DROP TABLE IF EXISTS `hy_roletype_action_mapping`;
CREATE TABLE `hy_roletype_action_mapping` (
  `role_type` bigint(20) NOT NULL COMMENT '角色类型',
  `action_id` bigint(20) NOT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_secdstation`;
CREATE TABLE `hy_secdstation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '二级服务站名称',
  `code` varchar(50) DEFAULT NULL COMMENT '二级服务站编码',
  `longitude` bigint(20) DEFAULT NULL COMMENT '经度',
  `latitude` bigint(20) DEFAULT NULL COMMENT '纬度',
  `address` varchar(50) DEFAULT NULL COMMENT '逆地理地址(供APP)',
  `work_radius` int(11) DEFAULT NULL COMMENT '工作半经',
  `station_code` varchar(50) DEFAULT NULL COMMENT '所属服务站编码',
  `station_id` bigint(20) DEFAULT NULL COMMENT '所属服务站id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_secdteam`;
CREATE TABLE `hy_secdteam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '二级经销商名称',
  `code` varchar(50) DEFAULT NULL COMMENT '二级经销商编码',
  `work_type` int(11) DEFAULT NULL COMMENT '工作类型',
  `longitude` bigint(20) DEFAULT NULL COMMENT '经度',
  `latitude` bigint(20) DEFAULT NULL COMMENT '纬度',
  `address` varchar(50) DEFAULT NULL COMMENT '逆地理地址(供APP)',
  `work_radius` int(11) DEFAULT NULL COMMENT '工作半经',
  `dealer_code` varchar(50) DEFAULT NULL COMMENT '所属经销商编码',
  `dealer_id` bigint(20) DEFAULT NULL COMMENT '所属经销商id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_service_station`;
CREATE TABLE `hy_service_station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `station_name` varchar(200) DEFAULT NULL COMMENT '服务站名称',
  `name_forshort` varchar(100) DEFAULT NULL COMMENT '简称',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `longitude` bigint(20) DEFAULT NULL COMMENT '经度',
  `latitude` bigint(20) DEFAULT NULL COMMENT '纬度',
  `radius` int(11) DEFAULT NULL COMMENT '半径',
  `povince` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `service_manager` varchar(50) DEFAULT NULL COMMENT '服务经理',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `messages` varchar(200) DEFAULT NULL COMMENT '推送内容',
  `team_id` bigint(20) DEFAULT NULL COMMENT '分组ID',
  `stranded_max_time` bigint(20) DEFAULT NULL COMMENT '最大滞留时间',
  `star_level` int(11) DEFAULT NULL COMMENT '星级',
  `picture` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `SERVICE_Type` int(11) DEFAULT NULL COMMENT '服务类型0服务类(配件)1配件类',
  `SERVICE_CONTENT` varchar(400) DEFAULT NULL COMMENT '服务内容，通过_连接多个类容编号',
  `PARTS_CONTENT` varchar(400) DEFAULT NULL COMMENT '配件明细，通过_连接多个类容编号',
  `service_radius` int(11) DEFAULT NULL COMMENT '服务器半径',
  `service_code` varchar(100) DEFAULT NULL,
  `tool_content` varchar(400) DEFAULT NULL,
  `station_type` varchar(50) DEFAULT NULL,
  `creat_Date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '创建者',
  `fixed_telephone` varchar(50) DEFAULT NULL COMMENT '固定电话',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='服务站';



DROP TABLE IF EXISTS `hy_sim_modify`;
CREATE TABLE `hy_sim_modify` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOG_VEHICLE` bigint(20) DEFAULT NULL,
  `LOG_CPH` varchar(22) DEFAULT NULL,
  `LOG_DATE` bigint(20) DEFAULT NULL,
  `LOG_TYPE` varchar(32) DEFAULT NULL,
  `OLD_CONTENT` varchar(300) DEFAULT NULL,
  `NEW_CONTENT` varchar(300) DEFAULT NULL,
  `ACCOUNT_IP` varchar(20) DEFAULT NULL,
  `ACCOUNT_ID` varchar(32) DEFAULT NULL,
  `SIM` bigint(20) DEFAULT NULL,
  `ACCOUNT_NAME` varchar(32) DEFAULT NULL,
  `DISTRICT` int(11) DEFAULT NULL,
  `TEAM_NAME` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_st_master`;
CREATE TABLE `hy_st_master` (
  `M_ID` bigint(20) NOT NULL COMMENT '自增标识',
  `M_VEH` int(11) DEFAULT NULL COMMENT '车辆ID',
  `DM_NUMBER` bigint(20) DEFAULT NULL COMMENT '区域标识',
  `M_TYPE` int(11) DEFAULT NULL COMMENT '参数设置类型（数据字典）',
  `M_CONTENT` blob COMMENT '参数设置内容(直接存protobuf字符串)',
  PRIMARY KEY (`M_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域报警参数设置基础表';



DROP TABLE IF EXISTS `hy_team`;
CREATE TABLE `hy_team` (
  `T_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT` int(11) NOT NULL COMMENT '分区服务编码',
  `T_NAME` varchar(200) DEFAULT NULL COMMENT '分组名称',
  `T_TYPE` int(11) DEFAULT NULL COMMENT '是否是公司',
  `T_DATE` bigint(20) DEFAULT NULL COMMENT '分组创建时间',
  `T_ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '上级分组',
  `T_LINKMAN` varchar(200) DEFAULT NULL COMMENT '联系人',
  `T_LINKTEL` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `T_DESC` varchar(2000) DEFAULT NULL COMMENT '描述',
  `COMPANY_NAME` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `COMPANY_BUSINESS_SCOPE` varchar(200) DEFAULT NULL COMMENT '公司经营范围',
  `COMPANY_ADDRESS` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `COMPANY_CODE` varchar(50) DEFAULT NULL COMMENT '邮编',
  `COMPANY_TEL` varchar(50) DEFAULT NULL COMMENT '公司电话',
  `COMPANY_FAX` varchar(50) DEFAULT NULL COMMENT '传真',
  `COMPANY_LINKMAN` varchar(200) DEFAULT NULL COMMENT '公司联系人',
  `COMPANY_LINKTEL` varchar(50) DEFAULT NULL COMMENT '公司联系人电话',
  `LICENCE_NUMBER` varchar(200) DEFAULT NULL COMMENT '公司营业执照号',
  `DEL_FLAG` int(11) DEFAULT '0' COMMENT '逻辑删除标记(1:删除，0：正常，default：0)',
  `DEALER_CODE` varchar(50) DEFAULT NULL COMMENT '经销商编码',
  `manage_brand` varchar(100) DEFAULT NULL COMMENT '经营品牌',
  `team_lat` bigint(20) DEFAULT NULL COMMENT '经销商经度',
  `team_lon` bigint(20) DEFAULT NULL COMMENT '经销商纬度',
  `enable_radius` int(11) DEFAULT '30000' COMMENT '激活半径（M）',
  `lock_radius` int(11) DEFAULT '20000' COMMENT '锁车半径（M）',
  `secd_name_latlon` text COMMENT '二级网点信息',
  `team_picture` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `land_area_radius` int(11) DEFAULT '2000' COMMENT '占地半径，默认2000（M）',
  PRIMARY KEY (`T_ID`),
  KEY `Index_1` (`T_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=745 DEFAULT CHARSET=utf8 COMMENT='车组信息表';



DROP TABLE IF EXISTS `hy_terminal`;
CREATE TABLE `hy_terminal` (
  `T_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DISTRICT` int(11) NOT NULL COMMENT '分区服务编码',
  `T_SIM` varchar(50) DEFAULT NULL COMMENT '车载终端SIM卡号',
  `T_COMMUNICATION_ID` bigint(20) DEFAULT NULL COMMENT '通讯号',
  `T_TEAM_ID` bigint(20) DEFAULT NULL COMMENT '车载终端所属组',
  `T_CODE` varchar(50) DEFAULT NULL COMMENT '终端自编号',
  `T_TYPE_ID` int(11) DEFAULT NULL COMMENT '硬件型号(终端类型)',
  `T_SIM_SIGN` int(11) DEFAULT NULL COMMENT 'SIM卡提供方',
  `DEL_FLAG` int(11) DEFAULT '0' COMMENT '逻辑删除标记(0:删除，1：正常，default：1)',
  `ALARM_HANDLE` int(11) DEFAULT NULL,
  `T_AUTO_COMMUNICATION_ID` bigint(20) DEFAULT NULL COMMENT '生成永久通信id',
  `AUTO_FLAG` int(11) DEFAULT NULL COMMENT '是否使用自动生成通信id标识',
  `IMPORT_TIME` bigint(20) DEFAULT NULL COMMENT '导入时间',
  `T_STYLE` int(11) DEFAULT NULL COMMENT '终端类型',
  `T_MODEL` varchar(50) DEFAULT NULL COMMENT '终端型号',
  `T_COMMON` varchar(200) DEFAULT NULL COMMENT '备注',
  `T_MIC` int(11) DEFAULT '0' COMMENT 'MIC：影响PC及手机端监听功能的显示与否（是1，否0）',
  `T_CAMERA_CHANNEL` varchar(30) DEFAULT NULL COMMENT '摄像头：默认不勾选通道',
  `t_label_id` varchar(50) DEFAULT NULL COMMENT '设备标签ID,对于其他车厂设备，该设备标签ID与设备ID(终端自编号)是一致的，对东风两者不同',
  `applianceEngineType` bigint(20) NOT NULL DEFAULT '0' COMMENT ' 适用发动机类型：终端初始化参数设置保存的数据',
  `applianceCarType` bigint(20) NOT NULL DEFAULT '0' COMMENT ' 适用车辆类型：终端初始化参数设置保存的数据',
  PRIMARY KEY (`T_ID`),
  KEY `T_STYLE` (`T_STYLE`),
  KEY `T_COMMUNICATION_ID` (`T_COMMUNICATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='终端基本信息';



DROP TABLE IF EXISTS `hy_terminal_log`;
CREATE TABLE `hy_terminal_log` (
  `LOG_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOG_NAME` varchar(50) DEFAULT NULL COMMENT '操作名称',
  `LOG_VALUE` int(11) DEFAULT NULL COMMENT '操作结果',
  `LOG_TYPE` int(11) DEFAULT NULL COMMENT '操作日志类型',
  `LOG_SIM` bigint(20) NOT NULL COMMENT 'sim卡',
  `LOG_CONTENT` varchar(2000) DEFAULT NULL COMMENT '操作内容描述',
  `LOG_DATE` bigint(20) DEFAULT NULL COMMENT '操作日期',
  `LOG_ACCOUNT_IP` varchar(50) DEFAULT NULL COMMENT '操作账户IP',
  `LOG_ACCOUNT_NAME` varchar(50) DEFAULT NULL COMMENT '操作账户名称',
  `LOG_COMMON` int(11) DEFAULT NULL COMMENT '日志备注',
  `module_type` int(11) DEFAULT NULL COMMENT '模块类型',
  `CAR_CPH` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `COMPANY_NAME` varchar(50) DEFAULT NULL COMMENT '经销商',
  `CAR_OWNER` varchar(50) DEFAULT NULL COMMENT '所属客户',
  `CHASSIS_NUM` varchar(50) DEFAULT NULL COMMENT '底盘号',
  `LOG_STATE` int(11) NOT NULL DEFAULT '-3' COMMENT '操作状态',
  `LOG_LOCK_COMMON` varchar(50) DEFAULT NULL COMMENT '到库锁车中操作备注',
  PRIMARY KEY (`LOG_ID`),
  KEY `module_type` (`module_type`),
  KEY `LOG_SIM` (`LOG_SIM`),
  KEY `LOG_SIM2` (`LOG_SIM`,`LOG_ACCOUNT_NAME`,`LOG_NAME`,`LOG_TYPE`,`LOG_VALUE`)
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=utf8 COMMENT='终端操作日志表';


DROP TABLE IF EXISTS `hy_terminal_model`;
CREATE TABLE `hy_terminal_model` (
  `TM_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '业务主键',
  `TM_NAME` varchar(100) NOT NULL COMMENT '终端型号',
  `TM_FACTORY_NAME` varchar(100) NOT NULL COMMENT '厂家名称',
  `TM_FACTORY_NUM` varchar(50) NOT NULL COMMENT '厂家编号',
  `TM_PUBLIC_NUM` varchar(50) NOT NULL COMMENT '交通部公告批次',
  `TM_LOCTION_MODEL` varchar(50) NOT NULL COMMENT '定位模式',
  `TM_COMMU_MODEL` varchar(50) NOT NULL COMMENT '通信模式',
  `TM_CAR_MATCHED` varchar(50) NOT NULL COMMENT '适用车型',
  `TM_MODEL_NUM` varchar(50) DEFAULT NULL COMMENT '型号编号',
  `TM_NOTES` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`TM_ID`),
  KEY `TM_NAME_INDEX` (`TM_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='终端型号表';



DROP TABLE IF EXISTS `hy_terminal_register`;
CREATE TABLE `hy_terminal_register` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vehicle_no` varchar(50) DEFAULT NULL,
  `vehicle_color` int(11) DEFAULT NULL,
  `provinceIdentify` bigint(20) DEFAULT NULL COMMENT '省域 ID',
  `cityIdentify` bigint(20) DEFAULT NULL COMMENT '市县域 ID',
  `produceCoding` varchar(50) DEFAULT NULL COMMENT '制造商编码',
  `terminalModel` varchar(100) DEFAULT NULL COMMENT '终端型号',
  `terminalIdentify` varchar(50) DEFAULT NULL COMMENT '终端编号',
  `sim` varchar(50) DEFAULT NULL,
  `accessTocken` bigint(20) DEFAULT NULL COMMENT '鉴权码',
  PRIMARY KEY (`id`),
  KEY `register_index_1` (`sim`),
  KEY `Index_2` (`id`),
  KEY `Index_3` (`accessTocken`)
) ENGINE=InnoDB AUTO_INCREMENT=43494 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `hy_terminal_setting`;
CREATE TABLE `hy_terminal_setting` (
  `ST_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `ST_TYPE` int(11) DEFAULT NULL COMMENT '终端设置类型（数据字典）',
  `ST_CONTENT` blob COMMENT '设置内容(直接存protobuf字符串)',
  `ST_DATE` bigint(20) DEFAULT NULL COMMENT '日期',
  `ST_ACCOUNT` varchar(50) DEFAULT NULL COMMENT '设置账号',
  PRIMARY KEY (`ST_ID`),
  KEY `Index_1` (`ST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端参数设置表';



DROP TABLE IF EXISTS `hy_terminal_type`;
CREATE TABLE `hy_terminal_type` (
  `TYPE_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '终端型号',
  `PRODUCER_ID` bigint(20) DEFAULT NULL COMMENT '制造商ID',
  `TRAVEL_VERSION` int(11) DEFAULT NULL COMMENT '行驶记录仪版本',
  `PROTOCOL_VERSION` int(11) DEFAULT NULL COMMENT '协议版本',
  `GPS_MODULE` int(11) DEFAULT NULL COMMENT 'GPS模块',
  PRIMARY KEY (`TYPE_ID`),
  KEY `Index_1` (`TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=200304 DEFAULT CHARSET=utf8 COMMENT='终端类型表';


DROP TABLE IF EXISTS `hy_user_login_record`;
CREATE TABLE `hy_user_login_record` (
  `LOGIN_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOGIN_NAME` varchar(50) DEFAULT NULL COMMENT '登录用户名',
  `LOGIN_IP` varchar(20) DEFAULT NULL COMMENT '登录用户IP',
  `START_DATE` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `END_DATE` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `LOGIN_STATUS` int(11) DEFAULT NULL COMMENT '状态（1在线，0不在线）',
  `CONTINUE_TIME` bigint(20) DEFAULT NULL COMMENT '在线时长',
  `EXIT_TYPE` int(11) DEFAULT NULL COMMENT '退出类型',
  PRIMARY KEY (`LOGIN_ID`),
  KEY `Index_1` (`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户记录';



DROP TABLE IF EXISTS `hy_waybill_info`;
CREATE TABLE `hy_waybill_info` (
  `wid` bigint(20) NOT NULL AUTO_INCREMENT,
  `carNum` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `carColor` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `sim` bigint(20) DEFAULT NULL,
  `date` bigint(20) DEFAULT NULL COMMENT '时间',
  `content` varchar(400) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`wid`),
  KEY `Index_1` (`wid`),
  KEY `Index_2` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运单表';



DROP TABLE IF EXISTS `hy_wsxreport`;
CREATE TABLE `hy_wsxreport` (
  `CHASSIS_NO` varchar(11) NOT NULL COMMENT '底盘号',
  `WORK_CENTER` varchar(256) DEFAULT NULL COMMENT '工作中心',
  `MATERIALS_ID` varchar(20) DEFAULT NULL COMMENT '物资编码',
  `MATERIALS_NAME` varchar(256) DEFAULT NULL COMMENT '物资名称',
  `PLAN_TIME` varchar(30) DEFAULT NULL COMMENT '计划时间',
  `INSTOCK_TIME` varchar(30) DEFAULT NULL COMMENT '入库时间',
  `QUALIFIED_TIME` varchar(30) DEFAULT NULL COMMENT '合格时间',
  `REMOVAL_TIME` varchar(30) DEFAULT NULL COMMENT '出库时间',
  `STORAGE_LOCATION` varchar(256) DEFAULT NULL COMMENT '库位',
  `SALE_ID` varchar(11) DEFAULT NULL COMMENT '销售编码',
  `LAST_TIME` varchar(30) DEFAULT NULL COMMENT '末次时间',
  `LAST_LOCATION` varchar(256) DEFAULT NULL COMMENT '末次位置',
  `XXSJ` varchar(30) DEFAULT NULL COMMENT '下线时间',
  `ZXSJ` varchar(30) DEFAULT NULL COMMENT '转运时间',
  PRIMARY KEY (`CHASSIS_NO`),
  KEY `CHASSIS_NO` (`CHASSIS_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆入库未上线报表';



DROP TABLE IF EXISTS `platform_alarm_handle`;
CREATE TABLE `platform_alarm_handle` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ALARM_TYPE` bigint(20) NOT NULL COMMENT '报警类型',
  `SIM` bigint(20) NOT NULL COMMENT 'sim',
  `HANDLE_RESULT` int(11) NOT NULL COMMENT '报警处理结果',
  `HANDLE_DATE` bigint(20) NOT NULL COMMENT '报警处理时间（秒）',
  `HANDLE_ACCOUNT` varchar(100) DEFAULT NULL COMMENT '报警处理账户',
  `HANDLE_COMMON` varchar(200) DEFAULT NULL,
  `ALARM_DATE` bigint(20) DEFAULT NULL COMMENT '报警时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `platform_data_info`;
CREATE TABLE `platform_data_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PLATFORM_SOURCE` varchar(50) DEFAULT NULL COMMENT '平台',
  `PLATFORM_TYPE` int(11) DEFAULT NULL COMMENT '上/下行',
  `PLATFORM_TARGET` varchar(50) DEFAULT NULL COMMENT '平台目标',
  `DATE` bigint(20) DEFAULT NULL COMMENT '时间',
  `TARGET_OBJECT` varchar(50) DEFAULT NULL COMMENT '目标对象',
  `MSG_TYPE` int(11) DEFAULT NULL COMMENT '信息类型',
  `CONTENT` varchar(500) DEFAULT NULL COMMENT '内容',
  `HANDLE_RESULT` int(11) DEFAULT NULL COMMENT '处理结果',
  `HANDLE_PERSON` varchar(50) DEFAULT NULL COMMENT '处理人',
  `HANDLE_DATE` bigint(20) DEFAULT NULL COMMENT '处理时间',
  `HANDLE_COMMON` varchar(500) DEFAULT NULL COMMENT '处理描述',
  `COMPANY` int(11) DEFAULT NULL COMMENT '公司',
  `info_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`PLATFORM_TYPE`),
  KEY `Index_2` (`MSG_TYPE`),
  KEY `Index_3` (`HANDLE_RESULT`),
  KEY `Index_4` (`COMPANY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台监管信息';



DROP TABLE IF EXISTS `platform_info`;
CREATE TABLE `platform_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PLATFORM_NAME` varchar(100) DEFAULT NULL COMMENT '平台名称',
  `PLATFORM_IP` varchar(30) DEFAULT NULL COMMENT 'IP',
  `PLATFORM_PORT` int(11) DEFAULT NULL COMMENT '端口',
  `MAC_PASSWORD` varchar(50) DEFAULT NULL COMMENT 'MAC加密密码',
  `TYPE` int(11) DEFAULT NULL COMMENT '0 上行 1下行',
  `ACCOUNT` varchar(100) DEFAULT NULL COMMENT '绑定本地用户',
  `LINK_CODE` int(11) DEFAULT NULL COMMENT '连接号',
  `LINK_USERNAME` varchar(100) DEFAULT NULL COMMENT '连接用户名',
  `LINK_PASSWORD` varchar(50) DEFAULT NULL COMMENT '连接密码',
  `PROVINCE` int(11) DEFAULT NULL COMMENT '省份',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `platform_link_status`;
CREATE TABLE `platform_link_status` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STATUS_TYPE` int(11) DEFAULT NULL COMMENT '状态类型（0中断1建立连接2注销）',
  `LINK_DATE` bigint(20) DEFAULT NULL COMMENT '时间（秒）',
  `LINK_TYPE` int(11) DEFAULT NULL COMMENT '链路类型（1主链路2从链路）',
  `LINK_DESC` varchar(200) DEFAULT NULL COMMENT '描述（IP）',
  `PLATFORM_ID` bigint(20) DEFAULT NULL COMMENT '809平台ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `platform_post_query`;
CREATE TABLE `platform_post_query` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INFO_ID` bigint(20) DEFAULT NULL COMMENT '信息ID',
  `OBJECT_TYPE` int(11) DEFAULT NULL COMMENT '查岗对象类型',
  `OBJECT_ID` bigint(20) DEFAULT NULL COMMENT '查岗对象ID',
  `REQUEST_CONTENT` varchar(200) DEFAULT NULL COMMENT '查岗内容',
  `REQUEST_DATE` bigint(20) DEFAULT NULL COMMENT '查岗时间（秒）',
  `SUPERIOR_PLATFORM_NAME` varchar(100) DEFAULT NULL COMMENT '上级平台名称',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='809 平台查岗请求';



DROP TABLE IF EXISTS `platform_post_query_ack`;
CREATE TABLE `platform_post_query_ack` (
  `P_ID` bigint(20) DEFAULT NULL COMMENT '查岗ID',
  `ANSWER_CONTENT` varchar(200) DEFAULT NULL COMMENT '应答内容',
  `ANSWER_DATE` bigint(20) DEFAULT NULL COMMENT '应答日期',
  `ANSWER_ACCOUNT` varchar(50) DEFAULT NULL COMMENT '应答用户',
  `ANSWER_COMPANY` varchar(100) DEFAULT NULL COMMENT '应答公司',
  KEY `Index_1` (`P_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='809查岗应答';



DROP TABLE IF EXISTS `platform_server_status`;
CREATE TABLE `platform_server_status` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `server_num` int(10) DEFAULT NULL COMMENT '服务号',
  `update_time` bigint(32) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `platform_totalrecvmsg`;
CREATE TABLE `platform_totalrecvmsg` (
  `ID` bigint(20) NOT NULL,
  `TOTAL` int(11) DEFAULT NULL COMMENT '车辆接收数',
  `START_TIME` bigint(20) DEFAULT NULL COMMENT '开始时间',
  `END_TIME` bigint(20) DEFAULT NULL COMMENT '结束时间',
  `PLATFORM_ID` bigint(20) DEFAULT NULL COMMENT '平台ID',
  `RECV_TIME` bigint(20) DEFAULT NULL COMMENT '接收时间',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆定位信息数量通知消息';



DROP TABLE IF EXISTS `platform_urge_todo_ack`;
CREATE TABLE `platform_urge_todo_ack` (
  `U_ID` bigint(20) DEFAULT NULL,
  `SUPERVISOR_RESULT` int(11) DEFAULT NULL COMMENT '督办结果',
  `SUPERVISOR_DATE` bigint(20) DEFAULT NULL COMMENT '督办时间',
  `SUPERVISOR_ACCOUNT` varchar(50) DEFAULT NULL COMMENT '账户',
  KEY `Index_1` (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督办应答';


DROP TABLE IF EXISTS `platform_urge_todo_req`;
CREATE TABLE `platform_urge_todo_req` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAR_NUM` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `CAR_COLOR` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `ALARM_SRC` int(11) DEFAULT NULL COMMENT '报警来源 1终端2企业平台3政府平台9其他',
  `ALARM_TYPE` int(11) DEFAULT NULL COMMENT '报警类型',
  `ALARM_DATE` bigint(20) DEFAULT NULL COMMENT '报警时间(秒)',
  `SUPERVISION_ID` bigint(20) DEFAULT NULL COMMENT '督办ID',
  `SUPERVISION_ENDDATE` bigint(20) DEFAULT NULL COMMENT '督办截止时间(秒)',
  `SUPERVISION_LEVEL` int(11) DEFAULT NULL COMMENT '督办级别',
  `SUPERVISOR` varchar(50) DEFAULT NULL COMMENT '督办人',
  `SUPERVISOR_TEL` varchar(50) DEFAULT NULL,
  `SUPERVISOR_EMAIL` varchar(50) DEFAULT NULL COMMENT '督办邮件',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID（修改为车辆SIM）',
  `SUPERIOR_PLATFORM_NAME` varchar(100) DEFAULT NULL COMMENT '平台名称',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报警督办请求';



DROP TABLE IF EXISTS `product_operate_record`;
CREATE TABLE `product_operate_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vin_code` varchar(60) DEFAULT NULL COMMENT '车辆唯一标识',
  `prev_process` varchar(10) DEFAULT NULL COMMENT '操作前的流程的code',
  `curr_process` varchar(10) DEFAULT NULL COMMENT '操作后的流程的code',
  `operate_user` varchar(64) DEFAULT NULL COMMENT '操作人的id',
  `operate_msg` varchar(1000) DEFAULT NULL COMMENT '用户填写的操作信息',
  `operate_pos` varchar(1000) DEFAULT NULL COMMENT '操作人员当时的位置，经纬度坐标',
  `log_name` varchar(20) DEFAULT NULL COMMENT 'log显示名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1.1.4	操作日志表：product_operate_record';



DROP TABLE IF EXISTS `product_process_info`;
CREATE TABLE `product_process_info` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `current_process` varchar(10) NOT NULL COMMENT '当前流程环节',
  `next_process` varchar(10) NOT NULL COMMENT '要流转到的流程环节',
  `process_act` varchar(50) NOT NULL COMMENT '要进行处理的动作（按钮名称：质检通过或驳回）',
  `log_name` varchar(50) DEFAULT NULL COMMENT '流程记录名',
  `expect_name` varchar(50) DEFAULT NULL,
  `reject_flag` int(11) DEFAULT '1' COMMENT '1：非驳回操作；2：驳回操作 3 过期、超时',
  `scan_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='操作流程表';



DROP TABLE IF EXISTS `report_credit_risk`;
CREATE TABLE `report_credit_risk` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CAR_NUM` varchar(100) DEFAULT NULL COMMENT '车牌号',
  `TEAM` varchar(200) DEFAULT NULL COMMENT '车组',
  `CHASSIS_NUM` varchar(100) DEFAULT NULL COMMENT '底盘号',
  `DATE` bigint(20) DEFAULT NULL COMMENT '日期',
  `BEGIN_POSITION` varchar(100) DEFAULT NULL COMMENT '开始位置',
  `END_POSITION` varchar(100) DEFAULT NULL COMMENT '结束位置',
  `HALF_MONTH_MIN` varchar(100) DEFAULT NULL COMMENT '半月最小里程',
  `HALF_MONTH_MIN_REAL` varchar(100) DEFAULT NULL COMMENT '实际半月最小里程',
  `MONTH_MIN` varchar(100) DEFAULT NULL COMMENT '最小里程',
  `MONTH_MIN_REAL` varchar(100) DEFAULT NULL COMMENT '实际里程数',
  `CAR_ID` bigint(20) DEFAULT NULL COMMENT '车辆ID',
  `HALF_OR_MONTH` int(2) NOT NULL DEFAULT '0' COMMENT '半月全月标识位：0半月，1全月',
  `REMIND_AGAIN` int(2) NOT NULL DEFAULT '0' COMMENT '不再提醒标识位：0正常，1本月内不再提醒',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷款风险';



DROP TABLE IF EXISTS `report_repair_remind`;
CREATE TABLE `report_repair_remind` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARNUM` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `TEAM` varchar(200) DEFAULT NULL COMMENT '分组',
  `CAR_OWNER` varchar(50) DEFAULT NULL COMMENT '车主',
  `PHONE` varchar(50) DEFAULT NULL COMMENT '电话',
  `CAR_TYPE` varchar(100) DEFAULT NULL COMMENT '车型',
  `REPAIR_TYPE` varchar(50) DEFAULT NULL COMMENT '保养类型',
  `REPAIR_MILEAGE` varchar(50) DEFAULT NULL COMMENT '保养里程',
  `REPAIR_DATE` bigint(20) DEFAULT NULL COMMENT '保养时间',
  `MILEAGE` varchar(50) DEFAULT NULL COMMENT '当前里程',
  `SERVICE_MANAGER` varchar(50) DEFAULT NULL COMMENT '服务经理',
  `BROKERAGE` varchar(50) DEFAULT NULL COMMENT '经手人',
  `STATION_NAME` varchar(200) DEFAULT NULL COMMENT '服务站名称',
  `FIRST_REMIND_TIME` bigint(20) DEFAULT NULL COMMENT '首次提醒时间',
  `chassisNum` varchar(100) DEFAULT NULL COMMENT '底盘号',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保养提醒报表';



DROP TABLE IF EXISTS `report_repair_timeout`;
CREATE TABLE `report_repair_timeout` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARNUM` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `TEAM` varchar(200) DEFAULT NULL COMMENT '分组',
  `SERVICE_STATION` varchar(200) DEFAULT NULL COMMENT '服务站',
  `REPAIR_TYPE` varchar(50) DEFAULT NULL COMMENT '维保类型',
  `REPAIR_CONTENT` varchar(500) DEFAULT NULL COMMENT '维保内容',
  `IN_TIME` bigint(20) DEFAULT NULL COMMENT '入站时间',
  `OUT_TIME` bigint(20) DEFAULT NULL COMMENT '出站时间',
  `STRANDED_TIME` bigint(20) DEFAULT NULL COMMENT '滞留时间',
  `SERIVCE_MAX_TIME` int(11) DEFAULT NULL COMMENT '服务最大时间阀值(单位:小时)',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维保超时报表';



DROP TABLE IF EXISTS `report_station_overload`;
CREATE TABLE `report_station_overload` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STATION_ID` bigint(20) NOT NULL COMMENT '服务站ID',
  `STATION_NAME` varchar(100) DEFAULT NULL COMMENT '服务站名称',
  `POVINCE` varchar(100) DEFAULT NULL COMMENT '省',
  `CITY` varchar(100) DEFAULT NULL COMMENT '市',
  `ADDRESS` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `LONGITUDE` bigint(20) NOT NULL COMMENT '经度',
  `LATITUDE` bigint(20) NOT NULL COMMENT '纬度',
  `SERVICE_RADIUS` int(11) NOT NULL COMMENT '服务半径',
  `OVERLOAD` int(11) DEFAULT NULL COMMENT '负荷能力（限值）',
  `AREA_COUNT` int(11) DEFAULT NULL COMMENT '实际车次',
  `ODATE` bigint(20) DEFAULT NULL COMMENT '日期',
  `REMIND_AGAIN` int(2) NOT NULL DEFAULT '0' COMMENT '不再提醒标识位：0正常，1本月内不再提醒',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='服务站超负荷信息';



DROP TABLE IF EXISTS `report_stranded_timeout`;
CREATE TABLE `report_stranded_timeout` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARNUM` varchar(50) DEFAULT NULL COMMENT '车牌号',
  `TEAM` varchar(200) DEFAULT NULL COMMENT '分组',
  `SERVICE_STATION` varchar(200) DEFAULT NULL COMMENT '服务站',
  `IN_TIME` bigint(20) DEFAULT NULL COMMENT '入站时间',
  `OUT_TIME` bigint(20) DEFAULT NULL COMMENT '出站时间',
  `REPAIR_TIME` bigint(20) DEFAULT NULL COMMENT '维保时间',
  `STRANDED_TIME` bigint(20) DEFAULT NULL COMMENT '滞留时间',
  `STRANDED_THRESHOLD` int(11) DEFAULT NULL COMMENT '滞留阀值',
  PRIMARY KEY (`ID`),
  KEY `Index_1` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='滞留超时报表';


DROP TABLE IF EXISTS `t_bridge_type`;
CREATE TABLE `t_bridge_type` (
  `code` varchar(2) NOT NULL COMMENT '驾驶室代码',
  `manufacturer_code` varchar(2) NOT NULL COMMENT '生产厂家代码',
  `name` varchar(40) NOT NULL COMMENT '驾驶室名称',
  `simple_name` varchar(40) NOT NULL COMMENT '驾驶室简称',
  PRIMARY KEY (`code`,`manufacturer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驾驶室码表';



DROP TABLE IF EXISTS `t_car_type`;
CREATE TABLE `t_car_type` (
  `code` varchar(2) NOT NULL COMMENT '车辆类型代码',
  `name` varchar(40) NOT NULL COMMENT '车辆类型名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆类型码表';



DROP TABLE IF EXISTS `t_drive_from`;
CREATE TABLE `t_drive_from` (
  `code` varchar(2) NOT NULL COMMENT '驱动类型代码',
  `name` varchar(40) NOT NULL COMMENT '驱动类型名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='驱动类型码表';



DROP TABLE IF EXISTS `t_manufacturer`;
CREATE TABLE `t_manufacturer` (
  `code` varchar(2) NOT NULL COMMENT '生产厂家代码',
  `name` varchar(40) NOT NULL COMMENT '生产厂家名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生产厂家码表';


DROP TABLE IF EXISTS `t_team_tree_full_name`;
CREATE TABLE `t_team_tree_full_name` (
  `parentId` int(11) DEFAULT NULL,
  `rootId` int(11) DEFAULT NULL,
  `nDepth` int(11) DEFAULT NULL,
  `fullname` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbtest`;
CREATE TABLE `tbtest` (
  `optime` varchar(255) DEFAULT NULL,
  `opuser` varchar(255) DEFAULT NULL,
  `appointment_id` bigint(20) DEFAULT NULL,
  `old_appointment_status` bigint(20) DEFAULT NULL,
  `new_appointment_status` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

