package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "billing")
public class BillingModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "customer_name")
    private String customerName;

    @ColumnInfo(name = "total_amount")
    private double totalAmount;

    @ColumnInfo(name = "items_summary")
    private String itemsSummary; // Menyimpan ringkasan barang yg dibeli

    @ColumnInfo(name = "date")
    private String date;

    public BillingModel() {}

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}