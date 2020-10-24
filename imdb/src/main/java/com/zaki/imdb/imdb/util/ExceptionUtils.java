package com.zaki.imdb.imdb.util;

import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.exception.ResourceEntityDataException;
import com.zaki.imdb.imdb.service.EntityService;

public final class ExceptionUtils {
    public static NonExistingEntityException newNonExistingEntityExceptionFromService(EntityService service, String columnName, Object value) {
        value = value == null ? "" : value;
        return new NonExistingEntityException(String.format("%s with %s='%s' does not exist", service.getEntityName(), columnName, value.toString()));
    }

    public static EntityAlreadyExistsException newEntityAlreadyExitsExceptionFromService(EntityService service, String columnName, Object value) {
        value = value == null ? "" : value;
        return new EntityAlreadyExistsException(String.format("%s with %s='%s' already exist", service.getEntityName(), columnName, value.toString()));
    }

    public static InvalidEntityDataException newInvalidEntityDataExceptionFromService(EntityService service, String columnName, Object value) {
        value = value == null ? "" : value;
        return new InvalidEntityDataException(String.format("%s %s='%s' can not be modified", service.getEntityName(), columnName, value.toString()));
    }

    public static ResourceEntityDataException newResourceEntityDataException(String entityFragment, Long urlFragmentValue, Long bodyFragmentValue) {
        throw new ResourceEntityDataException(String.format("Url %s:%d differs from body entity %s:%d", entityFragment, urlFragmentValue, entityFragment, bodyFragmentValue));
    }

    public static void handleConstraintViolationException(RuntimeException e) {
    }
}
