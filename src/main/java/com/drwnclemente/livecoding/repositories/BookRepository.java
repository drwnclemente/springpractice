package com.drwnclemente.livecoding.repositories;

import com.drwnclemente.livecoding.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b WHERE b.price > :threshold")
    List<Book> findBooksMoreExpensiveThan(@Param("threshold") Double threshold);

}
