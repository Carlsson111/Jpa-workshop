package se.lexicon.jpaworkshop1.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpaworkshop1.Entity.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    List<Author> findByFirstName(String firstName);
    List<Author> findByLastName(String lastName);
    @Query("SELECT a FROM Author a WHERE a.firstName LIKE CONCAT( '%',:keyword,'%') OR a.lastName LIKE CONCAT( '%',:keyword,'%')")
    List<Author> findByFirstNameContainingOrLastNameContaining(@Param("keyword") String keyword);

    @Query("SELECT a FROM Author a JOIN a.writtenBooks b WHERE b.id = :bookId")
    List<Author> findByWrittenBooks_BookId(@Param("bookId") int bookId);



    @Query("UPDATE Author a set a.firstName = :firstName, a.lastName = :lastName WHERE a.id = :id")
    @Modifying
    @Transactional
    void updateAuthorNameById(@Param("id") int id,
                              @Param("firstName") String firstName,
                              @Param("lastName") String lastName);



    void deleteAuthorById(int authorId);
}
