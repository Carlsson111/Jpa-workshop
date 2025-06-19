package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.jpaworkshop1.Entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class DetailsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DetailsRepository detailsRepository;

    private Details anders,ivar,linus;

    @BeforeEach
    void setUp() {
        anders = createDetails("Ander Andersson","Test@test.nu", LocalDate.of(1990,5,1));
        ivar = createDetails("Ivar","Test@test.se", LocalDate.of(1990,7,1));
        linus = createDetails("Linus","Test@test.com", LocalDate.of(1998,8,1));

    }

    private Details createDetails(String name, String email, LocalDate birthDate) {
        Details details = new Details();
        details.setName(name);
        details.setEmail(email);
        details.setBirthDate(birthDate);
        return entityManager.persistAndFlush(details);
    }


    @Test
    void findByEmail() {
        Optional<Details> result = detailsRepository.findByEmail("Test@test.nu");
        assertTrue(result.isPresent());
        assertEquals(anders.getEmail(), result.get().getEmail());
        System.out.println(anders.getEmail());
    }

    @Test
    void findByNameContains() {
        List<Details> result = detailsRepository.findByNameContains("Iv");
        assertEquals(1, result.size());
        System.out.println(result.get(0).getName());
    }

    @Test
    void findByNameIgnoreCase() {
        List<Details> result = detailsRepository.findByNameIgnoreCase("AnDeR AnDeRsSoN");
        assertEquals(1,result.size());
        System.out.println(result.get(0).getName());
    }
}