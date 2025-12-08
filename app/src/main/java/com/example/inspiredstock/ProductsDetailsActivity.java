package com.example.inspiredstock;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import java.util.List;

public class ProductsDetailsActivity extends AppCompatActivity {

    EditText pName, pCategory, pPrice, pQty;
    ImageView pImage;
    Button btnUpdate;
    AppDatabase db;
    ProductsModel currentProduct;
    int productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        // Mapping ID sesuai activity_products_details.xml
        pName = findViewById(R.id.detail_name);
        pCategory = findViewById(R.id.detail_category);
        pPrice = findViewById(R.id.detail_price);
        pQty = findViewById(R.id.detail_qty);
        pImage = findViewById(R.id.detail_image);
        btnUpdate = findViewById(R.id.detail_update_btn);

        db = AppDatabase.getDbInstance(this);

        // Menerima ID dari Intent
        if (getIntent().hasExtra("product_id")) {
            productId = getIntent().getIntExtra("product_id", 0);
            loadProductData(productId);
        }

        btnUpdate.setOnClickListener(v -> updateProduct());
    }

    private void loadProductData(int id) {
        // Cara sederhana mencari berdasarkan ID (bisa dioptimalkan dengan query getById di DAO)
        List<ProductsModel> list = db.productsDao().getAllProducts();
        for (ProductsModel p : list) {
            if (p.getId() == id) {
                currentProduct = p;

                // Gunakan Getter
                pName.setText(p.getProductName());
                pCategory.setText(p.getProductCategory());
                pPrice.setText(String.valueOf(p.getPrice())); // Double ke String
                pQty.setText(String.valueOf(p.getStock()));   // Int ke String

                if (p.getImagePath() != null && !p.getImagePath().isEmpty()) {
                    Glide.with(this).load(Uri.parse(p.getImagePath())).into(pImage);
                }
                break;
            }
        }
    }

    private void updateProduct() {
        if (currentProduct == null) return;

        // Gunakan Setter untuk update data
        currentProduct.setProductName(pName.getText().toString());
        currentProduct.setProductCategory(pCategory.getText().toString());

        try {
            currentProduct.setPrice(Double.parseDouble(pPrice.getText().toString()));
            currentProduct.setStock(Integer.parseInt(pQty.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Harga/Stok harus angka!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan perubahan
        db.productsDao().updateProduct(currentProduct);

        Toast.makeText(this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
        finish(); // Kembali ke list
    }
}