package telephoneregister.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select p.name from Person p order by p.name")
    List<String> getAllPersonsNames();
}