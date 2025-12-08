package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenses extends AppCompatActivity {

    EditText etCategory, etAmount, etNote;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses); // Pastikan XML ini ada

        etCategory = findViewById(R.id.cost_name_input); // ID dari activity_add_cost.xml / activity_add_expenses.xml
        etAmount = findViewById(R.id.cost_amount_input);
        etNote = findViewById(R.id.cost_desc_input);
        btnSave = findViewById(R.id.save_cost_btn);

        btnSave.setOnClickListener(v -> {
            String category = etCategory.getText().toString();
            String amountStr = etAmount.getText().toString();
            String note = etNote.getText().toString();

            // Ambil tanggal hari ini
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Nominal harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);

            // Gunakan Constructor atau Setter
            ExpensesModel expense = new ExpensesModel();
            expense.setExpenseName(category.isEmpty() ? "Pengeluaran Umum" : category);
            expense.setAmount(amount);
            expense.setDate(currentDate);
            expense.setNote(note);

            // Simpan ke DB
            AppDatabase.getDbInstance(getApplicationContext()).expensesDao().insertExpense(expense);

            Toast.makeText(this, "Pengeluaran Berhasil Disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}