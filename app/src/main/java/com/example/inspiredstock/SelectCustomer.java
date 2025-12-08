package com.example.inspiredstock;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.SelectCustomersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;
import java.util.List;

public class SelectCustomer extends AppCompatActivity {

    RecyclerView recyclerView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);

        db = AppDatabase.getDbInstance(this);
        recyclerView = findViewById(R.id.recycler_select_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        List<CustomersModelClass> list = db.customersDao().getAllCustomers();
        SelectCustomersAdapter adapter = new SelectCustomersAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}