package com.example.inspiredstock.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.inspiredstock.Models.ExpensesModel; // Pastikan import Model benar
import java.util.List;

@Dao
public interface ExpensesDao {
    @Insert
    void insertExpense(ExpensesModel expense);

    @Query("SELECT * FROM table_expenses ORDER BY id DESC")
    List<ExpensesModel> getAllExpenses();

    @Delete
    void deleteExpense(ExpensesModel expense);
}