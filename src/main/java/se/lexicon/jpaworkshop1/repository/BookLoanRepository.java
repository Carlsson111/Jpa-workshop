package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.lexicon.jpaworkshop1.Entity.BookLoan;

import java.time.LocalDate;
import java.util.List;

public interface BookLoanRepository extends CrudRepository<BookLoan, Integer> {
    BookLoan findByBorrowerId(int borrowerId);
    BookLoan findByBookId(int id);
    List<BookLoan> findByReturnedFalse();
    List<BookLoan> findByDueDateAfter(LocalDate dueDate);
    List<BookLoan> findByLoanDateBetween(LocalDate startDate, LocalDate endDate);
    @Query("UPDATE BookLoan b SET b.returned = TRUE WHERE b.id = :loanId")
    @Modifying
    BookLoan markABookLoanAsReturnedByItsLoanID(int loanId);
}
