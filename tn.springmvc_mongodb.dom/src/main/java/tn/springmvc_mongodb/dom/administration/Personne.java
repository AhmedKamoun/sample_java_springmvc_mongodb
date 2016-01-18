package tn.springmvc_mongodb.dom.administration;

import tn.springmvc_mongodb.dom.base.BaseEntity;

import java.util.Date;

public class Personne extends BaseEntity {

    //@Column(nullable = false)
    public String nom;
    //@Column(nullable = false)
    public String prenom;
    //@Column(unique = true, nullable = true)
    public String cin;
    public Date birthday;
    public Boolean isMale;

}
