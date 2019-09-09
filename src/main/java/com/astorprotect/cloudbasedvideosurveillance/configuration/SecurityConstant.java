package com.astorprotect.cloudbasedvideosurveillance.configuration;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConstant {
    public static final String  SECRET = "jokayam95@gmail.com";
    public static final long EXPIRATION_TIME = 864_000_000; //10  jours
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

}
