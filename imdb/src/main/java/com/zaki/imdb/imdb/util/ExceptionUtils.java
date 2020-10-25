package com.zaki.imdb.imdb.util;

import com.zaki.imdb.imdb.exception.*;
import com.zaki.imdb.imdb.service.EntityService;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolationException;

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

    public static void handleConstraintViolationException(RuntimeException e) throws RuntimeException {
        ValidationErrorsException ex = extractConstraintViolationException(e);
        if (ex != null) {
            throw ex;
        } else {
            throw e;
        }
    }

    public static ValidationErrorsException extractConstraintViolationException(RuntimeException e) throws RuntimeException {
        if (e instanceof ValidationErrorsException) {
            return (ValidationErrorsException) e;
        }
        Throwable ex = e;
        while (ex.getCause() != null && !(ex instanceof ConstraintViolationException)) {
            ex = ex.getCause();
        }
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex;
            return new ValidationErrorsException(cvex.getConstraintViolations());
        } else {
            return null;
        }
    }

    public static void onResourceEntryValidation(Errors errors, Long urlId, Long bodyId) {
        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
        if (!urlId.equals(bodyId)) {
            throw ExceptionUtils.newResourceEntityDataException("id", urlId, bodyId);
        }
    }
}
