package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class CustomersModelClass {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cust_name")
    private String customerName;

    @ColumnInfo(name = "cust_phone")
    private String customerPhone;

    @ColumnInfo(name = "cust_address")
    private String customerAddress;

    public CustomersModelClass() {}

    // Getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public String getCustomerAddress() { return customerAddress; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
}