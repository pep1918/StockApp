package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductsModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "category")
    private String productCategory;

    @ColumnInfo(name = "stock")
    private int stock; // Menggantikan productQuantity

    @ColumnInfo(name = "price")
    private double price; // Menggantikan productPrice

    @ColumnInfo(name = "image_path")
    private String imagePath;

    public ProductsModel() { }

    // Getters
    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getProductCategory() { return productCategory; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}