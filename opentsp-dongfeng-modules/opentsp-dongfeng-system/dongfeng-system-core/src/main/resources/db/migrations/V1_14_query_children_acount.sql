-- 查询子账户函数
DROP FUNCTION IF EXISTS `queryChildrenAccountInfo`;
CREATE FUNCTION `queryChildrenAccountInfo` (accountId INT) RETURNS VARCHAR (4000)
BEGIN
    DECLARE sTemp VARCHAR (4000);
    DECLARE sTempChd VARCHAR (4000);
    SET sTemp = '$';
    SET sTempChd = cast(accountId AS CHAR);
    WHILE sTempChd IS NOT NULL DO
      SET sTemp = CONCAT(sTemp, ',', sTempChd);
      SELECT
        group_concat(account_id) INTO sTempChd
      FROM
        hy_account
      WHERE
        del_flag = 0 AND FIND_IN_SET(create_account, sTempChd) > 0;
    END
    WHILE;
    RETURN sTemp;
END;

-- 修改车辆查询条件字典表数据
DELETE FROM hy_basicdata WHERE DATA_TYPE=47 AND DATA_CODE=5;
DELETE FROM hy_basicdata WHERE DATA_TYPE=47 AND DATA_CODE=6;
UPDATE hy_basicdata SET DATA_CODE=5 WHERE DATA_TYPE=47 AND DATA_CODE=7;