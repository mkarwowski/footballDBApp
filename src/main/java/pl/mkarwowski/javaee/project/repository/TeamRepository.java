package pl.mkarwowski.javaee.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mkarwowski.javaee.project.domain.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

}
