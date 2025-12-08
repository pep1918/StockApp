package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.inspiredstock.Models.ProductsModel;
import java.util.List;

@Dao
public interface ProductsDao {
    @Insert
    void insertProduct(ProductsModel product);

    @Query("SELECT * FROM products ORDER BY id DESC")
    List<ProductsModel> getAllProducts();

    @Delete
    void deleteProduct(ProductsModel product);

    @Update
    void updateProduct(ProductsModel product);
}