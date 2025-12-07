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

    @Query("SELECT * FROM table_products ORDER BY id DESC")
    List<ProductsModel> getAllProducts();

    @Update
    void updateProduct(ProductsModel product);

    @Delete
    void deleteProduct(ProductsModel product);
}