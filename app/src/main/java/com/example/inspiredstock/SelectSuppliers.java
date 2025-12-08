package com.example.inspiredstock;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.SelectSuppliersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;
import java.util.List;

public class SelectSuppliers extends AppCompatActivity {

    RecyclerView recyclerView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_suppliers);

        db = AppDatabase.getDbInstance(this);
        recyclerView = findViewById(R.id.recycler_select_supplier);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        List<SuppliersModelClass> list = db.suppliersDao().getAllSuppliers();
        SelectSuppliersAdapter adapter = new SelectSuppliersAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}