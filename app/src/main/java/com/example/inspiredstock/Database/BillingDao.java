package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.inspiredstock.Models.BillingModel;

import java.util.List;

@Dao
public interface BillingDao {
    @Insert
    void insertBill(BillingModel bill);

    @Query("SELECT * FROM billing ORDER BY id DESC")
    List<BillingModel> getAllBills();
}