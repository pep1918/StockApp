package com.example.inspiredstock.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "table_costs")
public class CostModel {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cost_name") public String costName;
    @ColumnInfo(name = "cost_amount") public String costAmount;
    @ColumnInfo(name = "cost_desc") public String costDescription;

    public CostModel() {}

    public CostModel(String name, String amount, String desc) {
        this.costName = name;
        this.costAmount = amount;
        this.costDescription = desc;
    }
}