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

import com.example.inspiredstock.Adapters.CartAdapter; // Menggunakan CartAdapter
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

    // Komponen UI
    private Spinner customerSpinner;
    private RecyclerView cartRecycler;
    private TextView grandTotalTxt;
    private Button addItemBtn, confirmBtn;

    // Database & Data List
    private AppDatabase db;
    private List<CustomersModelClass> customerList = new ArrayList<>();
    private List<ProductsModel> allProducts = new ArrayList<>();

    // Keranjang Belanja
    private List<CartItem> cartList = new ArrayList<>();
    private CartAdapter cartAdapter; // Variabel adapter
    private double grandTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        // 1. Inisialisasi View (Pastikan ID di XML sesuai)
        customerSpinner = findViewById(R.id.customer_spinner);
        cartRecycler = findViewById(R.id.billing_cart_recycler);
        grandTotalTxt = findViewById(R.id.billing_grand_total);
        addItemBtn = findViewById(R.id.btn_add_item);
        confirmBtn = findViewById(R.id.confirm_bill_btn);

        // 2. Inisialisasi Database Room
        db = AppDatabase.getDbInstance(getApplicationContext());

        // 3. Setup RecyclerView dengan CartAdapter
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList); // Pasang CartAdapter
        cartRecycler.setAdapter(cartAdapter);

        // 4. Load Data Awal (Pelanggan & Produk)
        loadCustomers();
        loadProducts();

        // 5. Event Listeners (Tombol diklik)
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductSelectionDialog();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCheckout();
            }
        });
    }

    // --- FUNGSI LOAD DATA ---

    private void loadCustomers() {
        customerList = db.customersDao().getAllCustomers();

        List<String> names = new ArrayList<>();
        if (customerList.isEmpty()) {
            names.add("Umum (Tanpa Nama)");
        } else {
            for (CustomersModelClass c : customerList) {
                names.add(c.customerName);
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        customerSpinner.setAdapter(spinnerAdapter);
    }

    private void loadProducts() {
        // Ambil semua produk untuk ditampilkan di dialog pilihan
        allProducts = db.productsDao().getAllProducts();
    }

    // --- FUNGSI LOGIKA KERANJANG & DIALOG ---

    // Menampilkan daftar produk dalam bentuk Dialog
    private void showProductSelectionDialog() {
        if (allProducts.isEmpty()) {
            Toast.makeText(this, "Stok barang kosong di Gudang!", Toast.LENGTH_SHORT).show();
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

    // Dialog input angka jumlah beli
    private void showQuantityDialog(ProductsModel selectedProduct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Beli Berapa " + selectedProduct.productName + "?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qtyStr = input.getText().toString();
                if (!TextUtils.isEmpty(qtyStr)) {
                    int qty = Integer.parseInt(qtyStr);

                    // Validasi Stok
                    int currentStock = 0;
                    try {
                        currentStock = Integer.parseInt(selectedProduct.productQuantity);
                    } catch (NumberFormatException e) {
                        currentStock = 0;
                    }

                    if (qty <= 0) {
                        Toast.makeText(Billing.this, "Jumlah tidak valid", Toast.LENGTH_SHORT).show();
                    } else if (qty > currentStock) {
                        Toast.makeText(Billing.this, "Stok Kurang! Sisa cuma: " + currentStock, Toast.LENGTH_LONG).show();
                    } else {
                        // Masukkan ke keranjang
                        addToCart(selectedProduct, qty);
                    }
                }
            }
        });
        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void addToCart(ProductsModel product, int qty) {
        // Buat objek item keranjang baru
        CartItem item = new CartItem(product, qty);
        cartList.add(item);

        // Refresh tampilan RecyclerView
        cartAdapter.notifyDataSetChanged();

        // Hitung ulang total harga
        calculateTotal();
    }

    private void calculateTotal() {
        grandTotal = 0;
        for (CartItem item : cartList) {
            grandTotal += item.subtotal;
        }
        // Update teks Total di bawah layar
        grandTotalTxt.setText("Rp " + String.format("%.0f", grandTotal));
    }

    // --- FUNGSI CHECKOUT / BAYAR ---

    private void processCheckout() {
        if (cartList.isEmpty()) {
            Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Siapkan Data Laporan
        String customerName = "Umum";
        if (customerSpinner.getSelectedItem() != null) {
            customerName = customerSpinner.getSelectedItem().toString();
        }

        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Buat ringkasan item (Contoh: "Kopi x2, Gula x1")
        StringBuilder itemsSummary = new StringBuilder();
        for (CartItem item : cartList) {
            itemsSummary.append(item.product.productName)
                    .append(" (").append(item.qty).append("), ");
        }

        // 2. Simpan ke Tabel Billing (Laporan)
        BillingModel bill = new BillingModel();
        bill.customerName = customerName;
        bill.date = date;
        bill.totalAmount = String.valueOf(grandTotal);
        bill.itemsSummary = itemsSummary.toString();

        db.billingDao().insertBill(bill);

        // 3. KURANGI STOK BARANG DI DATABASE
        for (CartItem cartItem : cartList) {
            ProductsModel product = cartItem.product;

            int oldQty = 0;
            try {
                oldQty = Integer.parseInt(product.productQuantity);
            } catch (Exception e) { oldQty = 0; }

            int soldQty = cartItem.qty;
            int newQty = oldQty - soldQty;

            // Update data di objek model
            product.productQuantity = String.valueOf(newQty);

            // Update ke Database Room (Timpa data lama)
            db.productsDao().updateProduct(product);
        }

        // 4. Selesai
        Toast.makeText(this, "Transaksi Sukses & Stok Terupdate!", Toast.LENGTH_LONG).show();
        finish(); // Tutup halaman kasir dan kembali ke menu utama
    }
}