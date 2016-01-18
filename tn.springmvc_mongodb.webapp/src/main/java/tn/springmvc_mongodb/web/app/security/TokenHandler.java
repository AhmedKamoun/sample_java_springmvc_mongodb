package tn.springmvc_mongodb.web.app.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import tn.springmvc_mongodb.bl.administration.AdministrateurService;
import tn.springmvc_mongodb.dal.administration.AdministrateurRepository;
import tn.springmvc_mongodb.dom.administration.Administrateur;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenHandler {

    private static final Logger LOGGER = Logger.getLogger(TokenHandler.class);

    @Autowired
    private AdministrateurRepository adminRepository;
    @Autowired
    private AdministrateurService administrateurService;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public User parseAdminFromToken(String token) {
        String username = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
        Administrateur admin = administrateurService.findByUsername(username);

        // TODO must be taken from database depending on authenticated user,
        // but
        // until now we don't need roles based authorization
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(admin.username, admin.getPassword(), authorities);

    }

    public String createToken(User user) {

        JwtBuilder jwtBuilder = Jwts.builder();

        // Issued-at time (Now) UTC Unix time at which this token was issued.
        DateTime now = DateTime.now().withZone(DateTimeZone.UTC);
        // expire after one day
        Date expiration = now.plusDays(1).toDate();

        jwtBuilder.setSubject(user.getUsername()).setIssuedAt(now.toDate()).setExpiration(expiration);
        jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET);
        return jwtBuilder.compact();
    }
}
