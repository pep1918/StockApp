package com.example.inspiredstock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;

public class AddNewProducts extends AppCompatActivity {

    private ImageView inputProductImage;
    private EditText productName, productCategory, productPrice, productQuantity, productId;
    private Button saveButton;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String imagePathString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_products);

        // Inisialisasi View (Sesuaikan ID dengan XML Anda)
        inputProductImage = findViewById(R.id.select_product_image);
        productName = findViewById(R.id.product_name);
        productCategory = findViewById(R.id.product_category);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.product_quantity);
        productId = findViewById(R.id.product_id_input); // Jika ada field ID manual
        saveButton = findViewById(R.id.add_new_product);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndSave();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            // Ambil izin permanen untuk membaca URI ini (Penting untuk Android 11+)
            try {
                getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imagePathString = imageUri.toString();
            inputProductImage.setImageURI(imageUri);
        }
    }

    private void validateAndSave() {
        String pName = productName.getText().toString();
        String pCategory = productCategory.getText().toString();
        String pPrice = productPrice.getText().toString();
        String pQty = productQuantity.getText().toString();
        String pId = productId.getText().toString();

        if (TextUtils.isEmpty(pName)) {
            Toast.makeText(this, "Nama Produk Wajib Diisi", Toast.LENGTH_SHORT).show();
        } else {
            // SIMPAN KE ROOM DATABASE
            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

            ProductsModel product = new ProductsModel();
            product.productId = pId;
            product.productName = pName;
            product.productCategory = pCategory;
            product.productPrice = pPrice;
            product.productQuantity = pQty;
            product.imagePath = imagePathString;

            db.productsDao().insertProduct(product);

            Toast.makeText(this, "Produk Berhasil Disimpan Offline!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}