package com.navinfo.opentsp.common.messaging.data;

import com.navinfo.opentsp.common.messaging.CommandResult;
import com.navinfo.opentsp.common.messaging.ProxiedQuery;

import java.util.Map;

public class ProxiedCommandData extends CommandData
        implements ProxiedQuery<CommandResult> {
    private String queryString;
    private String requestUid;
    private Map<String, Object> headers;

    public String getRequestUid() {
        return this.requestUid;
    }

    public void setRequestUid(String requestUid) {
        put("requestUid", requestUid);
        this.requestUid = requestUid;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
        put("queryString", queryString);
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        put("headers", headers);
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }
}
