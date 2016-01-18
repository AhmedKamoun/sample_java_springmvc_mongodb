package tn.springmvc_mongodb.dom.administration;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collection = "Administrateur")
public class Administrateur extends Personne {

    //@Column(unique = true)
    public String username;

    private String password;

    public Date created_on;

    // Retourner un password encrypté
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);

    }

}
