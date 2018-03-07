package com.navinfo.opentsp.common.messaging;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.security.core.Authentication;

import java.util.Objects;


public abstract class AbstractSecuredCommand<T extends Command.Result> extends AbstractCommand<T>
        implements AuthenticatedCommand<T> {
    private Authentication authentication;

    @JsonTypeInfo(use = Id.CLASS)
    @NotPublic
    public Authentication getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String toString() {
        return "AbstractSecuredCommand{authentication=" + this.authentication + '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        AbstractSecuredCommand that = (AbstractSecuredCommand) o;
        return Objects.equals(this.authentication, that.authentication);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.authentication});
    }
}
