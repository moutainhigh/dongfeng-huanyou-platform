package messaging;

import org.springframework.cache.annotation.Cacheable;

@Cacheable
public abstract interface CacheAware {
    public abstract boolean isCacheable();
}
