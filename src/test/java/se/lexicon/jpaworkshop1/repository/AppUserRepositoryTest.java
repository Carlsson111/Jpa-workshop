package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AppUserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void findByUsername() {
    }

    @Test
    void findByRegDateBetween() {
    }

    @Test
    void findByUserDetailsId() {
    }

    @Test
    void findByUserDetailsEmailIgnoreCase() {
    }
}