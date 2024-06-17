package com.msr.cg.afrimeta.security;


import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication){

        //ceci affichera le nom d'utilisatteur
        LOGGER.debug("Autheticated user: $'{}'", authentication.getName());


        return new Result(true,
                StatusCode.SUCCESS,
                "user info and json web token",
                this.authService.createLoginInfo(authentication)
                //le controller va sérealiser le Map en Json String
        );

    }
}

