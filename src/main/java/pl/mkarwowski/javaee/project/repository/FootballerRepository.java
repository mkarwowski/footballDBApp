package pl.mkarwowski.javaee.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.Sponsor;

import java.util.List;

@Repository
public interface FootballerRepository extends CrudRepository<Footballer, Long> {
    List<Footballer> findByLastName(String lastName);

    @Query ("Select f from Footballer f order by yob")
    List<Footballer> getFootballersOrderedByYob();

    List<Footballer> findBySponsor(Sponsor sponsor);
    //List<Footballer> findByFirstNameAndLastName(String firstName, String LastName);
    //List<Footballer> findByFirstNameOrLastName(String firstName, String LastName);


}
