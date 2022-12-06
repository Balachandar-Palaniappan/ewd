package com.ewd.flightstatus.configurations;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CacheLoaderException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    public CacheLoaderException(Class clazz, String cacheName)
    {
        super(CacheLoaderException.generateMessage(clazz.getSimpleName(), cacheName));
    }

    private static String generateMessage(String entity, String cacheName)
    {
        return StringUtils.capitalize(entity) + " not able to load the cache " + cacheName;
    }

}
