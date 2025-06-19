package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.jpaworkshop1.Entity.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppUserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser createTestUser(String username) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword("dummyPass");
        user.setRegDate(LocalDate.of(2024, 6, 1));
        return user;
    }
    @Test
    void findByUsername() {
        AppUser user = createTestUser("johndoe");
        entityManager.persistAndFlush(user);

        Optional<AppUser> result = appUserRepository.findByUsername("johndoe");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("johndoe");
    }

    @Test
    void findByRegDateBetween() {
        AppUser userInRange = createTestUser("anna.karlsson");
        userInRange.setRegDate(LocalDate.of(2024, 3, 15));
        entityManager.persistAndFlush(userInRange);

        AppUser userOutOfRange = createTestUser("ulf.nilsson");
        userOutOfRange.setRegDate(LocalDate.of(2020, 1, 1));
        entityManager.persistAndFlush(userOutOfRange);

        List<AppUser> result = appUserRepository.findByRegDateBetween(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("anna.karlsson");
    }

    @Test
    void findByUserDetailsId() {
    }

    @Test
    void findByUserDetailsEmailIgnoreCase() {
    }
}