package com.ewd.flightstatus.configurations;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    public EntityNotFoundException(Class clazz, String... searchParamsMap)
    {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), searchParamsMap));
    }

    private static String generateMessage(String entity, String... searchParams)
    {
        return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams[0] + ", "
            + searchParams[1];
    }

}
