package com.msr.cg.afrimeta.storage;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}