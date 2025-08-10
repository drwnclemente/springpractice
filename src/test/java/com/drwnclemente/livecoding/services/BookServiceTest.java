package com.drwnclemente.livecoding.services;

import com.drwnclemente.livecoding.models.Author;
import com.drwnclemente.livecoding.models.Book;
import com.drwnclemente.livecoding.repositories.AuthorRepository;
import com.drwnclemente.livecoding.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private AuthorRepository mockAuthorRepository;

    @InjectMocks
    private BookService mockBookService;

    @Test
    void addBookToAuthorAddsBookWhenAuthorExists() {

        Author mockAuthor = new Author();
        mockAuthor.setId(1L);

        Book newBook = new Book();

        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));
        when(mockBookRepository.save(newBook)).thenReturn(newBook);

        Book savedBook = mockBookService.addBookToAuthor(newBook, 1L);

        assertEquals(mockAuthor, savedBook.getAuthor());
    }

    @Test
    void addBookToAuthorThrowsExceptionWhenAuthorDoesNotExists() {
        Book newBook = new Book();
        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mockBookService.addBookToAuthor(newBook,1L));

    }

    @Test
    void findBooksByAuthorIdReturnsBooksWhenAuthorExists() {

        List<Book> bookList = List.of(new Book(),new Book());
        Author mockAuthor = new Author();
        mockAuthor.setId(1L);
        mockAuthor.setBooks(bookList);

        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));

        List<Book> result = mockBookService.findBooksByAuthorId(1L);

        assertEquals(bookList, result);
    }

    @Test
    void findBooksByAuthorIdThrowsExceptionWhenAuthorDoesNotExists() {
        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mockBookService.findBooksByAuthorId(1L));
    }

    @Test
    void calculateAvgPagesOfAuthorBooksReturnsAverageWhenBooksExist() {

        Book bookOne = new Book();
        bookOne.setPages(300);

        Book bookTwo = new Book();
        bookTwo.setPages(500);

        Author mockAuthor = new Author();
        mockAuthor.setId(1L);
        mockAuthor.setBooks(List.of(bookOne,bookTwo));

        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));

        Double result = mockBookService.calculateAvgPagesOfAuthorBooks(1L);

        assertEquals(400,result);
    }

    @Test
    void calculateAvgPagesOfAuthorBooksReturnsZeroWhenNoBooksExist() {

        Author mockAuthor = new Author();
        mockAuthor.setId(1L);
        mockAuthor.setBooks(List.of());

        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));

        Double result = mockBookService.calculateAvgPagesOfAuthorBooks(1L);

        assertEquals(0.0,result);
    }

    @Test
    void calculateAvgPagesOfAuthorBooksThrowsExceptionWhenAuthorDoesNotExist() {
        when(mockAuthorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mockBookService.calculateAvgPagesOfAuthorBooks(1L));
    }

    @Test
    void findBooksMoreExpensiveThan() {

        Double threshold = 500.0;
        List<Book> books = List.of(new Book(),new Book());

        when(mockBookRepository.findBooksMoreExpensiveThan(threshold)).thenReturn(books);

        List<Book> result = mockBookService.findBooksMoreExpensiveThan(threshold);
        assertEquals(books, result);

    }
}