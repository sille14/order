package com.example.order.model;


public class CartItem {

    private final Book book;
    private int quantity;

    public CartItem(Book book){
        this.book = book;
        this.quantity = 1;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity(){
        this.quantity++;
    }
}
