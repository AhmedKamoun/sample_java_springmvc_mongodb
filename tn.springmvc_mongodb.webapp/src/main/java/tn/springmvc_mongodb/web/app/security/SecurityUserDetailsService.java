package tn.springmvc_mongodb.web.app.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.springmvc_mongodb.bl.administration.AdministrateurService;
import tn.springmvc_mongodb.dal.administration.AdministrateurRepository;
import tn.springmvc_mongodb.dom.administration.Administrateur;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetails service that reads the user credentials from the database, using
 * a JPA repository.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private AdministrateurService administrateurService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrateur user = administrateurService.findByUsername(username);

        if (user == null) {
            String message = "Administrator not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }

        // TODO must be taken from database depending on authenticated user, but
        // until now we don't need roles based authorization
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found Administrator in database: " + user);

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
