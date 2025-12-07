package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.SelectCustomersAdapter;
import com.example.inspiredstock.Database.AppDatabase;

public class SelectCustomer extends AppCompatActivity {
    RecyclerView rec;
    SelectCustomersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);

        rec = findViewById(R.id.select_customer_recycler);
        rec.setLayoutManager(new LinearLayoutManager(this));

        // Load data dan set ke adapter
        adapter = new SelectCustomersAdapter(this, AppDatabase.getDbInstance(this).customersDao().getAllCustomers());
        rec.setAdapter(adapter);
    }
}