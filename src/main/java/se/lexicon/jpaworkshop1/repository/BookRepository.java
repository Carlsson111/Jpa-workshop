package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.jpaworkshop1.Entity.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findByIsbnIgnoreCase(String isbn);
    Book findByTitleContains(String title);
    Book findByMaxLoanDaysLessThan(int maxLoanDays);
}
