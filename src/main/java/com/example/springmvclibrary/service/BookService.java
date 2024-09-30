package com.example.springmvclibrary.service;

import com.example.springmvclibrary.entity.Author;
import com.example.springmvclibrary.entity.Book;
import com.example.springmvclibrary.repository.AuthorRepository;
import com.example.springmvclibrary.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        if (book.getAuthor() != null) {
            Author author = book.getAuthor();

            if (author.getId() == null) {
                author = authorRepository.save(author);
            } else {
                author = authorRepository.findById(author.getId())
                        .orElseThrow(() -> new RuntimeException("Author not found"));
            }
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Book update(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
