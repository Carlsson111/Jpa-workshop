package se.lexicon.jpaworkshop1.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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

}

