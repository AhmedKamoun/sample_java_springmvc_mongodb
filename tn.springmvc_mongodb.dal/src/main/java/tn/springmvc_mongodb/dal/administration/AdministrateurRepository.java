package tn.springmvc_mongodb.dal.administration;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.springmvc_mongodb.dom.administration.Administrateur;

@Repository
public interface AdministrateurRepository extends
        MongoRepository<Administrateur, Long> {

}
