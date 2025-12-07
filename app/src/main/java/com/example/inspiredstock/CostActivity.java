package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.CostAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class CostActivity extends AppCompatActivity {
    RecyclerView rec;
    FloatingActionButton fab;
    CostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        rec = findViewById(R.id.recycler_cost);
        fab = findViewById(R.id.fab_add_cost);
        rec.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CostAdapter(this, new ArrayList<>());
        rec.setAdapter(adapter);

        fab.setOnClickListener(v -> startActivity(new Intent(this, AddCost.class)));
    }

    @Override protected void onResume() {
        super.onResume();
        adapter.setList(AppDatabase.getDbInstance(this).costDao().getAllCosts());
    }
}