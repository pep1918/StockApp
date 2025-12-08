package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.inspiredstock.Models.CostModel;

import java.util.List;

@Dao
public interface CostDao {
    @Insert
    void insertCost(CostModel cost);

    @Query("SELECT * FROM costs ORDER BY id DESC")
    List<CostModel> getAllCosts();
}