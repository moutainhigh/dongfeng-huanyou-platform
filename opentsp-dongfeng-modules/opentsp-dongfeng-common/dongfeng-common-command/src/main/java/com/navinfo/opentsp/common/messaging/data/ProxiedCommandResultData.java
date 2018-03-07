package com.navinfo.opentsp.common.messaging.data;

import com.navinfo.opentsp.common.messaging.ProxiedResultQuery;

import java.util.List;
import java.util.Map;

public class ProxiedCommandResultData extends CommandResultData
        implements ProxiedResultQuery {
    public Map<String, List<String>> getResultHeaders() {
        return (Map) get("resultHeaders");
    }

    public String getResultBody() {
        return (String) get("resultBody");
    }
}