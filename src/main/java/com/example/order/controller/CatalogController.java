package com.example.order.controller;

import com.example.order.model.Book;
import com.example.order.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public String showCatalog(
            @RequestParam(defaultValue = "") String query,
            Model model) {

        List<Book> books = List.of();

        if(!query.isBlank()) {
            books = catalogService.searchBooks(query);
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);

        return "catalog";
    }

}
