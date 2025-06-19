package se.lexicon.jpaworkshop1.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    Set<Book> writtenBooks = new HashSet<>();

    public void addBook(Book book) {
        writtenBooks.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book) {
        writtenBooks.remove(book);
        book.getAuthors().remove(this);
    }

}
