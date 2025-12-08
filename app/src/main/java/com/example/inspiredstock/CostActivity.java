package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.CostAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CostModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class CostActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CostAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        recyclerView = findViewById(R.id.recycler_view_cost);
        fab = findViewById(R.id.fab_add_cost);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();

        fab.setOnClickListener(v -> startActivity(new Intent(CostActivity.this, AddCost.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        List<CostModel> list = AppDatabase.getDbInstance(this).costDao().getAllCosts();
        adapter = new CostAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}