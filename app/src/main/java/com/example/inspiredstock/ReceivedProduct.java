package com.example.inspiredstock;

import android.os.Bundle;
import android.text.TextUtils;
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

    private Spinner productSpinner;
    private EditText qtyInput;
    private Button saveBtn;
    private List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_product);

        productSpinner = findViewById(R.id.received_product_spinner);
        qtyInput = findViewById(R.id.received_qty_input);
        saveBtn = findViewById(R.id.btn_add_stock);

        loadProducts();

        saveBtn.setOnClickListener(v -> addStock());
    }

    private void loadProducts() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        productList = db.productsDao().getAllProducts();

        List<String> names = new ArrayList<>();
        for(ProductsModel p : productList) names.add(p.productName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        productSpinner.setAdapter(adapter);
    }

    private void addStock() {
        int pos = productSpinner.getSelectedItemPosition();
        String qtyStr = qtyInput.getText().toString();

        if(pos >= 0 && !TextUtils.isEmpty(qtyStr)){
            ProductsModel product = productList.get(pos);
            int addQty = Integer.parseInt(qtyStr);
            int currentQty = Integer.parseInt(product.productQuantity);

            product.productQuantity = String.valueOf(currentQty + addQty); // Tambah Stok

            AppDatabase.getDbInstance(getApplicationContext()).productsDao().updateProduct(product);
            Toast.makeText(this, "Stok Berhasil Ditambah!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}