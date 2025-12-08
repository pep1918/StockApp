package com.example.inspiredstock.Models;

public class CartItem {
    private ProductsModel product;
    private int quantity;

    public CartItem(ProductsModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter Setter
    public ProductsModel getProduct() { return product; }
    public void setProduct(ProductsModel product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}