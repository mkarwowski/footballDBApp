package pl.mkarwowski.javaee.project.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mkarwowski.javaee.project.domain.Sponsor;

@Repository
public interface SponsorRepository extends CrudRepository<Sponsor, Long> {
}
