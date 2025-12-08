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

        // Pastikan ID ini ada di activity_add_new_products.xml
        etName = findViewById(R.id.et_product_name);
        etCategory = findViewById(R.id.et_product_category);
        etPrice = findViewById(R.id.et_product_price);
        etStock = findViewById(R.id.et_product_stock);
        btnSave = findViewById(R.id.btn_save_product);

        btnSave.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        if (etName.getText().toString().isEmpty() || etPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Isi data penting!", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etName.getText().toString();
        String cat = etCategory.getText().toString();
        double price = Double.parseDouble(etPrice.getText().toString());
        int stock = etStock.getText().toString().isEmpty() ? 0 : Integer.parseInt(etStock.getText().toString());

        ProductsModel product = new ProductsModel();
        // PERBAIKAN: Setter
        product.setProductName(name);
        product.setProductCategory(cat);
        product.setPrice(price);
        product.setStock(stock);
        product.setImagePath("");

        AppDatabase.getDbInstance(getApplicationContext()).productsDao().insertProduct(product);

        Toast.makeText(this, "Tersimpan", Toast.LENGTH_SHORT).show();
        finish();
    }
}