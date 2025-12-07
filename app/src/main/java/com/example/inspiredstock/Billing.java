package com.example.inspiredstock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Import Adapter dan Model yang dibutuhkan
import com.example.inspiredstock.Adapters.CartAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.Models.CartItem;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.Models.ProductsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Billing extends AppCompatActivity {

    // --- Deklarasi Variabel UI ---
    private Spinner customerSpinner;
    private RecyclerView cartRecycler;
    private TextView grandTotalTxt;
    private Button addItemBtn, confirmBtn;

    // --- Deklarasi Variabel Database & List ---
    private AppDatabase db;
    private List<CustomersModelClass> customerList = new ArrayList<>();
    private List<ProductsModel> allProducts = new ArrayList<>();

    // --- Variabel Keranjang Belanja ---
    private List<CartItem> cartList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private double grandTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing); // Pastikan XML activity_billing.xml sudah benar

        // 1. Sambungkan Variabel dengan ID di XML
        customerSpinner = findViewById(R.id.customer_spinner);
        cartRecycler = findViewById(R.id.billing_cart_recycler);
        grandTotalTxt = findViewById(R.id.billing_grand_total);
        addItemBtn = findViewById(R.id.btn_add_item);
        confirmBtn = findViewById(R.id.confirm_bill_btn);

        // 2. Inisialisasi Database
        db = AppDatabase.getDbInstance(getApplicationContext());

        // 3. Setup RecyclerView untuk Keranjang
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList);
        cartRecycler.setAdapter(cartAdapter);

        // 4. Load Data Awal (Pelanggan & Produk)
        loadCustomers();
        loadProducts();

        // 5. Event Listener Tombol "Tambah Barang"
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductSelectionDialog();
            }
        });

        // 6. Event Listener Tombol "Bayar"
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCheckout();
            }
        });
    }

    // --- METHOD: LOAD DATA DARI DATABASE ---

    private void loadCustomers() {
        customerList = db.customersDao().getAllCustomers();

        List<String> names = new ArrayList<>();
        // Jika kosong, tambahkan opsi default
        if (customerList.isEmpty()) {
            names.add("Umum (Tanpa Nama)");
        } else {
            for (CustomersModelClass c : customerList) {
                names.add(c.customerName);
            }
        }

        // Pasang data ke Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        customerSpinner.setAdapter(spinnerAdapter);
    }

    private void loadProducts() {
        // Ambil semua data produk untuk ditampilkan saat user menekan "Tambah Barang"
        allProducts = db.productsDao().getAllProducts();
    }

    // --- METHOD: DIALOG & KERANJANG ---

    // Menampilkan Dialog Daftar Barang
    private void showProductSelectionDialog() {
        if (allProducts.isEmpty()) {
            Toast.makeText(this, "Stok barang kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] productNames = new String[allProducts.size()];
        for (int i = 0; i < allProducts.size(); i++) {
            ProductsModel p = allProducts.get(i);
            productNames[i] = p.productName + " (Stok: " + p.productQuantity + ")";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Barang");
        builder.setItems(productNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User memilih produk urutan ke-'which'
                showQuantityDialog(allProducts.get(which));
            }
        });
        builder.show();
    }

    // Menampilkan Dialog Input Jumlah Beli
    private void showQuantityDialog(ProductsModel selectedProduct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Beli " + selectedProduct.productName);
        builder.setMessage("Masukkan jumlah:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qtyStr = input.getText().toString();

                if (!TextUtils.isEmpty(qtyStr)) {
                    int qty = Integer.parseInt(qtyStr);
                    int currentStock = 0;

                    // Validasi konversi stok dari String ke Integer
                    try {
                        currentStock = Integer.parseInt(selectedProduct.productQuantity);
                    } catch (NumberFormatException e) {
                        currentStock = 0;
                    }

                    if (qty <= 0) {
                        Toast.makeText(Billing.this, "Jumlah minimal 1", Toast.LENGTH_SHORT).show();
                    } else if (qty > currentStock) {
                        Toast.makeText(Billing.this, "Stok tidak cukup! Sisa: " + currentStock, Toast.LENGTH_LONG).show();
                    } else {
                        // Jika valid, masukkan ke keranjang
                        addToCart(selectedProduct, qty);
                    }
                }
            }
        });
        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    // Masukkan Item ke List Keranjang
    private void addToCart(ProductsModel product, int qty) {
        CartItem item = new CartItem(product, qty);
        cartList.add(item);

        // Refresh Adapter agar item muncul di layar
        cartAdapter.notifyDataSetChanged();

        // Hitung ulang total
        calculateTotal();
    }

    // Hitung Total Belanjaan
    private void calculateTotal() {
        grandTotal = 0;
        for (CartItem item : cartList) {
            grandTotal += item.subtotal;
        }
        grandTotalTxt.setText("Rp " + String.format("%.0f", grandTotal));
    }

    // --- METHOD: PROSES PEMBAYARAN & UPDATE STOK ---

    private void processCheckout() {
        if (cartList.isEmpty()) {
            Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Ambil Nama Customer
        String customerName = "Umum";
        if (customerSpinner.getSelectedItem() != null) {
            customerName = customerSpinner.getSelectedItem().toString();
        }

        // 2. Ambil Tanggal Hari Ini
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        // 3. Buat Ringkasan Item untuk Laporan
        StringBuilder itemsSummary = new StringBuilder();
        for (CartItem item : cartList) {
            itemsSummary.append(item.product.productName)
                    .append(" (").append(item.qty).append("), ");
        }

        // 4. SIMPAN LAPORAN KE DATABASE (Tabel Billing)
        BillingModel bill = new BillingModel();
        bill.customerName = customerName;
        bill.date = date;
        bill.totalAmount = String.valueOf(grandTotal);
        bill.itemsSummary = itemsSummary.toString();

        db.billingDao().insertBill(bill); // <--- Ini yang sebelumnya error (sekarang harusnya aman)

        // 5. UPDATE STOK BARANG (Looping semua item di keranjang)
        for (CartItem cartItem : cartList) {
            ProductsModel product = cartItem.product;

            int oldQty = 0;
            try {
                oldQty = Integer.parseInt(product.productQuantity);
            } catch (Exception e) {
                oldQty = 0;
            }

            int soldQty = cartItem.qty;
            int newQty = oldQty - soldQty; // Kurangi Stok

            // Update nilai stok di objek product
            product.productQuantity = String.valueOf(newQty);

            // Simpan perubahan stok ke Database (Tabel Products)
            db.productsDao().updateProduct(product);
        }

        // 6. Selesai
        Toast.makeText(this, "Transaksi Berhasil Disimpan!", Toast.LENGTH_LONG).show();
        finish(); // Tutup halaman
    }
}