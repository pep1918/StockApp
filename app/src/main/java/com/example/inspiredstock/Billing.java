package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.Models.ProductsModel;
import java.util.ArrayList;
import java.util.List;

public class Billing extends AppCompatActivity {

    Spinner customerSpinner;
    Button btnAddItem, btnPay;
    TextView tvGrandTotal;
    RecyclerView recyclerView;
    AppDatabase db;
    List<ProductsModel> allProducts;
    List<String> productNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        db = AppDatabase.getDbInstance(this);

        customerSpinner = findViewById(R.id.customer_spinner);
        tvGrandTotal = findViewById(R.id.billing_grand_total);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnPay = findViewById(R.id.confirm_bill_btn);
        recyclerView = findViewById(R.id.billing_cart_recycler);

        loadCustomers();
        loadProducts();

        if (btnAddItem != null) {
            btnAddItem.setOnClickListener(v -> showProductDialog());
        }
    }

    private void loadCustomers() {
        List<CustomersModelClass> customers = db.customersDao().getAllCustomers();
        List<String> customerNames = new ArrayList<>();
        for (CustomersModelClass c : customers) {
            customerNames.add(c.getCustomerName()); // GUNAKAN GETTER
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, customerNames);
        if (customerSpinner != null) customerSpinner.setAdapter(adapter);
    }

    private void loadProducts() {
        allProducts = db.productsDao().getAllProducts();
        productNames.clear();
        for (ProductsModel p : allProducts) {
            // GUNAKAN GETTER & STOK SUDAH INT (TIDAK PERLU STRING)
            productNames.add(p.getProductName() + " (Stok: " + p.getStock() + ")");
        }
    }

    private void showProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Produk");
        builder.setItems(productNames.toArray(new String[0]), (dialog, which) -> {
            ProductsModel selected = allProducts.get(which);
            addToCart(selected);
        });
        builder.show();
    }

    private void addToCart(ProductsModel product) {
        if (product.getStock() > 0) {
            // Update Stok (Gunakan Setter)
            product.setStock(product.getStock() - 1);
            db.productsDao().updateProduct(product);

            Toast.makeText(this, "Ditambahkan: " + product.getProductName(), Toast.LENGTH_SHORT).show();
            loadProducts(); // Refresh list
        } else {
            Toast.makeText(this, "Stok Habis!", Toast.LENGTH_SHORT).show();
        }
    }
}