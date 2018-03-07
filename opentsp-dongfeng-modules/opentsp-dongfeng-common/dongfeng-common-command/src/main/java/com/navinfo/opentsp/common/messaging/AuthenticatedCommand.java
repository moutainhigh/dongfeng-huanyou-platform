package com.navinfo.opentsp.common.messaging;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.security.core.Authentication;

public abstract interface AuthenticatedCommand<T extends Command.Result> extends Command<T> {
    @JsonTypeInfo(use = Id.CLASS)
    @NotPublic
    public abstract Authentication getAuthentication();

    public abstract void setAuthentication(Authentication paramAuthentication);
}