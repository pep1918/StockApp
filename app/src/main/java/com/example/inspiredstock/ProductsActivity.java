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

import com.example.inspiredstock.Adapters.ProductsAdapter;
import com.example.inspiredstock.Database.AppDatabase; // Pastikan import ini benar (D Besar)
import com.example.inspiredstock.Models.ProductsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalItems, tvTotalStock;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private ProductsAdapter adapter;
    private List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        // 1. Init Database
        database = AppDatabase.getDbInstance(this);

        // 2. Hubungkan ID dengan XML (activity_products.xml)
        tvTotalItems = findViewById(R.id.tvTotalItems);
        tvTotalStock = findViewById(R.id.tvTotalStock);
        recyclerView = findViewById(R.id.recyclerProducts);
        fabAdd = findViewById(R.id.fabAddProduct);

        // 3. Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4. Load Data Awal
        loadData();

        // 5. Tombol Tambah (Membuka Dialog)
        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        // Ambil data dari DB
        productList = database.productsDao().getAllProducts();

        // Pasang Adapter
        adapter = new ProductsAdapter(productList, this);
        recyclerView.setAdapter(adapter);

        // --- HITUNG RINGKASAN ---
        int totalVariant = productList.size();
        double totalAset = 0;

        for (ProductsModel item : productList) {
            // Rumus: Harga * Stok
            totalAset += (item.getPrice() * item.getStock());
        }

        // Tampilkan ke TextView
        tvTotalItems.setText(String.valueOf(totalVariant));
        tvTotalStock.setText(formatRupiah(totalAset));
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Inflate Layout Dialog (dialog_product_input.xml)
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_product_input, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        // Background Transparan agar CardView terlihat rounded
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Hubungkan ID Dialog
        EditText etName = view.findViewById(R.id.etProductName);
        EditText etPrice = view.findViewById(R.id.etProductPrice);
        EditText etStock = view.findViewById(R.id.etProductStock);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Aksi Simpan
        btnSave.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty() || etPrice.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nama dan Harga wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = etName.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());
            // Jika stok kosong, isi 0
            int stock = etStock.getText().toString().isEmpty() ? 0 : Integer.parseInt(etStock.getText().toString());

            // Buat Model Baru
            ProductsModel product = new ProductsModel();
            product.setProductName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setImagePath(""); // Default kosong

            // Simpan ke DB
            database.productsDao().insertProduct(product);

            Toast.makeText(this, "Produk Disimpan", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            loadData(); // Refresh Halaman Otomatis
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // Helper Format Rupiah
    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}