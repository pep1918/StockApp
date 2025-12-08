package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.Models.ExpensesModel;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PerformanceMain extends AppCompatActivity {

    TextView tvTotalIncome, tvTotalExpense, tvNetProfit;
    Button btnViewDetails;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_main);

        db = AppDatabase.getDbInstance(this);


        tvTotalIncome = findViewById(R.id.tvTotalIncome); // Buat ID ini di XML
        tvTotalExpense = findViewById(R.id.tvTotalExpenses);
        tvNetProfit = findViewById(R.id.tvNetProfit); // Buat ID ini di XML
        btnViewDetails = findViewById(R.id.btnViewDetails); // Tombol Lihat Detail Transaksi

        calculatePerformance();


        if (btnViewDetails != null) {
            btnViewDetails.setOnClickListener(v ->
                    startActivity(new Intent(this, ReportsDetails.class))
            );
        }
    }

    private void calculatePerformance() {
        // 1. Hitung Pemasukan (Dari Billing)
        List<BillingModel> bills = db.billingDao().getAllBills();
        double totalIncome = 0;
        for (BillingModel bill : bills) {
            totalIncome += bill.getTotalAmount();
        }

        // 2. Hitung Pengeluaran (Dari Expenses)
        List<ExpensesModel> expenses = db.expensesDao().getAllExpenses();
        double totalExpense = 0;
        for (ExpensesModel ex : expenses) {
            totalExpense += ex.getAmount();
        }

        // 3. Hitung Laba Bersih
        double profit = totalIncome - totalExpense;

        // Tampilkan
        if (tvTotalIncome != null) tvTotalIncome.setText(formatRupiah(totalIncome));
        if (tvTotalExpense != null) tvTotalExpense.setText(formatRupiah(totalExpense));
        if (tvNetProfit != null) tvNetProfit.setText(formatRupiah(profit));
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}