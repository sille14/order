
package com.example.order.controller;

import com.example.order.model.Book;
import com.example.order.service.CatalogService;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogControllerTest {

    @Test
    void searchBooksReturnsResults() {

        // Arrange
        CatalogService catalogService = mock(CatalogService.class);
        CatalogController controller =
                new CatalogController(catalogService);

        Book book = new Book();
        book.setIsbn(9780132350884L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setDescription("A programming book");

        when(catalogService.searchBooks("Java"))
                .thenReturn(List.of(book));

        Model model = new ExtendedModelMap();

        // Act
        String view = controller.showCatalog("Java", model);

        // Assert
        assertEquals("catalog", view);
        assertEquals("Java", model.asMap().get("query"));

        @SuppressWarnings("unchecked")
        List<Book> books =
                (List<Book>) model.asMap().get("books");

        assertEquals(1, books.size());
        assertEquals(9780132350884L, books.get(0).getIsbn());
        assertEquals("Clean Code", books.get(0).getTitle());

        verify(catalogService).searchBooks("Java");
    }

    @Test
    void emptyQueryReturnsEmptyList() {

        // Arrange
        CatalogService catalogService = mock(CatalogService.class);
        CatalogController controller =
                new CatalogController(catalogService);

        Model model = new ExtendedModelMap();

        // Act
        String view = controller.showCatalog("", model);

        // Assert
        assertEquals("catalog", view);
        assertEquals(List.of(), model.asMap().get("books"));

        verifyNoInteractions(catalogService);
    }
}
