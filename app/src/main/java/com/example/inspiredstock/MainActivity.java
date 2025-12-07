package com.example.inspiredstock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<ProductsModel> list = new ArrayList<>();
    ProgressDialog progressDialog;
    double FindTotalStock = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Progress Dialog (Opsional, karena Room sangat cepat, ini mungkin hanya muncul sekejap)
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Memuat Data...");
        progressDialog.setCancelable(false);

        // --- NAVIGATION BUTTONS ---
        binding.goodsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            }
        });
        binding.performanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PerformanceMain.class));
            }
        });
        binding.customersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomersActivity.class));
            }
        });
        binding.suppliersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SuppliersActivity.class));
            }
        });
        binding.expensesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpensesActivity.class));
            }
        });
        binding.billingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Billing.class)); // Pastikan Activity Billing sudah ada
            }
        });
    }

    // Gunakan onResume agar data ter-refresh otomatis saat kembali dari halaman lain
    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
    }

    // Menggantikan FetchTotalAmount dari Firebase
    private void loadDashboardData() {
        progressDialog.show();

        try {
            // 1. Panggil Database Room
            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

            // 2. Ambil Semua Data Produk
            List<ProductsModel> productsList = db.productsDao().getAllProducts();

            // 3. Masukkan ke ArrayList lokal
            list.clear();
            list.addAll(productsList);

            // 4. Hitung Jumlah Item (Total Stock Count)
            FindTotalStock = list.size();
            binding.totalStock.setText(String.valueOf((int)FindTotalStock)); // Tampilkan sebagai Integer

            // 5. Hitung Total Nilai Uang (Total Balance)
            calculateTotalAmount(list);

            progressDialog.dismiss();

        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Error memuat data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Menghitung Total Harga (Quantity * Price)
    private void calculateTotalAmount(List<ProductsModel> productList) {
        double totalAmount = 0.0;

        for (ProductsModel item : productList) {
            try {
                // Konversi String ke Double karena di Model kita tipe datanya String
                // Pastikan data tidak kosong untuk menghindari crash
                String strPrice = item.productPrice != null ? item.productPrice : "0";
                String strQty = item.productQuantity != null ? item.productQuantity : "0";

                double price = Double.parseDouble(strPrice);
                double qty = Double.parseDouble(strQty);

                // Rumus: Total = Total + (Harga * Jumlah)
                totalAmount += (price * qty);

            } catch (NumberFormatException e) {
                // Abaikan item yang format angkanya salah
                e.printStackTrace();
            }
        }

        // Tampilkan hasil dengan format Rupiah (atau Rs sesuai kode lama Anda)
        binding.totalBalance.setText("Rs: " + String.format("%.2f", totalAmount));
    }
}