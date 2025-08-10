package com.drwnclemente.livecoding.services;

import com.drwnclemente.livecoding.models.Author;
import com.drwnclemente.livecoding.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author){

        boolean authorAlreadyExists = authorRepository.existsByNameIgnoreCase(author.getName());

        if (authorAlreadyExists){
            throw new IllegalArgumentException("Author Already exists");
        }

        authorRepository.save(author);
        return author;
    }

}
