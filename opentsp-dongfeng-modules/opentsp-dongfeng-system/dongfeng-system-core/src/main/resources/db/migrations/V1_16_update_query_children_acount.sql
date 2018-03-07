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
      FIND_IN_SET(create_account, sTempChd) > 0;
  END
  WHILE;
  RETURN sTemp;
END;