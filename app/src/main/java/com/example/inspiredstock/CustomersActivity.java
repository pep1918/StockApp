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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        database = AppDatabase.getDbInstance(this);

        // ID sesuai activity_customers.xml
        tvTotalCustomers = findViewById(R.id.tvTotalCustomers);
        recyclerView = findViewById(R.id.recyclerCustomers);
        fabAdd = findViewById(R.id.fabAddCustomer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadData() {
        customerList = database.customersDao().getAllCustomers();
        CustomersAdapter adapter = new CustomersAdapter(customerList, this);
        recyclerView.setAdapter(adapter);

        // Update Total
        tvTotalCustomers.setText(customerList.size() + " Orang");
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // ID layout: dialog_customer_input.xml
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_customer_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText etName = view.findViewById(R.id.etCustName);
        EditText etPhone = view.findViewById(R.id.etCustPhone);
        EditText etAddress = view.findViewById(R.id.etCustAddress);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nama wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            CustomersModelClass customer = new CustomersModelClass();
            customer.setCustomerName(etName.getText().toString());
            customer.setCustomerPhone(etPhone.getText().toString());
            customer.setCustomerAddress(etAddress.getText().toString());

            database.customersDao().insertCustomer(customer);

            Toast.makeText(this, "Pelanggan Disimpan", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            loadData();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}