package com.example.inspiredstock;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.SelectSuppliersAdapter;
import com.example.inspiredstock.Database.AppDatabase;

public class SelectSuppliers extends AppCompatActivity {
    RecyclerView rec;
    SelectSuppliersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_suppliers);
        rec = findViewById(R.id.select_suppliers_recycler);
        rec.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectSuppliersAdapter(this, AppDatabase.getDbInstance(this).suppliersDao().getAllSuppliers());
        rec.setAdapter(adapter);
    }
}