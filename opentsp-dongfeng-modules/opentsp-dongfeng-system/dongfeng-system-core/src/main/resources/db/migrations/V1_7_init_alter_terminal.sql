-- 修改终端字段为可以为空
ALTER TABLE hy_terminal
  MODIFY applianceEngineType BIGINT(20) NULL;
ALTER TABLE hy_terminal
  MODIFY applianceCarType BIGINT(20) NULL;