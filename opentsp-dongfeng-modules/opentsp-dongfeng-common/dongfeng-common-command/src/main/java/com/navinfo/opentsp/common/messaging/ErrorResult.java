package com.navinfo.opentsp.common.messaging;

import java.util.List;

public class ErrorResult {
    private List<ErrorItem> errors;

    public ErrorResult(List<ErrorItem> errors) {
        this.errors = errors;
    }

    public ErrorResult() {
    }

    public List<ErrorItem> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ErrorItem> errors) {
        this.errors = errors;
    }

    public String toString() {
        return "ErrorResult{errors=" + this.errors + "} " + super.toString();
    }
}