package messaging;

import java.util.List;
import java.util.Map;

public abstract interface ProxiedResultQuery {
    public abstract Map<String, List<String>> getResultHeaders();
    public abstract Object getResultBody();
}
