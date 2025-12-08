package com.example.inspiredstock;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Adapters.ExpensesAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpensesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalExpenses;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private List<ExpensesModel> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        database = AppDatabase.getDbInstance(this);

        // ID sesuai activity_expenses.xml
        tvTotalExpenses = findViewById(R.id.tvTotalExpenses);
        recyclerView = findViewById(R.id.recyclerExpenses);
        fabAdd = findViewById(R.id.fabAddExpense);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        expenseList = database.expensesDao().getAllExpenses();
        ExpensesAdapter adapter = new ExpensesAdapter(expenseList, this);
        recyclerView.setAdapter(adapter);

        // Hitung Total Pengeluaran
        double totalKeluar = 0;
        for (ExpensesModel item : expenseList) {
            totalKeluar += item.getAmount();
        }
        tvTotalExpenses.setText(formatRupiah(totalKeluar));
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // ID layout: dialog_expense_input.xml
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_expense_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText etDesc = view.findViewById(R.id.etExpDesc);
        EditText etAmount = view.findViewById(R.id.etExpAmount);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            if (etAmount.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nominal wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            String desc = etDesc.getText().toString();
            if (desc.isEmpty()) desc = "Pengeluaran Lainnya";
            double amount = Double.parseDouble(etAmount.getText().toString());

            ExpensesModel expense = new ExpensesModel();
            expense.setExpenseName(desc);
            expense.setAmount(amount);
            // expense.setDate(...); // Tambahkan logika tanggal jika diperlukan

            database.expensesDao().insertExpense(expense);

            Toast.makeText(this, "Pengeluaran Dicatat", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            loadData();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}