package com.example.inspiredstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.inspiredstock.Adapters.ProductsAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProductsAdapter adapter;
    private List<ProductsModel> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerView = findViewById(R.id.recycler_menu); // Sesuaikan ID RecyclerView di XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup Adapter Kosong Dulu
        adapter = new ProductsAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab_add_product); // Tombol Tambah
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this, AddNewProducts.class);
                startActivity(intent);
            }
        });
    }

    // Dipanggil setiap kali halaman ini muncul kembali
    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDB();
    }

    private void loadDataFromDB() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        productList = db.productsDao().getAllProducts();

        // Masukkan data ke adapter
        adapter.setList(productList);
    }
}