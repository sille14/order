
package com.example.order.controller;

import com.example.order.model.Book;
import com.example.order.model.ShoppingCart;
import com.example.order.service.CatalogService;
import com.example.order.service.ShoppingCartService;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Test
    void addToCartAddsCorrectBook() {

        // Arrange
        CatalogService catalogService = mock(CatalogService.class);
        ShoppingCartService cartService =
                mock(ShoppingCartService.class);

        CartController controller =
                new CartController(cartService, catalogService);

        long isbn = 9780132350884L;

        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle("Clean Code");

        when(catalogService.getBookByIsbn(isbn))
                .thenReturn(book);

        // Act
        String view = controller.addToCart(isbn);

        // Assert
        assertEquals("redirect:/cart", view);

        verify(catalogService).getBookByIsbn(isbn);
        verify(cartService).addBook(book);
    }

    @Test
    void showCartReturnsCartView() {

        // Arrange
        CatalogService catalogService = mock(CatalogService.class);
        ShoppingCartService cartService =
                mock(ShoppingCartService.class);

        CartController controller =
                new CartController(cartService, catalogService);

        ShoppingCart cart = new ShoppingCart();

        when(cartService.getShoppingCart())
                .thenReturn(cart);

        Model model = new ExtendedModelMap();

        // Act
        String view = controller.showCart(model);

        // Assert
        assertEquals("cart", view);
        assertSame(cart, model.asMap().get("cart"));

        verify(cartService).getShoppingCart();
    }
}
