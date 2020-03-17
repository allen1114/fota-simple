package com.hunter.fota.exception;

import java.util.Map;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> clazz, Map<String, Object> attributes) {
        super(clazz.getSimpleName() + " with " + attributes.toString() + " does not exist!");
    }

}
