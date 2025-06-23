package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpaworkshop1.Entity.BookLoan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookLoanRepository extends CrudRepository<BookLoan, Integer> {
    Optional<BookLoan> findByBorrowerId(int borrowerId);
    Optional<BookLoan> findByBookId(int id);
    List<BookLoan> findByReturnedFalse();
    List<BookLoan> findByDueDateAfter(LocalDate dueDate);
    List<BookLoan> findByLoanDateBetween(LocalDate startDate, LocalDate endDate);
    @Modifying
    @Transactional
    @Query("UPDATE BookLoan b SET b.returned = TRUE WHERE b.id = :loanId")
    int markABookLoanAsReturnedByItsLoanID(@Param("loanId") int loanId);
}
