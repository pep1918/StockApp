package com.example.inspiredstock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;

public class ProductsDetailsActivity extends AppCompatActivity {

    private EditText pName, pCategory, pPrice, pQty;
    private ImageView pImage;
    private Button updateBtn, deleteBtn;
    private ProductsModel product;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details); // Pastikan layout XML ini ada

        pName = findViewById(R.id.product_name_details);
        pCategory = findViewById(R.id.product_category_details);
        pPrice = findViewById(R.id.product_price_details);
        pQty = findViewById(R.id.product_quantity_details);
        pImage = findViewById(R.id.product_image_details);
        updateBtn = findViewById(R.id.update_product_btn);
        deleteBtn = findViewById(R.id.delete_product_btn);

        // Ambil Data dari Intent (dikirim dari Adapter saat klik list)
        if(getIntent().hasExtra("product_id")) {
            productId = getIntent().getIntExtra("product_id", 0);
            loadProductData(productId);
        }

        updateBtn.setOnClickListener(v -> updateProduct());
        deleteBtn.setOnClickListener(v -> deleteProduct());
    }

    private void loadProductData(int id) {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        // Perlu tambah method getProductById di ProductsDao, tapi kita bisa filter manual jika darurat
        // Idealnya tambahkan: @Query("SELECT * FROM table_products WHERE id = :id") ProductsModel getProductById(int id); di DAO
        // Di sini saya pakai logika pencarian manual dari list untuk kompatibilitas cepat
        for(ProductsModel p : db.productsDao().getAllProducts()){
            if(p.id == id){
                product = p;
                break;
            }
        }

        if(product != null){
            pName.setText(product.productName);
            pCategory.setText(product.productCategory);
            pPrice.setText(product.productPrice);
            pQty.setText(product.productQuantity);
            if(product.imagePath != null && !product.imagePath.isEmpty()) {
                Glide.with(this).load(Uri.parse(product.imagePath)).into(pImage);
            }
        }
    }

    private void updateProduct() {
        if(product == null) return;
        product.productName = pName.getText().toString();
        product.productCategory = pCategory.getText().toString();
        product.productPrice = pPrice.getText().toString();
        product.productQuantity = pQty.getText().toString();

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        db.productsDao().updateProduct(product);
        Toast.makeText(this, "Produk Diupdate!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteProduct() {
        if(product == null) return;
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        db.productsDao().deleteProduct(product);
        Toast.makeText(this, "Produk Dihapus!", Toast.LENGTH_SHORT).show();
        finish();
    }
}