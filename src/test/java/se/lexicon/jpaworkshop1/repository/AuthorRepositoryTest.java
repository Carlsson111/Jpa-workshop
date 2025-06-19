package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpaworkshop1.Entity.Author;
import se.lexicon.jpaworkshop1.Entity.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Transactional
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;


    private Author createTestAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return entityManager.persist(author);
    }

    private Book createTestBook(String title, String isbn, int maxLoanDays, Set<Author> authors) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setMaxLoanDays(maxLoanDays);
        book.setAuthors(authors);

        Book saved = entityManager.persist(book);

        for (Author author : authors) {
            author.getWrittenBooks().add(saved);
        }

        return saved;
    }


    @Test
    void testFindByFirstName() {
        createTestAuthor("John", "Doe");
        createTestAuthor("John", "Smith");

        List<Author> result = authorRepository.findByFirstName("John");

        assertThat(result).hasSize(2);
    }

    @Test
    void testFindByLastName() {
        createTestAuthor("Jane", "Austen");
        createTestAuthor("Emily", "Austen");

        List<Author> result = authorRepository.findByLastName("Austen");

        assertThat(result).hasSize(2);
    }

    @Test
    void testFindByFirstNameContainingOrLastNameContaining() {
        createTestAuthor("Leo", "Tolstoy");
        createTestAuthor("Lev", "Nikolaevich");

        List<Author> result = authorRepository.findByFirstNameContainingOrLastNameContaining("Lev");

        assertThat(result).hasSize(1);
    }

    @Test
    void testFindByWrittenBooks_BookId() {
        Author author = createTestAuthor("George", "Orwell");
        Set<Author> authors = new HashSet<>();
        authors.add(author);
        Book book = createTestBook("1984", "123-XYZ", 30, authors);

        entityManager.flush();
        entityManager.clear();

        List<Author> result = authorRepository.findByWrittenBooks_BookId(book.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLastName()).isEqualTo("Orwell");
    }

    @Test
    @Transactional
    void testUpdateAuthorNameById() {
        Author author = createTestAuthor("Old", "Name");

        authorRepository.updateAuthorNameById(author.getId(), "New", "Name");
        entityManager.flush();
        entityManager.clear();

        Author updated = authorRepository.findById(author.getId()).orElseThrow();
        assertThat(updated.getFirstName()).isEqualTo("New");
        assertThat(updated.getLastName()).isEqualTo("Name");
    }

    @Test
    void testDeleteAuthorById() {
        Author author = createTestAuthor("Delete", "Me");

        authorRepository.deleteAuthorById(author.getId());
        entityManager.flush();

        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isEmpty();
    }
}