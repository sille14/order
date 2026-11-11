
package com.example.order.service;

import com.example.order.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class CatalogServiceTest {

    @Test
    void searchBooksReturnsMatchingBooks() {

        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        CatalogService service = new CatalogService(
                builder, "http://localhost:8080");

        String response = """
                [
                    {
                        "isbn": 9780132350884,
                        "title": "Clean Code",
                        "description": "A Handbook of Agile Software Craftsmanship",
                        "author": "Robert C. Martin"
                    }
                ]
                """;

        server.expect(requestTo(
                        "http://localhost:8080/books?query=Java"))
                .andRespond(withSuccess(
                        response, MediaType.APPLICATION_JSON));

        List<Book> books = service.searchBooks("Java");

        assertEquals(1, books.size());
        assertEquals(9780132350884L, books.get(0).getIsbn());
        assertEquals("Clean Code", books.get(0).getTitle());
        assertEquals("Robert C. Martin", books.get(0).getAuthor());

        server.verify();
    }

    @Test
    void getBookByIsbnReturnsCorrectBook() {

        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        CatalogService service = new CatalogService(
                builder, "http://localhost:8080");

        long isbn = 9780132350884L;

        String response = """
                {
                    "isbn": 9780132350884,
                    "title": "Clean Code",
                    "description": "A Handbook of Agile Software Craftsmanship",
                    "author": "Robert C. Martin"
                }
                """;

        server.expect(requestTo(
                        "http://localhost:8080/books/9780132350884"))
                .andRespond(withSuccess(
                        response, MediaType.APPLICATION_JSON));

        Book book = service.getBookByIsbn(isbn);

        assertNotNull(book);
        assertEquals(isbn, book.getIsbn());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());

        server.verify();
    }
}
