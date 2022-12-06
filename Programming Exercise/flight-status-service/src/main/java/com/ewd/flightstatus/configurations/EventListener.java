package com.ewd.flightstatus.configurations;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener implements CacheEventListener<Object, Object>
{

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @SuppressWarnings("rawtypes")
    @Override
    public void onEvent(CacheEvent cacheEvent)
    {
        log.debug("Cache event = {}, Key = {},  Old value = {}, New value = {}", cacheEvent.getType(),
            cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
