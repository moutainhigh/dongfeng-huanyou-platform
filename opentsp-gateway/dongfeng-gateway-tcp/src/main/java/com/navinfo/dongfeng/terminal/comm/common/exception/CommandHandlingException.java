package com.navinfo.dongfeng.terminal.comm.common.exception;


import messaging.Command;

/**
 * Exception result wrapper for any not handled exception thrown while processing command
 */
public class CommandHandlingException extends RuntimeException implements Command.Result {
    public CommandHandlingException(String message) {
        super(message);
    }
}
