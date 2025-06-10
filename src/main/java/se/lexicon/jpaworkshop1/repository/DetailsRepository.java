package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.jpaworkshop1.Entity.Details;

import java.util.List;
import java.util.Optional;

public interface DetailsRepository extends CrudRepository<Details, Integer> {

    Optional<Details> findByEmail(String email);

    List<Details>findByNameContains(String name);

    List<Details> findByNameIgnoreCase(String name);
}
