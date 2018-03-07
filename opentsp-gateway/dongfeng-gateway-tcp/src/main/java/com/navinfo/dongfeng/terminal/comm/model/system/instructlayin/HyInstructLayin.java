package com.navinfo.dongfeng.terminal.comm.model.system.instructlayin;

public class HyInstructLayin {
    private Long id;

    private Long terminalId;

    private Long carId;

    private Long peopleId;

    private String logAccountName;

    private String terminalNum;

    private String instructNum;

    private String instructContent;

    private String instructDate;

    private String instructState;

    private String reciveDate;

    private String instructSerialnumber;
    
    private String sessionId;
    
    private int issuccessOrfalse; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    public String getLogAccountName() {
        return logAccountName;
    }

    public void setLogAccountName(String logAccountName) {
        this.logAccountName = logAccountName == null ? null : logAccountName.trim();
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum == null ? null : terminalNum.trim();
    }

    public String getInstructNum() {
        return instructNum;
    }

    public void setInstructNum(String instructNum) {
        this.instructNum = instructNum == null ? null : instructNum.trim();
    }

    public String getInstructContent() {
        return instructContent;
    }

    public void setInstructContent(String instructContent) {
        this.instructContent = instructContent == null ? null : instructContent.trim();
    }

    public String getInstructDate() {
        return instructDate;
    }

    public void setInstructDate(String instructDate) {
        this.instructDate = instructDate == null ? null : instructDate.trim();
    }

    public String getInstructState() {
        return instructState;
    }

    public void setInstructState(String instructState) {
        this.instructState = instructState == null ? null : instructState.trim();
    }

    public String getReciveDate() {
        return reciveDate;
    }

    public void setReciveDate(String reciveDate) {
        this.reciveDate = reciveDate == null ? null : reciveDate.trim();
    }

    public String getInstructSerialnumber() {
        return instructSerialnumber;
    }

    public void setInstructSerialnumber(String instructSerialnumber) {
        this.instructSerialnumber = instructSerialnumber == null ? null : instructSerialnumber.trim();
    }
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public int getIssuccessOrfalse() {
        return issuccessOrfalse;
    }

    public void setIssuccessOrfalse(int issuccessOrfalse) {
        this.issuccessOrfalse = issuccessOrfalse;
    }
}