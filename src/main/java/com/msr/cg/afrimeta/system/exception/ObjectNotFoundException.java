package com.msr.cg.afrimeta.system.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String objectName, Long id ) {
        super("Nous ne retrouvons pas l'entité " + objectName + " avec id " + id);
    }
}
