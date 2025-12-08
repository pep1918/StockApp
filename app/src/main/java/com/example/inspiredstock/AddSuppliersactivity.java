package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;

public class AddSuppliersactivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etAddress;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suppliersactivity); // Pastikan nama layout benar

        etName = findViewById(R.id.supplier_name);
        etPhone = findViewById(R.id.supplier_phone);
        // etEmail = findViewById(R.id.supplier_email); // Hapus jika tidak ada di XML
        // etAddress = findViewById(R.id.supplier_address); // Hapus jika tidak ada di XML
        btnSave = findViewById(R.id.save_supplier_btn);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();

            SuppliersModelClass supplier = new SuppliersModelClass(name, phone);

            AppDatabase.getDbInstance(getApplicationContext()).suppliersDao().insertSupplier(supplier);
            finish();
        });
    }
}