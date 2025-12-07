package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.inspiredstock.Adapters.CustomersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;
import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomersAdapter adapter;
    private FloatingActionButton fab;
    private List<CustomersModelClass> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        recyclerView = findViewById(R.id.customers_recycler_view); // Cek ID di XML activity_customers.xml
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomersAdapter(this, list);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab_add_customer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomersActivity.this, AddCustomersActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCustomers();
    }

    private void loadCustomers() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        list = db.customersDao().getAllCustomers();
        adapter.setList(list);
    }
}