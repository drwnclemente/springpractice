package com.drwnclemente.livecoding.services;

import com.drwnclemente.livecoding.models.Author;
import com.drwnclemente.livecoding.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository mockAuthorRepository;

    @InjectMocks
    private AuthorService mockAuthorService;

    @Test
    void addAuthorSavesAuthorWhenNameIsUnique() {
        Author newAuthor = new Author();
        newAuthor.setName("Tolkien");

        when(mockAuthorRepository.existsByNameIgnoreCase(newAuthor.getName())).thenReturn(false);

        Author savedAuthor = mockAuthorService.addAuthor(newAuthor);

        assertEquals(newAuthor, savedAuthor);
    }

    @Test
    void addAuthorThrowsExceptionWhenNameAlreadyExists() {
        Author newAuthor = new Author();
        newAuthor.setName("Tolkien");

        when(mockAuthorRepository.existsByNameIgnoreCase(newAuthor.getName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> mockAuthorService.addAuthor(newAuthor));
    }
}