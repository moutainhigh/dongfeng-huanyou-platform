--按照用户类型查找父节点
DROP FUNCTION IF EXISTS `getParentListByType`;
CREATE FUNCTION `getParentListByType`(rootId int) RETURNS varchar(1000)  
BEGIN   
DECLARE fid varchar(100) default '';
DECLARE pid varchar(100) default ''; 
DECLARE str1 varchar(1000) default ''; 
DECLARE str2 varchar(1000) default ''; 
DECLARE str3 varchar(1000) default ''; 
DECLARE done INT DEFAULT FALSE;
DECLARE cur1 CURSOR FOR (select	ha.A_PARENT_CODE from hy_action ha left join hy_roletype_action_mapping hram on	hram.action_id = ha.a_code where hram.role_type = rootId group by ha.A_PARENT_CODE); 
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=TRUE;
OPEN cur1;
read_loop: LOOP
		FETCH cur1 INTO fid;
	    IF done THEN
	       LEAVE read_loop;
	    END IF;
		SET str1 = concat(fid); 
		if	fid=1 OR fid=2 OR fid=3 OR fid=4 OR fid=5 then
				SET str1 = concat(str1,","); 
			else 
				SET str1 = concat(str1); 
		END IF;
		WHILE (fid is not null) DO        
			SET pid = (select A_PARENT_CODE from hy_action where A_CODE = fid);  
			IF pid is not null and pid > 0 THEN
				SET str2 = concat(",",pid,",");     
				SET fid = pid;
			ELSE
				SET fid = pid;
			END IF; 
		END WHILE;
		SET str3 = concat(str3,str1,str2); 
END LOOP;
CLOSE cur1;
return LEFT(str3,char_length(str3)-1);
END;

--按照用户ID查找父节点
DROP FUNCTION IF EXISTS `getParentListByAccountId`;
CREATE FUNCTION `getParentListByAccountId`(rootId varchar(100))   
RETURNS varchar(1000)    
BEGIN   
DECLARE fid varchar(100) default '';
DECLARE acode varchar(100) default '';
DECLARE pid varchar(100) default ''; 
DECLARE str1 varchar(1000) default ''; 
DECLARE str2 varchar(1000) default ''; 
DECLARE str3 varchar(1000) default ''; 
DECLARE done INT DEFAULT FALSE;
DECLARE cur1 CURSOR FOR (select t.A_CODE,t.A_PARENT_CODE from hy_account_role_mapping har, hy_role hr, hy_ra_mapping hrm, hy_action t where	har.R_CODE = hr.R_CODE and hrm.R_CODE = hr.R_CODE	and t.A_CODE = hrm.A_CODE	and har.ACCOUNT_ID = rootId); 
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=TRUE;

OPEN cur1;
read_loop: LOOP
	FETCH cur1 INTO acode,fid;
    IF done THEN
       LEAVE read_loop;
    END IF;
        SET str1 = concat(acode,",",fid);
       	IF	fid=1 OR fid=2 OR fid=3 OR fid=4 OR fid=5 then
                 SET str1 = concat(str1,","); 
			ELSE 
				 SET str1 = concat(str1); 
		END IF;
			WHILE (fid is not null) DO        
				SET pid = (select A_PARENT_CODE from hy_action where A_CODE = fid);  
				IF pid is not null and pid > 0 THEN
					SET str2 = concat(",",pid,",");     
					SET fid = pid;
				ELSE
					SET fid = pid;
				END IF; 
			END WHILE;
		SET str3 = concat(str3,str1,str2); 
END LOOP;
CLOSE cur1;
return LEFT(str3,char_length(str3)-1);
END;


DELETE FROM `hy_basicdata` WHERE DATA_TYPE=71;
INSERT INTO `hy_basicdata` (`DATA_ID`,`DATA_CODE`,`DATA_VALUE`,`DATA_TYPE`) VALUES (6157, '2', '车厂', 71),(6158, '3', '服务站', 71),(6159, '4', '运输企业', 71),(6160, '5', '经销商', 71);

DELETE FROM `hy_action`;
INSERT INTO `hy_action` (`A_CODE`,`A_NAME`,`A_DESC`,`A_PARENT_CODE`) VALUES (1,'车辆监控','车辆监控',0),(2,'统计报表','统计报表',0),(3,'风险防控','风险防控',0),(4,'系统管理','系统管理',0),(5,'终端设置','终端设置',0),(1004,'重点监控','重点监控',1),(1005,'轨迹回放','轨迹回放',1),(1008,'语音监控','语音监控',1),(1009,'短信调度','短信调度',1),(5001,'站内超时报警','站内超时报警',2),(5002,'车次统计分析','车次统计分析',2),(5003,'故障汇总报表','故障汇总报表',2),(5005,'运营车况查看','运营车况查看',2),(5006,'油量变化统计分析','油量变化统计分析',2),(5010,'出库销售报表','出库销售报表',2),(5011,'信息同步上线','信息同步上线',2),(5012,'销售状态报表','销售状态报表',2),(5013,'服务站配件预约','服务站配件预约',2),(5015,'服务站负荷分析','服务站负荷分析',2),(5016,'车辆生产线漏扫报表','车辆生产线漏扫报表',2),(5017,'车辆在库未上线报表','车辆在库未上线报表',2),(5018,'未售车辆出经销商库异常报表','未售车辆出经销商库异常报表',2),(5019,'车辆扫码报表','车辆扫码报表',2),(1012,'区域运营限定','区域运营限定',3),(1013,'防拆控制','防拆控制',3),(1014,'远程解锁车','远程解锁车',3),(4001,'角色管理','角色管理',4),(4002,'用户管理','用户管理',4),(4003,'车辆管理','车辆管理',4),(4004,'终端管理 ','终端管理 ',4),(4005,'客户管理','客户管理',4),(4007,'服务站管理','服务站管理',4),(4010,'终端型号管理','终端型号管理',4),(4011,'经销商管理','经销商管理',4),(4012,'销售区域管理','销售区域管理',4),(4013,'活动管理','活动管理',4),(1031,'远程无线升级','远程无线升级',5),(1032,'介质下发升级','介质下发升级',5),(1033,'恢复出厂设置','恢复出厂设置',5),(1034,'终端复位设置','终端复位设置',5),(1035,'终端关机操作','终端关机操作',5),(1036,'关闭数据通道','关闭数据通道',5),(1037,'关闭所有无线通信','关闭所有无线通信',5);


DELETE FROM `hy_roletype_action_mapping`;
INSERT INTO `hy_roletype_action_mapping` (`role_type`,`action_id`) VALUES (3,5005),(3,5013),(3,5015),(4,1004),(4,1005),(4,1006),(4,1009),(4,1008),(4,5003),(4,5005),(4,5006),(5,1012),(5,5005),(5,1013),(5,1014),(5,1004),(5,1005),(5,1006),(5,4003),(5,4005),(5,5010),(5,1008),(5,1009),(5,5012);
