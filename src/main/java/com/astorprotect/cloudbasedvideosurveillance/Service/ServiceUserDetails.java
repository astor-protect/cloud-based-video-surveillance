package com.astorprotect.cloudbasedvideosurveillance.Service;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class ServiceUserDetails implements UserDetailsService {
    @Autowired
    private ServiceAstorUser serviceAstorUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AstorUser user = serviceAstorUser.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        /* un user a un ensemble de roles definissant ses differents niveaux d accreditation */
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getAccountType()));
        });
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
