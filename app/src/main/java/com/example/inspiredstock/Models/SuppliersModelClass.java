package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_suppliers")
public class SuppliersModelClass {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "s_name")
    public String supplierName;

    @ColumnInfo(name = "s_phone")
    public String supplierPhone;

    @ColumnInfo(name = "s_email")
    public String supplierEmail;

    @ColumnInfo(name = "s_address")
    public String supplierAddress;

    // Constructor Kosong
    public SuppliersModelClass() {
    }

    // Constructor dengan Parameter
    @Ignore
    public SuppliersModelClass(String supplierName, String supplierPhone, String supplierEmail, String supplierAddress) {
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.supplierAddress = supplierAddress;
    }
}