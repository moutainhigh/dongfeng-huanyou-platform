package com.navinfo.opentsp.common.messaging;

public class FrontEndStubCommand extends AbstractCommand<Command.Result> {
    private Integer delay;

    public Integer getDelay() {
        return this.delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Class<? extends Result> getResultType() {
        return Result.class;
    }
}