package com.example.order.service;

import com.example.order.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CatalogService {

    private final RestClient restClient;

    public CatalogService(
            RestClient.Builder builder,
            @Value("${catalog.api.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    // Search for books by query
    public List<Book> searchBooks(String query) {

        Book[] books = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/books")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .body(Book[].class);

        return books == null
                ? List.of()
                : Arrays.asList(books);
    }

    // Retrieve a specific book by ISBN
    public Book getBookByIsbn(long isbn) {

        Book book = restClient.get()
                .uri("/books/{isbn}", isbn)
                .retrieve()
                .body(Book.class);

        if (book == null || book.getIsbn() != isbn) {
            throw new NoSuchElementException(
                    "Book not found: " + isbn);
        }

        return book;
    }
}
