package com.astorprotect.cloudbasedvideosurveillance.configuration;

import org.springframework.web.filter.OncePerRequestFilter;
//import sun.security.util.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTauthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With,"+
                "Content-Type, Access-Control-Request-Method,"+
                "Access-Control-RequestHeaders, Authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials,Authorization");
        if (httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else {
           // String jwt =// httpServletRequest.getHeader(SecurityConstant.HEADER_STRING);
            //if (jwt==null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)){
                filterChain.doFilter(httpServletRequest,httpServletResponse); return;
            }
        }

    }
//}
