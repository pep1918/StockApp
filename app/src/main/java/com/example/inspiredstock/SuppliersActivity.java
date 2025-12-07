package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.inspiredstock.Adapters.SuppliersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;
import java.util.ArrayList;
import java.util.List;

public class SuppliersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SuppliersAdapter adapter;
    private FloatingActionButton fab;
    private List<SuppliersModelClass> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        recyclerView = findViewById(R.id.recycler_suppliers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SuppliersAdapter(this, list);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab_add_supplier);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuppliersActivity.this, AddSuppliersactivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuppliers();
    }

    private void loadSuppliers() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        list = db.suppliersDao().getAllSuppliers();
        adapter.setList(list);
    }
}