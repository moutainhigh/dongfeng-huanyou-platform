package com.navinfo.opentsp.common.messaging;

public abstract interface Command<T extends Command.Result> {
    public abstract Class<? extends T> getResultType();

    public abstract String getRequestUid();

    public abstract void setRequestUid(String paramString);

    public static abstract interface Result {
    }
}