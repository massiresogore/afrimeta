package com.msr.cg.afrimeta.system.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName, String id ) {
        super("Nous ne retrouvons pas l'entité " + objectName + " not found with id " + id);
    }
}
