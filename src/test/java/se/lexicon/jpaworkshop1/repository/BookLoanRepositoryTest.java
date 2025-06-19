package se.lexicon.jpaworkshop1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpaworkshop1.Entity.AppUser;
import se.lexicon.jpaworkshop1.Entity.Book;
import se.lexicon.jpaworkshop1.Entity.BookLoan;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Transactional
class BookLoanRepositoryTest {
    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    private Book createTestBook(String isbn, String title, int maxLoanDays) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setMaxLoanDays(maxLoanDays);
        book.setAuthors(new HashSet<>());
        return entityManager.persist(book);
    }
    private AppUser createTestUser(String username) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword("dummyPass");
        user.setRegDate(LocalDate.of(2024, 6, 1));
        return entityManager.persist(user);
    }
    private BookLoan createTestLoan(Book book, AppUser borrower, LocalDate loanDate, LocalDate dueDate, boolean returned) {
        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setBorrower(borrower);
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);
        loan.setReturned(returned);
        return bookLoanRepository.save(loan);
    }

    @Test
    void findByBorrowerId() {
        AppUser user = createTestUser("alice");
        Book book = createTestBook("978-0134685991", "Effective Java", 21);
        BookLoan loan = createTestLoan(book, user, LocalDate.now().minusDays(2), LocalDate.now().plusDays(10), false);

        Optional<BookLoan> result = bookLoanRepository.findByBorrowerId(user.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getBook().getTitle()).isEqualTo("Effective Java");

    }

    @Test
    void findByBookId() {
        AppUser user = createTestUser("bob");
        Book book = createTestBook("978-1491950357", "Learning Python", 30);
        BookLoan loan = createTestLoan(book, user, LocalDate.now(), LocalDate.now().plusDays(15), false);

        Optional<BookLoan> result = bookLoanRepository.findByBookId(book.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getBorrower().getUsername()).isEqualTo("bob");
    }

    @Test
    void findByReturnedFalse() {
        AppUser user = createTestUser("carol");
        Book book1 = createTestBook("123-A", "Intro to AI", 7);
        Book book2 = createTestBook("123-B", "Intro to ML", 7);

        createTestLoan(book1, user, LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), false);
        createTestLoan(book2, user, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), true);

        List<BookLoan> result = bookLoanRepository.findByReturnedFalse();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBook().getTitle()).isEqualTo("Intro to AI");
    }

    @Test
    void findByDueDateAfter() {
        AppUser user = createTestUser("dave");
        Book book = createTestBook("456-C", "Databases", 15);

        createTestLoan(book, user, LocalDate.now(), LocalDate.now().plusDays(10), false);
        createTestLoan(book, user, LocalDate.now(), LocalDate.now().minusDays(2), false);

        List<BookLoan> result = bookLoanRepository.findByDueDateAfter(LocalDate.now());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDueDate()).isAfter(LocalDate.now());

    }

    @Test
    void findByLoanDateBetween() {
        AppUser user = createTestUser("eve");
        Book book = createTestBook("789-D", "Operating Systems", 14);

        createTestLoan(book, user, LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15), false);
        createTestLoan(book, user, LocalDate.of(2025, 5, 15), LocalDate.of(2025, 5, 30), false);

        List<BookLoan> result = bookLoanRepository.findByLoanDateBetween(
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 30));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLoanDate()).isEqualTo(LocalDate.of(2025, 6, 1));

    }

    @Test
    void markABookLoanAsReturnedByItsLoanID() {
        AppUser user = createTestUser("frank");
        Book book = createTestBook("999-Z", "Networks", 10);
        BookLoan loan = createTestLoan(book, user, LocalDate.now().minusDays(1), LocalDate.now().plusDays(9), false);

        int updated = bookLoanRepository.markABookLoanAsReturnedByItsLoanID(loan.getId());
        entityManager.flush();
        entityManager.clear();

        BookLoan updatedLoan = bookLoanRepository.findById(loan.getId()).orElseThrow();
        assertThat(updated).isEqualTo(1);
        assertThat(updatedLoan.getReturned()).isTrue();
    }
}