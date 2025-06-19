package se.lexicon.jpaworkshop1.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private LocalDate regDate;


    @OneToOne
    @JoinColumn(name= "details_id")
    private Details userDetails;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookLoan> loans = new ArrayList<>();

    public void addLoan (BookLoan loan){
        loans.add(loan);
        loan.setBorrower(this);
    }
    public void removeLoan (BookLoan loan){
        loans.remove(loan);
        loan.setBorrower(null);
    }

}

