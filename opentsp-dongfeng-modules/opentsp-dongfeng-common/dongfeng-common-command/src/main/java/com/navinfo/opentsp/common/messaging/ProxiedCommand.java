package com.navinfo.opentsp.common.messaging;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Cacheable
@CommandType("proxied")
public abstract class ProxiedCommand<T extends Command.Result> extends AbstractSecuredCommand<T>
        implements ProxiedQuery<T> {
    private String queryString;
    private Map<String, Object> headers;

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        if (!super.equals(o)) return false;
        ProxiedCommand that = (ProxiedCommand) o;
        return Objects.equals(this.queryString, that.queryString);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.queryString});
    }

    public String toString() {
        return "ProxiedCommand{queryString='" + this.queryString + '\'' + "} " + super.toString();
    }

    public static abstract class ProxiedResult extends CommandResult
            implements ProxiedResultQuery {
        private String resultBody;
        private Map<String, List<String>> resultHeaders;

        public Map<String, List<String>> getResultHeaders() {
            return this.resultHeaders;
        }

        public void setResultHeaders(Map<String, List<String>> resultHeaders) {
            this.resultHeaders = resultHeaders;
        }

        public String getResultBody() {
            return this.resultBody;
        }

        public void setResultBody(String resultBody) {
            this.resultBody = resultBody;
        }
    }
}
