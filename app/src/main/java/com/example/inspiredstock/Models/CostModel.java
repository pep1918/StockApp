package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "costs")
public class CostModel {
    @PrimaryKey(autoGenerate = true)
    public int id; // Public agar bisa diakses langsung di Adapter jika mau cepat, atau buat getter/setter

    @ColumnInfo(name = "cost_name")
    public String costName;

    @ColumnInfo(name = "amount")
    public double amount;

    @ColumnInfo(name = "description")
    public String description;

    public CostModel() {}
}