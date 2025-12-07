package com.example.inspiredstock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel; // <--- SUDAH DIPERBAIKI

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenses extends AppCompatActivity {

    private EditText inputCategory, inputAmount, inputNote;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        inputCategory = findViewById(R.id.expense_category_input);
        inputAmount = findViewById(R.id.expense_amount_input);
        inputNote = findViewById(R.id.expense_note_input);
        saveBtn = findViewById(R.id.save_expense_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String category = inputCategory.getText().toString();
        String amount = inputAmount.getText().toString();
        String note = inputNote.getText().toString();

        if (TextUtils.isEmpty(category) || TextUtils.isEmpty(amount)) {
            Toast.makeText(this, "Kategori dan Jumlah wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil Tanggal Hari Ini Otomatis
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        ExpensesModel expense = new ExpensesModel(category, amount, currentDate, note);

        db.expensesDao().insertExpense(expense);
        Toast.makeText(this, "Pengeluaran Disimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}