-- 修改终端日志表经销商名称和所属客户字段的字段长度为200
ALTER TABLE hy_terminal_log MODIFY COMPANY_NAME VARCHAR(200);
ALTER TABLE hy_terminal_log MODIFY CAR_OWNER VARCHAR(200);
-- 添加语音监控字典数据
insert hy_basicdata (data_code,data_value,data_type) values('2152','语音监控',52);
-- 删除数据字典
DELETE FROM hy_basicdata
WHERE DATA_CODE = "2253" AND DATA_TYPE = 52;
DELETE FROM hy_basicdata
WHERE DATA_CODE = "2270" AND DATA_TYPE = 52;
DELETE FROM hy_basicdata
WHERE DATA_CODE = "22731" AND DATA_TYPE = 52;
DELETE FROM hy_basicdata
WHERE DATA_CODE = "22732" AND DATA_TYPE = 52;
DELETE FROM hy_basicdata
WHERE DATA_CODE = "2450" AND DATA_TYPE = 52;

-- 删除重复的数据字典
DELETE FROM hy_basicdata
WHERE DATA_id BETWEEN 5843 AND 5850 AND data_code = "8103" AND DATA_TYPE = 52;