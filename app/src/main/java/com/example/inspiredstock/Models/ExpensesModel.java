package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class ExpensesModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "exp_name")
    private String expenseName;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "note")
    private String note;

    public ExpensesModel() { }

    @Ignore
    public ExpensesModel(String expenseName, double amount, String date, String note) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    // Getter Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getExpenseName() { return expenseName; }
    public void setExpenseName(String expenseName) { this.expenseName = expenseName; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}