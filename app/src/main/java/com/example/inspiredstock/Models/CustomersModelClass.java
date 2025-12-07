package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_customers")
public class CustomersModelClass {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "c_name") public String customerName;
    @ColumnInfo(name = "c_phone") public String customerPhone;
    @ColumnInfo(name = "c_address") public String customerAddress;
    @ColumnInfo(name = "c_email") public String customerEmail;

    public CustomersModelClass() {}
}