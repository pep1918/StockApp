package com.example.inspiredstock.Models;

public class CartItem {
    public ProductsModel product; // Barang aslinya
    public int qty; // Jumlah yang dibeli
    public double subtotal; // Harga x Qty

    public CartItem(ProductsModel product, int qty) {
        this.product = product;
        this.qty = qty;
        try {
            double price = Double.parseDouble(product.productPrice);
            this.subtotal = price * qty;
        } catch (Exception e) {
            this.subtotal = 0;
        }
    }
}