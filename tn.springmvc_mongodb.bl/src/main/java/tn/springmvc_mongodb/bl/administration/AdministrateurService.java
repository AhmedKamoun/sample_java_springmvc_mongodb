package tn.springmvc_mongodb.bl.administration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.springmvc_mongodb.dal.administration.AdministrateurRepository;
import tn.springmvc_mongodb.dom.administration.Administrateur;

import java.util.Date;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static tn.springmvc_mongodb.bl.utils.ValidationUtils.*;

@Service
public class AdministrateurService {
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");

    private static final Pattern EMAIL_REGEX = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    /**
     * creates a new Administrator in the database
     *
     * @param username - the username of the new user
     * @param password - the user plain text password
     */
    @Transactional
    public void createAdministrator(String nom, String prenom, String username, String password) {

        assertNotBlank(nom, "Last name cannot be empty.");
        assertNotBlank(prenom, "First name cannot be empty.");
        assertNotBlank(username, "Username cannot be empty.");
        assertMinimumLength(username, 6, "Username must have at least 6 characters.");
        assertNotBlank(password, "Password cannot be empty.");
        assertMatches(password, PASSWORD_REGEX,
                "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");
        // assertMatches(email, EMAIL_REGEX, "Invalid email.");

        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("The username is not available.");
        }

        Administrateur admin = new Administrateur();
        admin.nom = nom;
        admin.prenom = prenom;
        admin.setPassword(password);
        admin.username = username;
        admin.created_on = new Date();

        administrateurRepository.save(admin);
    }


    public Administrateur findByUsername(String username) {
        return mongoTemplate.findOne(query(where("username").is(username)), Administrateur.class);
    }
}
