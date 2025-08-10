package com.drwnclemente.livecoding.controllers;

import com.drwnclemente.livecoding.models.Author;
import com.drwnclemente.livecoding.models.Book;
import com.drwnclemente.livecoding.services.AuthorService;
import com.drwnclemente.livecoding.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthorController {

    private final AuthorService authorService;

    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author requestAuthor){
        Author savedAuthor =  authorService.addAuthor(requestAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, @PathVariable Long authorId){
        Book savedBook = bookService.addBookToAuthor(book, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/authors/{authorsId}/books")
    public ResponseEntity<List<Book>> getBooksOfAuthor(@PathVariable Long authorsId) {
        List<Book> authorBooks = bookService.findBooksByAuthorId(authorsId);
        return ResponseEntity.ok(authorBooks);
    }

    @GetMapping("/authors/{authorsId}/avg-pages")
    public ResponseEntity<?> getAveragePages(@PathVariable Long authorsId) {
        Double avgPages = bookService.calculateAvgPagesOfAuthorBooks(authorsId);
        return ResponseEntity.ok().body(
                Map.of("averagesPages",avgPages)
        );
    }

    @GetMapping("/books/expensive")
    public ResponseEntity<List<Book>> getExpensiveBooks(@RequestParam("threshold") Double threshold){
        List<Book> expensiveBooks = bookService.findBooksMoreExpensiveThan(threshold);
        return ResponseEntity.ok(expensiveBooks);
    }



}
