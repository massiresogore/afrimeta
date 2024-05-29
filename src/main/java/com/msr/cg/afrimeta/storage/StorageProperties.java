package com.msr.cg.afrimeta.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/Users/esprit/www_java/projet_personnel_b3/afrimeta/afrimeta_server/src/main/resources/upload-dir/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
