package com.navinfo.opentsp.common.messaging.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.navinfo.opentsp.common.messaging.*;
import com.navinfo.opentsp.common.messaging.routing.MessageGroupInfo;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.*;


public class CommandData extends HashMap<String, Object>
        implements RoutableCommand, CacheAware, TypeDescriptor, AuthenticatedCommand<CommandResult>, Serializable {
    private static final String AUTHENTICATION = "authentication";
    private static final String REQUESTUID = "requestUid";
    private static final List<String> IGNORED_FIELDS = Arrays.asList(new String[]{"authentication", "requestUid"});

    @JsonTypeId
    private String typeInfo;

    @JsonTypeId
    private boolean cacheable;
    private String requestUid;
    private String originalURL;

    @JsonIgnore
    private MessageGroupInfo messageGroupInfo;

    public Authentication getAuthentication() {
        return (Authentication) get("authentication");
    }

    public void setAuthentication(Authentication authentication) {
        put("authentication", authentication);
    }

    public String getTypeInfo() {
        return this.typeInfo;
    }

    public MessageGroupInfo getRoute() {
        return this.messageGroupInfo;
    }

    public void setTypeInfo(String typeInfo) {
        put(JsonTypeInfo.Id.CLASS.getDefaultPropertyName(), typeInfo);
        this.typeInfo = typeInfo;
    }

    public String getRequestUid() {
        return this.requestUid;
    }

    public void setRequestUid(String requestUid) {
        put("requestUid", requestUid);
        this.requestUid = requestUid;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public boolean isCacheable() {
        return this.cacheable;
    }

    public MessageGroupInfo getMessageGroupInfo() {
        return this.messageGroupInfo;
    }

    public void setMessageGroupInfo(MessageGroupInfo messageGroupInfo) {
        this.messageGroupInfo = messageGroupInfo;
    }

    public Class<? extends CommandResult> getResultType() {
        return CommandResult.class;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        CommandData that = (CommandData) o;
        if (!Objects.equals(this.typeInfo, that.typeInfo)) return false;
        return compareMaps(that);
    }

    private boolean compareMaps(CommandData m) {
        if (m.size() != size()) {
            return false;
        }
        Iterator i = entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry) i.next();
            if (!IGNORED_FIELDS.contains(e.getKey())) {
                String key = (String) e.getKey();
                Object value = e.getValue();
                if (value == null) {
                    if ((m.get(key) != null) || (!m.containsKey(key)))
                        return false;
                } else if (!value.equals(m.get(key))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.typeInfo, Integer.valueOf(generateHashCode())});
    }

    private int generateHashCode() {
        int h = 0;
        Iterator i = entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry) i.next();
            if (!IGNORED_FIELDS.contains(e.getKey())) {
                h += e.hashCode();
            }
        }
        return h;
    }

    public String getOriginalURL() {
        return this.originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
        put("originalURL", originalURL);
    }

    public String toString() {
        return "CommandData{typeInfo='" + this.typeInfo + '\'' + ", originalURL='" + this.originalURL + '\'' + ", messageGroupInfo=" + this.messageGroupInfo + "} " + super.toString();
    }
}