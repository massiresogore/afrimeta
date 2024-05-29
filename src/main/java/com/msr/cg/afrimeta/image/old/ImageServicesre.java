package com.msr.cg.afrimeta.image.old;

import com.msr.cg.afrimeta.image.Image;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

public class ImageServicesre {
    private final JdbcClient jdbcClient;

    public ImageServicesre(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Image> getAllImage() {
        return jdbcClient.sql("select  * from image ")
                .query(Image.class)
                .list();
    }

    public Image getImage(String name) {
        return jdbcClient.sql("select * rom image where file_path=:nameValue")
                .param("nameValue",name)
                .query(Image.class)
                .single();
    }


}
