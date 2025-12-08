package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    Spinner customerSpinner;
    Button btnAddItem, btnPay;
    TextView tvGrandTotal;
    RecyclerView recyclerView;

    AppDatabase db;
    List<ProductsModel> allProducts;
    List<String> productNames = new ArrayList<>();

    // Keranjang Belanja
    List<CartItem> cartList = new ArrayList<>();
    CartAdapter cartAdapter;
    double grandTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        db = AppDatabase.getDbInstance(this);

        // ID sesuai activity_billing.xml
        customerSpinner = findViewById(R.id.customer_spinner);
        tvGrandTotal = findViewById(R.id.billing_grand_total);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnPay = findViewById(R.id.confirm_bill_btn);
        recyclerView = findViewById(R.id.billing_cart_recycler);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList);
        recyclerView.setAdapter(cartAdapter);

        loadCustomers();
        loadProducts();

        btnAddItem.setOnClickListener(v -> showProductDialog());
        btnPay.setOnClickListener(v -> processPayment());
    }

    private void loadCustomers() {
        List<CustomersModelClass> customers = db.customersDao().getAllCustomers();
        List<String> names = new ArrayList<>();
        for (CustomersModelClass c : customers) {
            names.add(c.getCustomerName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        customerSpinner.setAdapter(adapter);
    }

    private void loadProducts() {
        allProducts = db.productsDao().getAllProducts();
        productNames.clear();
        for (ProductsModel p : allProducts) {
            productNames.add(p.getProductName()); // Tampilkan nama saja di dialog
        }
    }

    private void showProductDialog() {
        if (productNames.isEmpty()) {
            Toast.makeText(this, "Belum ada produk!", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Produk");
        builder.setItems(productNames.toArray(new String[0]), (dialog, which) -> {
            ProductsModel selected = allProducts.get(which);
            addToCart(selected);
        });
        builder.show();
    }

    private void addToCart(ProductsModel product) {
        // Cek stok
        if (product.getStock() <= 0) {
            Toast.makeText(this, "Stok Habis!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cek apakah barang sudah ada di cart
        boolean exists = false;
        for (CartItem item : cartList) {
            if (item.getProduct().getId() == product.getId()) {
                // Jika ada, tambah qty
                if (item.getQuantity() < product.getStock()) {
                    item.setQuantity(item.getQuantity() + 1);
                    exists = true;
                } else {
                    Toast.makeText(this, "Stok tidak cukup!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if (!exists) {
            cartList.add(new CartItem(product, 1));
        }

        updateGrandTotal();
        cartAdapter.notifyDataSetChanged();
    }

    private void updateGrandTotal() {
        grandTotal = 0;
        for (CartItem item : cartList) {
            grandTotal += (item.getProduct().getPrice() * item.getQuantity());
        }
        tvGrandTotal.setText("Rp " + grandTotal);
    }

    private void processPayment() {
        if (cartList.isEmpty()) {
            Toast.makeText(this, "Keranjang Kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        String customerName = customerSpinner.getSelectedItem() != null ?
                customerSpinner.getSelectedItem().toString() : "Umum";

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        // 1. Simpan Transaksi ke Billing Table
        BillingModel bill = new BillingModel();
        bill.setCustomerName(customerName);
        bill.setTotalAmount(grandTotal);
        bill.setDate(date);

        // Buat ringkasan item string
        StringBuilder summary = new StringBuilder();
        for(CartItem item : cartList) {
            summary.append(item.getProduct().getProductName())
                    .append(" (").append(item.getQuantity()).append("), ");
        }
        bill.setItemsSummary(summary.toString());

        db.billingDao().insertBill(bill);

        // 2. Kurangi Stok di Database Produk
        for (CartItem item : cartList) {
            ProductsModel p = item.getProduct();
            int newStock = p.getStock() - item.getQuantity();
            p.setStock(newStock);
            db.productsDao().updateProduct(p);
        }

        Toast.makeText(this, "Pembayaran Sukses!", Toast.LENGTH_LONG).show();
        finish(); // Tutup halaman billing
    }
}