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

public class IssueProducts extends AppCompatActivity {

    private Spinner productSpinner;
    private EditText qtyInput;
    private Button saveBtn;
    private List<ProductsModel> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_products); // Pastikan XML ini ada

        productSpinner = findViewById(R.id.issue_product_spinner);
        qtyInput = findViewById(R.id.issue_qty_input);
        saveBtn = findViewById(R.id.btn_issue_stock);

        loadProducts();

        saveBtn.setOnClickListener(v -> reduceStock());
    }

    private void loadProducts() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        productList = db.productsDao().getAllProducts();
        List<String> names = new ArrayList<>();
        for(ProductsModel p : productList) names.add(p.productName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        productSpinner.setAdapter(adapter);
    }

    private void reduceStock() {
        int pos = productSpinner.getSelectedItemPosition();
        String qtyStr = qtyInput.getText().toString();

        if(pos >= 0 && !TextUtils.isEmpty(qtyStr)){
            ProductsModel product = productList.get(pos);
            int reduceQty = Integer.parseInt(qtyStr);
            int currentQty = Integer.parseInt(product.productQuantity);

            if(currentQty >= reduceQty) {
                product.productQuantity = String.valueOf(currentQty - reduceQty); // Kurangi Stok
                AppDatabase.getDbInstance(getApplicationContext()).productsDao().updateProduct(product);
                Toast.makeText(this, "Stok Berhasil Dikurangi!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Stok Tidak Cukup!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}