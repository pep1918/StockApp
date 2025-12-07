package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.inspiredstock.Adapters.ExpensesAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel; // <--- SUDAH DIPERBAIKI

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpensesAdapter adapter;
    private FloatingActionButton fab;
    private TextView totalExpenseTxt;
    private List<ExpensesModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        recyclerView = findViewById(R.id.recycler_expenses);
        totalExpenseTxt = findViewById(R.id.total_expenses_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ExpensesAdapter(this, list);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpensesActivity.this, AddExpenses.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }

    private void loadExpenses() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        list = db.expensesDao().getAllExpenses();
        adapter.setList(list);
        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        for (ExpensesModel item : list) {
            try {
                total += Double.parseDouble(item.expenseAmount);
            } catch (Exception e) {}
        }
        if(totalExpenseTxt != null) {
            totalExpenseTxt.setText("Total: Rp " + String.format("%.0f", total));
        }
    }
}