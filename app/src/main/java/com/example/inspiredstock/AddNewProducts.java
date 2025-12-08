package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;

public class AddNewProducts extends AppCompatActivity {

    EditText etName, etCategory, etPrice, etStock;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_products);

        etName = findViewById(R.id.et_product_name); // Sesuaikan ID XML Anda
        etCategory = findViewById(R.id.et_product_category);
        etPrice = findViewById(R.id.et_product_price);
        etStock = findViewById(R.id.et_product_stock);
        btnSave = findViewById(R.id.btn_save_product);

        btnSave.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String name = etName.getText().toString();
        String category = etCategory.getText().toString();
        String priceStr = etPrice.getText().toString();
        String stockStr = etStock.getText().toString();

        if (name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Isi data!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductsModel product = new ProductsModel();
        // PERBAIKAN: GUNAKAN SETTER
        product.setProductName(name);
        product.setProductCategory(category);
        product.setPrice(Double.parseDouble(priceStr));
        product.setStock(Integer.parseInt(stockStr));
        product.setImagePath(""); // Kosongkan dulu jika tidak ada gambar

        AppDatabase.getDbInstance(getApplicationContext()).productsDao().insertProduct(product);
        finish();
    }
}