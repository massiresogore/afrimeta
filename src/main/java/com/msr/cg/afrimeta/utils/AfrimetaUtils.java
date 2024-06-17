package com.msr.cg.afrimeta.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AfrimetaUtils {


    public static DateTimeFormatter EuropeanDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }



}
