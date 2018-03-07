package com.navinfo.opentsp.dongfeng.common.exception;

import com.navinfo.opentsp.common.messaging.Command;

/**
 * Exception result wrapper for any not handled exception thrown while processing command
 */
public class CommandHandlingException extends RuntimeException implements Command.Result {
    public CommandHandlingException(String message) {
        super(message);
    }
}
