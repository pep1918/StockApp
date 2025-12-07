package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_products")
public class ProductsModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "p_id")
    public String productId;

    @ColumnInfo(name = "p_name")
    public String productName;

    @ColumnInfo(name = "p_category")
    public String productCategory;

    @ColumnInfo(name = "p_qty")
    public String productQuantity;

    @ColumnInfo(name = "p_price")
    public String productPrice;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    // Constructor Kosong
    public ProductsModel() {
    }

    // Constructor dengan Parameter
    @Ignore
    public ProductsModel(String productId, String productName, String productCategory, String productQuantity, String productPrice, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.imagePath = imagePath;
    }
}