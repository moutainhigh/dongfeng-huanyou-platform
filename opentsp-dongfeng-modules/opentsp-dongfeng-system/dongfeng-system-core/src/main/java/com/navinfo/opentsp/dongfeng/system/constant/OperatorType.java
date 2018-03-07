package com.navinfo.opentsp.dongfeng.system.constant;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-19
 * @modify
 * @copyright Navi Tsp
 */
public enum OperatorType {
    ADD(1, "新增"), UPDATE(2, "修改"), DELETE(3, "删除"), QUERY(4, "查询"), MODIFY_PASSWORD(5, "修改密码"),
    UNKNOWN(99, "未知类型");

    private String value;
    private int index;

    private OperatorType(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public static int getIndex(String value) {
        for (OperatorType type : OperatorType.values()) {
            if (type.value.equals(value)) {
                return type.index;
            }
        }
        return UNKNOWN.index;
    }

    public static String getValue(byte indexValue) {
        for (OperatorType type : OperatorType.values()) {
            if (type.index == indexValue) {
                return type.value;
            }
        }
        return UNKNOWN.value;
    }
    /**
     * 终端型号操作枚举
     */
    public enum TerminalModelOperateEnum {
        ADD("新建终端型号"),
        DELETE("删除终端型号"),
        QUERY("查询终端型号详情"),
        UPDATE("修改终端型号"),
        SEARCH("搜索终端型号");
        private String operateName;

        TerminalModelOperateEnum(String operateName) {
            this.operateName = operateName;
        }

        public String getOperateName() {
            return operateName;
        }
    }
    /**
     * 客户操作枚举
     */
    public enum BusinessOperateEnum {
        ADD("新建客户"),
        DELETE("删除客户"),
        QUERY("查询客户详情"),
        UPDATE("修改客户"),
        SEARCH("搜索客户");
        private String operateName;

        BusinessOperateEnum(String operateName) {
            this.operateName = operateName;
        }

        public String getOperateName() {
            return operateName;
        }
    }
}
