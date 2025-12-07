package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_billing")
public class BillingModel {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "b_customer") public String customerName;
    @ColumnInfo(name = "b_date") public String date;
    @ColumnInfo(name = "b_total") public String totalAmount;
    @ColumnInfo(name = "b_items") public String itemsSummary; // Simpan daftar barang sebagai String panjang

    public BillingModel() {}
}