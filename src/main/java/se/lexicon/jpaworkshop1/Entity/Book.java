package se.lexicon.jpaworkshop1.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String title;
    private int maxLoanDays;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    public void addAuthor(Author author) {
        authors.add(author);
        author.getWrittenBooks().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getWrittenBooks().remove(this);
    }
}
