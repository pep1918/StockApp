package com.example.inspiredstock.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "suppliers")
public class SuppliersModelClass {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "supp_name")
    private String supplierName;

    @ColumnInfo(name = "supp_contact")
    private String supplierContact;

    public SuppliersModelClass() { }

    @Ignore
    public SuppliersModelClass(String supplierName, String supplierContact) {
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierContact() { return supplierContact; }
    public void setSupplierContact(String supplierContact) { this.supplierContact = supplierContact; }
}