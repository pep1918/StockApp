package com.example.inspiredstock;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Import Adapter yang baru kita perbaiki
import com.example.inspiredstock.Adapters.SuppliersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SuppliersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalSuppliers;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private List<SuppliersModelClass> supplierList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers);

        // Inisialisasi Database
        database = AppDatabase.getDbInstance(this);

        // Binding ID dari XML
        tvTotalSuppliers = findViewById(R.id.tvTotalSuppliers);
        recyclerView = findViewById(R.id.recyclerSuppliers);
        fabAdd = findViewById(R.id.fabAddSupplier);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load Data Awal
        loadData();

        // Tombol Tambah
        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        // Ambil data dari database
        supplierList = database.suppliersDao().getAllSuppliers();

        // Pasang ke Adapter
        SuppliersAdapter adapter = new SuppliersAdapter(supplierList, this);
        recyclerView.setAdapter(adapter);

        // Update Total
        tvTotalSuppliers.setText(supplierList.size() + " Mitra");
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_supplier_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        EditText etName = view.findViewById(R.id.etSuppName);
        EditText etContact = view.findViewById(R.id.etSuppContact);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nama Supplier wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan Data
            SuppliersModelClass supplier = new SuppliersModelClass();
            supplier.setSupplierName(etName.getText().toString());
            supplier.setSupplierContact(etContact.getText().toString());

            database.suppliersDao().insertSupplier(supplier);

            Toast.makeText(this, "Supplier Disimpan", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            loadData(); // Refresh List
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}