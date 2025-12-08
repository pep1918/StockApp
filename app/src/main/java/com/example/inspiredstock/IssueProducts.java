package com.example.inspiredstock;

import android.os.Bundle;
import android.view.View;
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

public class IssueProducts extends AppCompatActivity {

    Spinner spinnerProducts;
    EditText etQty;
    Button btnSubmit;
    AppDatabase db;
    List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_products);

        db = AppDatabase.getDbInstance(this);

        // Pastikan ID ini ada di XML activity_issue_products.xml
        spinnerProducts = findViewById(R.id.issue_product_spinner);
        etQty = findViewById(R.id.issue_qty_input);
        btnSubmit = findViewById(R.id.issue_submit_btn);

        loadProductsToSpinner();

        btnSubmit.setOnClickListener(v -> issueStock());
    }

    private void loadProductsToSpinner() {
        productList = db.productsDao().getAllProducts();
        List<String> names = new ArrayList<>();
        for (ProductsModel p : productList) {
            names.add(p.getProductName()); // Getter
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        spinnerProducts.setAdapter(adapter);
    }

    private void issueStock() {
        int selectedPos = spinnerProducts.getSelectedItemPosition();
        String qtyStr = etQty.getText().toString();

        if (qtyStr.isEmpty()) {
            Toast.makeText(this, "Masukkan Jumlah", Toast.LENGTH_SHORT).show();
            return;
        }

        int qtyToReduce = Integer.parseInt(qtyStr);
        ProductsModel selectedProduct = productList.get(selectedPos);
        int currentStock = selectedProduct.getStock(); // Getter

        if (currentStock >= qtyToReduce) {
            selectedProduct.setStock(currentStock - qtyToReduce); // Setter
            db.productsDao().updateProduct(selectedProduct);
            Toast.makeText(this, "Stok Berhasil Dikurangi", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Stok Tidak Cukup!", Toast.LENGTH_SHORT).show();
        }
    }
}