package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.jpaworkshop1.Entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findByIsbnIgnoreCase(String isbn);

    List<Book> findByTitleContains(String title);

    List<Book> findByMaxLoanDaysLessThan(int maxLoanDays);
}
