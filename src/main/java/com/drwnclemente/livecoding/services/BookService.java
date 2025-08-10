package com.drwnclemente.livecoding.services;

import com.drwnclemente.livecoding.models.Author;
import com.drwnclemente.livecoding.models.Book;
import com.drwnclemente.livecoding.repositories.AuthorRepository;
import com.drwnclemente.livecoding.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book addBookToAuthor(Book book, Long authorId){
        Author author = getAuthorOrThrow(authorId);
        book.setAuthor(author);
        bookRepository.save(book);
        return book;
    }

    public List<Book> findBooksByAuthorId(Long authorId){
        return getAuthorOrThrow(authorId).getBooks();
    }

    public Double calculateAvgPagesOfAuthorBooks(Long authorId){
        Author author = getAuthorOrThrow(authorId);
        return author.getBooks().stream()
                .mapToDouble(Book::getPages)
                .average()
                .orElse(0.0);
    }

    public List<Book> findBooksMoreExpensiveThan(Double threshold){
        return bookRepository.findBooksMoreExpensiveThan(threshold);
    }

    private Author getAuthorOrThrow(Long authorId){
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author Id does not exist"));
    }
}
