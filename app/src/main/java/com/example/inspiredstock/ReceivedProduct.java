package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import java.util.ArrayList;
import java.util.List;

public class ReceivedProduct extends AppCompatActivity {

    Spinner spinnerProducts;
    EditText etQty;
    Button btnSubmit;
    AppDatabase db;
    List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_product);

        db = AppDatabase.getDbInstance(this);

        // Sesuaikan ID dengan XML
        spinnerProducts = findViewById(R.id.receive_product_spinner);
        etQty = findViewById(R.id.receive_qty_input);
        btnSubmit = findViewById(R.id.receive_submit_btn);

        loadProducts();

        btnSubmit.setOnClickListener(v -> addStock());
    }

    private void loadProducts() {
        productList = db.productsDao().getAllProducts();
        List<String> names = new ArrayList<>();
        for (ProductsModel p : productList) {
            names.add(p.getProductName()); // Getter
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        spinnerProducts.setAdapter(adapter);
    }

    private void addStock() {
        int pos = spinnerProducts.getSelectedItemPosition();
        String qtyStr = etQty.getText().toString();

        if (qtyStr.isEmpty()) return;

        int qtyToAdd = Integer.parseInt(qtyStr);
        ProductsModel product = productList.get(pos);

        // Update Logic
        product.setStock(product.getStock() + qtyToAdd);
        db.productsDao().updateProduct(product);

        Toast.makeText(this, "Stok Ditambahkan", Toast.LENGTH_SHORT).show();
        finish();
    }
}