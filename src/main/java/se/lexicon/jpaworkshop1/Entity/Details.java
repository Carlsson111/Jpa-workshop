package se.lexicon.jpaworkshop1.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Data
/*
the use of Entity makes so Pojo´s registers to our db in a plain and simple way with the help of our info provided
 */
public class Details {


    /*
     @Id defines our primary key, we use @GeneratedValue as a Strategy to increase the value of id auto, or with different,
     contexts like, identity, uuid, sequence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*
     @Column is just a column in the table, where we can define rules for the column.
     */
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;


}


