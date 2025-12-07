package com.example.inspiredstock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;

public class AddCustomersActivity extends AppCompatActivity {

    private EditText inputName, inputPhone, inputAddress, inputEmail;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customers);

        inputName = findViewById(R.id.customer_name);
        inputPhone = findViewById(R.id.customer_phone);
        inputAddress = findViewById(R.id.customer_address);
        inputEmail = findViewById(R.id.customer_email);
        saveBtn = findViewById(R.id.add_customer_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomer();
            }
        });
    }

    private void saveCustomer() {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String address = inputAddress.getText().toString();
        String email = inputEmail.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Nama dan Telepon wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        CustomersModelClass customer = new CustomersModelClass();
        customer.customerName = name;
        customer.customerPhone = phone;
        customer.customerAddress = address;
        customer.customerEmail = email;

        db.customersDao().insertCustomer(customer);
        Toast.makeText(this, "Pelanggan Disimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}