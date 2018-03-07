insert into hy_basicdata (data_code , data_value , data_type) value (9101 , '平台录入' , 91);
insert into hy_basicdata (data_code , data_value , data_type) value (9102 , 'MES' , 91);

DELETE FROM `hy_basicdata` WHERE DATA_TYPE=71;
INSERT INTO `hy_basicdata` (`DATA_ID`,`DATA_CODE`,`DATA_VALUE`,`DATA_TYPE`) VALUES (6157, '2', '车厂', 71),(6158, '3', '服务站', 71),(6159, '4', '运输企业', 71),(6160, '5', '经销商', 71);