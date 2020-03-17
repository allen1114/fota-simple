package com.hunter.fota.exception;

import java.util.Map;

public class EntityExistsException extends RuntimeException {

    public EntityExistsException(Class<?> clazz, Map<String, Object> attributes) {
        super(clazz.getSimpleName() + " with " + attributes.toString() + " existed!");
    }

}
