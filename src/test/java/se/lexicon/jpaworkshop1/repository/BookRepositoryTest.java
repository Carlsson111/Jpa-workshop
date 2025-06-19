package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.jpaworkshop1.Entity.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private Book createBook (String isbn, String title, int maxLoanDays) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setMaxLoanDays(maxLoanDays);
        book.setAuthors(new HashSet<>());
        return bookRepository.save(book);
    }

    @Test
    void findByIsbnIgnoreCase() {
        Book book = createBook("978-0134685991", "Effective Java", 21);

        Optional<Book> result = bookRepository.findByIsbnIgnoreCase("978-0134685991");

        assertThat(result).isPresent();
        assertThat(result.get().getIsbn()).isEqualTo("978-0134685991");

    }

    @Test
    void findByTitleContains() {
        createBook("978-0134685991", "Effective Java", 21);
        createBook("978-0596009205", "Head First Java", 14);
        createBook("978-1491950357", "Learning Python", 28);

        List<Book> result = bookRepository.findByTitleContains("Java");

        assertThat(result)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Effective Java", "Head First Java");
        System.out.println(result);


    }

    @Test
    void findByMaxLoanDaysLessThan() {
        createBook("978-0134685991", "Effective Java", 21);
        createBook("978-0201633610", "Design Patterns", 7);
        createBook("978-0132350884", "Clean Code", 30);

        List<Book> result = bookRepository.findByMaxLoanDaysLessThan(15);

        assertThat(result)
                .extracting(Book::getTitle)
                .containsExactly("Design Patterns");
    }

}