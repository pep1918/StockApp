package com.example.inspiredstock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
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

    private Spinner customerSpinner;
    private RecyclerView cartRecycler;
    private TextView grandTotalTxt;
    private Button addItemBtn, confirmBtn;

    private AppDatabase db;
    private List<CustomersModelClass> customerList = new ArrayList<>();
    private List<ProductsModel> allProducts = new ArrayList<>();

    // Keranjang Belanja
    private List<CartItem> cartList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private double grandTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        // Init Views
        customerSpinner = findViewById(R.id.customer_spinner);
        cartRecycler = findViewById(R.id.billing_cart_recycler);
        grandTotalTxt = findViewById(R.id.billing_grand_total);
        addItemBtn = findViewById(R.id.btn_add_item);
        confirmBtn = findViewById(R.id.confirm_bill_btn);

        // Init Database
        db = AppDatabase.getDbInstance(getApplicationContext());

        // Setup RecyclerView Cart
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList);
        cartRecycler.setAdapter(cartAdapter);

        // Load Data Awal
        loadCustomers();
        loadProducts();

        // Event Listeners
        addItemBtn.setOnClickListener(v -> showProductSelectionDialog());
        confirmBtn.setOnClickListener(v -> processCheckout());
    }

    private void loadCustomers() {
        customerList = db.customersDao().getAllCustomers();
        List<String> names = new ArrayList<>();
        if (customerList.isEmpty()) {
            names.add("Umum (Tanpa Nama)");
        } else {
            for (CustomersModelClass c : customerList) names.add(c.customerName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        customerSpinner.setAdapter(adapter);
    }

    private void loadProducts() {
        allProducts = db.productsDao().getAllProducts();
    }

    // Dialog untuk memilih barang dari list
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
        builder.setItems(productNames, (dialog, which) -> {
            // User memilih barang ke-'which'
            showQuantityDialog(allProducts.get(which));
        });
        builder.show();
    }

    // Dialog input jumlah barang
    private void showQuantityDialog(ProductsModel selectedProduct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Masukkan Jumlah Beli (" + selectedProduct.productName + ")");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String qtyStr = input.getText().toString();
            if (!qtyStr.isEmpty()) {
                int qty = Integer.parseInt(qtyStr);
                int currentStock = Integer.parseInt(selectedProduct.productQuantity);

                // CEK STOK
                if (qty > currentStock) {
                    Toast.makeText(this, "Stok tidak cukup! Sisa: " + currentStock, Toast.LENGTH_LONG).show();
                } else {
                    addToCart(selectedProduct, qty);
                }
            }
        });
        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void addToCart(ProductsModel product, int qty) {
        CartItem item = new CartItem(product, qty);
        cartList.add(item);
        cartAdapter.notifyDataSetChanged();
        calculateTotal();
    }

    private void calculateTotal() {
        grandTotal = 0;
        for (CartItem item : cartList) {
            grandTotal += item.subtotal;
        }
        grandTotalTxt.setText("Rp " + String.format("%.0f", grandTotal));
    }

    private void processCheckout() {
        if (cartList.isEmpty()) {
            Toast.makeText(this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Simpan Transaksi ke Tabel Billing
        String customerName = customerSpinner.getSelectedItem() != null ? customerSpinner.getSelectedItem().toString() : "Umum";
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        // Buat ringkasan item (misal: "Kopi x2, Gula x1")
        StringBuilder itemsSummary = new StringBuilder();
        for(CartItem item : cartList){
            itemsSummary.append(item.product.productName).append(" x").append(item.qty).append(", ");
        }

        BillingModel bill = new BillingModel();
        bill.customerName = customerName;
        bill.date = date;
        bill.totalAmount = String.valueOf(grandTotal);
        bill.itemsSummary = itemsSummary.toString();

        db.billingDao().insertBill(bill);

        // 2. KURANGI STOK BARANG (PENTING!)
        for (CartItem cartItem : cartList) {
            ProductsModel product = cartItem.product;

            int oldQty = Integer.parseInt(product.productQuantity);
            int soldQty = cartItem.qty;
            int newQty = oldQty - soldQty;

            // Update objek product
            product.productQuantity = String.valueOf(newQty);

            // Update ke Database Room
            db.productsDao().updateProduct(product);
        }

        Toast.makeText(this, "Transaksi Berhasil! Stok Terupdate.", Toast.LENGTH_LONG).show();
        finish(); // Tutup halaman kasir
    }
}