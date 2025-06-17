package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.jpaworkshop1.Entity.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findByRegDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<AppUser> findByUserDetailsId (int detailsId);

    Optional<AppUser> findByUserDetailsEmailIgnoreCase(String email);
}
