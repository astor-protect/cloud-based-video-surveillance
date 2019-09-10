package com.astorprotect.cloudbasedvideosurveillance.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
//import sun.security.util.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


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
            String jwt = httpServletRequest.getHeader(SecurityConstant.HEADER_STRING);
            if (jwt==null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)){
                filterChain.doFilter(httpServletRequest,httpServletResponse); return;
            }


            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX,""))
                    .getBody();

            String username = claims.getSubject();
            ArrayList<Map<String,String>> roles = (ArrayList<Map<String, String>>)claims.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            roles.forEach(r->{
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            });

            UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
            /* A l acces au contexte Spring -- on renverra toujours l user qui s est authentifié*/
            SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }

    }
}
