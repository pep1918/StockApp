package com.example.inspiredstock.Database; // Package 'D' Besar

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.inspiredstock.Models.SuppliersModelClass;

import java.util.List;

@Dao
public interface SuppliersDao {

    @Insert
    void insertSupplier(SuppliersModelClass supplier);


    @Query("SELECT * FROM suppliers ORDER BY id DESC")
    List<SuppliersModelClass> getAllSuppliers();
}