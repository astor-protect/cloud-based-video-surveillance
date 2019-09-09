package com.astorprotect.cloudbasedvideosurveillance.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
  * disable spring boot security default  login page
  * */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserDetailsService userDetailsService;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
    protected void configure(HttpSecurity security) throws Exception
    {
    // security.httpBasic().disable();

     /*  disable cross site request forgery*/
     security.csrf().disable();
     /* Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext*/
        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

     /* access rights accorder pour tout 09-09-2019*/
        security.authorizeRequests().antMatchers("/login/**","/**").permitAll();
        security.authorizeRequests().antMatchers("/").hasAnyAuthority("USER");
        security.authorizeRequests().anyRequest().authenticated();

      security.addFilter(new JWTauthentificationFilter(authenticationManager()));
      security.addFilterBefore(new JWTauthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	    /* Le systeme d'authentification base sur les services UserDetailService qui a des methodes pour l implementer ce service */
	    auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);// Hachage de mot de passe pour harmoniser avec celui de Bd
    }

}
