package se.lexicon.jpaworkshop1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpaworkshop1.Entity.AppUser;
import se.lexicon.jpaworkshop1.Entity.Book;
import se.lexicon.jpaworkshop1.Entity.BookLoan;
import se.lexicon.jpaworkshop1.repository.BookLoanRepository;

import java.time.LocalDate;

@Service
public class BookLoanService {
    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Transactional
    public BookLoan lendBook(Book book, AppUser user) {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for loan.");
        }

        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setBorrower(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(book.getMaxLoanDays()));
        loan.setReturned(false);

        book.markAsUnavailable();
        user.addLoan(loan);

        return bookLoanRepository.save(loan);
    }

    @Transactional
    public void returnBook(int loanId) {
        BookLoan loan = bookLoanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (loan.getReturned()) {
            throw new IllegalStateException("Book is already returned.");
        }

        loan.setReturned(true);
        loan.getBook().markAsAvailable();
    }

}
