package com.example.inspiredstock;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;

public class AddSuppliersactivity extends AppCompatActivity {

    private EditText sName, sPhone, sEmail, sAddress;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suppliersactivity);

        sName = findViewById(R.id.supplier_name_input);
        sPhone = findViewById(R.id.supplier_phone_input);
        sEmail = findViewById(R.id.supplier_email_input);
        sAddress = findViewById(R.id.supplier_address_input);
        saveBtn = findViewById(R.id.save_supplier_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSupplier();
            }
        });
    }

    private void saveSupplier() {
        String name = sName.getText().toString();
        String phone = sPhone.getText().toString();
        String email = sEmail.getText().toString();
        String address = sAddress.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Nama Supplier Kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        SuppliersModelClass supplier = new SuppliersModelClass(name, phone, email, address);

        db.suppliersDao().insertSupplier(supplier);
        Toast.makeText(this, "Supplier Tersimpan!", Toast.LENGTH_SHORT).show();
        finish();
    }
}