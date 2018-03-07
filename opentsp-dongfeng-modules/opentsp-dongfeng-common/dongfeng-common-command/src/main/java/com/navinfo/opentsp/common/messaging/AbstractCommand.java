package com.navinfo.opentsp.common.messaging;

public abstract class AbstractCommand<T extends Command.Result>
        implements Command<T> {
    public String version;
    public String requestUid;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestUid() {
        return this.requestUid;
    }

    public void setRequestUid(String requestUid) {
        this.requestUid = requestUid;
    }

    public static abstract interface ExtendedResult extends Result {
        public abstract int getResultCode();

        public abstract String getMessage();
    }
}