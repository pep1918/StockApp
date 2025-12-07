package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_expenses")
public class ExpensesModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "e_category")
    public String expenseCategory;

    @ColumnInfo(name = "e_amount")
    public String expenseAmount;

    @ColumnInfo(name = "e_date")
    public String expenseDate;

    @ColumnInfo(name = "e_note")
    public String expenseNote;

    // Constructor Kosong (Wajib untuk Room)
    public ExpensesModel() {
    }

    // Constructor dengan Parameter (Wajib pakai @Ignore agar Room tidak bingung)
    @Ignore
    public ExpensesModel(String expenseCategory, String expenseAmount, String expenseDate, String expenseNote) {
        this.expenseCategory = expenseCategory;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseNote = expenseNote;
    }
}