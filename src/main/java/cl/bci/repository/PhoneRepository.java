package cl.bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.bci.dao.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

}
