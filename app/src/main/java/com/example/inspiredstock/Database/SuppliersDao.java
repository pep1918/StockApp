package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.inspiredstock.Models.SuppliersModelClass;
import java.util.List;

@Dao
public interface SuppliersDao {
    @Insert
    void insertSupplier(SuppliersModelClass supplier);

    @Query("SELECT * FROM table_suppliers ORDER BY id DESC")
    List<SuppliersModelClass> getAllSuppliers();

    @Update
    void updateSupplier(SuppliersModelClass supplier);

    @Delete
    void deleteSupplier(SuppliersModelClass supplier);
}