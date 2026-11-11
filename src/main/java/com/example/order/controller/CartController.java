package com.example.order.controller;

import com.example.order.model.Book;
import com.example.order.service.CatalogService;
import com.example.order.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final ShoppingCartService cartService;
    private final CatalogService catalogService;

    public CartController(ShoppingCartService cartService, CatalogService catalogService) {
        this.cartService = cartService;
        this.catalogService = catalogService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam long isbn) {

        Book book = catalogService.getBookByIsbn(isbn);

        cartService.addBook(book);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {

        model.addAttribute(
                "cart",
                cartService.getShoppingCart()
        );
        return "cart";
    }

}
