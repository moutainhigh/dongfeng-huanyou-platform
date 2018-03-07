package com.navinfo.opentsp.common.messaging;

@Deprecated
public class HttpCommandResult extends CommandResult {
    public HttpCommandResult(Integer resultCode, String message) {
        super(resultCode, message);
    }

    public HttpCommandResult() {
    }
}
