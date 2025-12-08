package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CardView cardProducts, cardCustomers, cardSuppliers, cardExpenses, cardBilling, cardReports;
    TextView tvTotalBalance, tvTotalStock;
    MaterialButton btnExit;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDbInstance(this);

        tvTotalBalance = findViewById(R.id.totalBalance);
        tvTotalStock = findViewById(R.id.totalStock);

        cardProducts = findViewById(R.id.goodsCard);
        cardCustomers = findViewById(R.id.customersCard);
        cardSuppliers = findViewById(R.id.suppliersCard);
        cardExpenses = findViewById(R.id.expensesCard);
        cardBilling = findViewById(R.id.billingCard);
        cardReports = findViewById(R.id.performanceCard);

        // Inisialisasi Tombol Keluar
        btnExit = findViewById(R.id.btnExitApp);

        setupClicks();
        loadDashboardStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardStats();
    }

    private void setupClicks() {
        cardProducts.setOnClickListener(v -> startActivity(new Intent(this, ProductsActivity.class)));
        cardCustomers.setOnClickListener(v -> startActivity(new Intent(this, CustomersActivity.class)));
        cardSuppliers.setOnClickListener(v -> startActivity(new Intent(this, SuppliersActivity.class)));
        cardExpenses.setOnClickListener(v -> startActivity(new Intent(this, ExpensesActivity.class)));
        cardBilling.setOnClickListener(v -> startActivity(new Intent(this, Billing.class)));
        cardReports.setOnClickListener(v -> startActivity(new Intent(this, PerformanceMain.class)));

        // --- LOGIKA TOMBOL KELUAR ---
        btnExit.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Keluar Aplikasi")
                    .setMessage("Apakah Anda yakin ingin menutup aplikasi?")
                    .setPositiveButton("Ya, Keluar", (dialog, which) -> {
                        finishAffinity(); // Menutup semua activity dan keluar
                        System.exit(0);
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    private void loadDashboardStats() {
        List<ProductsModel> products = db.productsDao().getAllProducts();
        double totalAssetValue = 0;
        int totalItemCount = 0;

        for (ProductsModel item : products) {
            totalAssetValue += (item.getPrice() * item.getStock());
            totalItemCount += item.getStock();
        }

        tvTotalBalance.setText(formatRupiah(totalAssetValue));
        tvTotalStock.setText(totalItemCount + " Unit");
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}