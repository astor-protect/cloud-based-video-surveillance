package com.astorprotect.cloudbasedvideosurveillance.configuration;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTauthentificationFilter extends  UsernamePasswordAuthenticationFilter{
    private AuthenticationManager authenticationManager;

    public JWTauthentificationFilter(AuthenticationManager authenticationManager){
        super();
        this.authenticationManager = authenticationManager;
    }

@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        try {
            /*stockage du json dans la classe user */
            AstorUser astorUser = new ObjectMapper().readValue(request.getInputStream(),AstorUser.class);
            //new  ObjectMapper().readValue(request.getInputStream(),AstorUser.class);
            System.out.println("le user"+astorUser.getUsername());
            System.out.println("le user"+astorUser.getPassword());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(astorUser.getUsername(),astorUser.getPassword()));
        }catch (IOException e){
            throw new RuntimeException("Il y a un souci dans le contenu de la requete : "+e);
        }
    }
@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)throws IOException, ServletException{
        User springUser = (User) authResult.getPrincipal();

        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(r->{
            roles.add(r.getAuthority());
        });

        String jwt = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SecurityConstant.SECRET)
                .claim("roles",springUser.getAuthorities())
                .compact();

        System.out.println("le json webtoken : " +jwt);

        response.addHeader(SecurityConstant.HEADER_STRING,SecurityConstant.TOKEN_PREFIX + jwt);

        System.out.println(" reponse jsonwebtoken : " + response.getHeaderNames()+ " status : " +response.getStatus() + " le type : "+response.getContentType());
    }

}
