package tn.springmvc_mongodb.bl.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.springmvc_mongodb.bl.administration.AdministrateurService;
import tn.springmvc_mongodb.dal.administration.AdministrateurRepository;
import tn.springmvc_mongodb.dom.administration.Administrateur;

/**
 * This is a initializing bean that inserts some test data in the database. It
 * is only active in the development profile, to see the data login with root /
 * root password.
 */
@Component
public class TestDataInitializer {
    @Autowired
    private AdministrateurService administrateurService;
    @Autowired
    private AdministrateurRepository administrateurRepository;


    public void init() throws Exception {

        administrateurRepository.deleteAll();
        administrateurService.createAdministrator("Kamoun", "Ahmed", "ahmed.kamoun", "Kamoun1989");

    }
}
