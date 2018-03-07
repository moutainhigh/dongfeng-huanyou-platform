package messaging;

import java.util.Map;

public abstract interface ProxiedQuery<T extends Command.Result> extends Command<T> {
    public abstract String getQueryString();

    public abstract void setQueryString(String paramString);

    public abstract void setHeaders(Map<String, Object> paramMap);

    public abstract Map<String, Object> getHeaders();
}