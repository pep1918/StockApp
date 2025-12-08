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

import com.example.inspiredstock.Adapters.CustomersAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalCustomers;
    private FloatingActionButton fabAdd;
    private AppDatabase database;
    private List<CustomersModelClass> customerList;
    private CustomersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        // 1. Inisialisasi Database
        database = AppDatabase.getDbInstance(this);

        // 2. Hubungkan ID dengan XML
        tvTotalCustomers = findViewById(R.id.tvTotalCustomers);
        recyclerView = findViewById(R.id.recyclerCustomers);
        fabAdd = findViewById(R.id.fabAddCustomer);

        // 3. Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4. Load Data Awal
        loadData();

        // 5. Aksi Tombol Tambah
        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        // Ambil data dari Database via DAO
        customerList = database.customersDao().getAllCustomers();

        // Pasang ke Adapter
        adapter = new CustomersAdapter(customerList, this);
        recyclerView.setAdapter(adapter);

        // Update Statistik Total
        tvTotalCustomers.setText(customerList.size() + " Orang");
    }

    private void showAddDialog() {
        // Inflate Layout Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_customer_input, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        // Background Transparan agar sudut CardView terlihat melengkung
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Init View di dalam Dialog
        EditText etName = view.findViewById(R.id.etCustName);
        EditText etPhone = view.findViewById(R.id.etCustPhone);
        EditText etAddress = view.findViewById(R.id.etCustAddress);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Logika Simpan
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String address = etAddress.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Nama wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat Object Model Baru
            CustomersModelClass customer = new CustomersModelClass();
            customer.setCustomerName(name);
            customer.setCustomerPhone(phone);
            customer.setCustomerAddress(address);
            // customer.setCustomerEmail(""); // Jika ada field email, tambahkan disini

            // Simpan ke Database
            database.customersDao().insertCustomer(customer);

            Toast.makeText(this, "Pelanggan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // Refresh Data di Layar
            loadData();
        });

        // Logika Batal
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}