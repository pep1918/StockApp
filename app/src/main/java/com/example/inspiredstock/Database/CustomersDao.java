package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.inspiredstock.Models.CustomersModelClass;
import java.util.List;

@Dao
public interface CustomersDao {
    @Insert
    void insertCustomer(CustomersModelClass customer);

    @Query("SELECT * FROM table_customers ORDER BY id DESC")
    List<CustomersModelClass> getAllCustomers();

    @Delete
    void deleteCustomer(CustomersModelClass customer);
}