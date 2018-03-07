package com.navinfo.opentsp.common.messaging;

import java.util.List;

public class CommandResult
        implements AbstractCommand.ExtendedResult {
    private int resultCode;
    private String message;
    private ErrorResult errorResult;

    public CommandResult() {
    }

    public CommandResult(Integer resultCode, String message) {
        this.resultCode = resultCode.intValue();
        this.message = message;
    }

    public CommandResult(int code, String message, List<ErrorItem> errors) {
        this(Integer.valueOf(code), message);
        setErrors(errors);
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public <T extends CommandResult> T fillResult(ResultCode result) {
        setResultCode(result.code());
        if (this.message == null) {
            setMessage(result.result());
        }
        return (T) this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResult getErrorResult() {
        return this.errorResult;
    }

    public void setErrorResult(ErrorResult errorResult) {
        this.errorResult = errorResult;
    }

    public String toString() {
        return "HttpCommandResult{resultCode=" + this.resultCode + ", message='" + this.message + '\'' + '}';
    }

    public void setErrors(List<ErrorItem> errors) {
        this.errorResult = new ErrorResult(errors);
    }
}
