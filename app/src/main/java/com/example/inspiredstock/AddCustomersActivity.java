package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;

public class AddCustomersActivity extends AppCompatActivity {

    EditText inputName, inputPhone, inputAddress, inputEmail;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);


        inputName = findViewById(R.id.customer_name);
        inputPhone = findViewById(R.id.customer_phone);
        inputAddress = findViewById(R.id.customer_address);
        inputEmail = findViewById(R.id.customer_email);
        saveBtn = findViewById(R.id.add_customer_btn);

        saveBtn.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String phone = inputPhone.getText().toString();
            String address = inputAddress.getText().toString();
            String email = inputEmail.getText().toString();

            if(name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Nama dan Telepon wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            CustomersModelClass customer = new CustomersModelClass();

            // --- BAGIAN INI YANG DIPERBAIKI (PAKAI SETTER) ---
            customer.setCustomerName(name);
            customer.setCustomerPhone(phone);
            customer.setCustomerAddress(address);
            customer.setCustomerEmail(email);

            // Simpan ke Database
            AppDatabase.getDbInstance(this).customersDao().insertCustomer(customer);

            Toast.makeText(this, "Pelanggan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke halaman sebelumnya
        });
    }
}