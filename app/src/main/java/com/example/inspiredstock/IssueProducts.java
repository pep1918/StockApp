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

public class IssueProducts extends AppCompatActivity {

    Spinner spinnerProducts;
    EditText etQty;
    Button btnIssue;
    AppDatabase db;
    List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_products);

        db = AppDatabase.getDbInstance(this);
        // Pastikan ID sesuai layout Anda
        // spinnerProducts = findViewById(R.id.issue_product_spinner);
        // etQty = findViewById(R.id.issue_qty_input);
        // btnIssue = findViewById(R.id.issue_submit_btn);

        loadProducts();
    }

    private void loadProducts() {
        productList = db.productsDao().getAllProducts();
        List<String> names = new ArrayList<>();
        for (ProductsModel p : productList) {
            names.add(p.getProductName()); // GUNAKAN GETTER
        }
        // ArrayAdapter code...
    }

    private void issueProduct(int index, int reduceQty) {
        ProductsModel product = productList.get(index);
        int currentQty = product.getStock(); // GUNAKAN GETTER (INT)

        if (currentQty >= reduceQty) {
            product.setStock(currentQty - reduceQty); // GUNAKAN SETTER
            db.productsDao().updateProduct(product);
            Toast.makeText(this, "Stok dikurangi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Stok tidak cukup", Toast.LENGTH_SHORT).show();
        }
    }
}