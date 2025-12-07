package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BillingDetail extends AppCompatActivity {
    TextView customer, date, items, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail); // Pastikan XML ini ada

        customer = findViewById(R.id.detail_bill_customer);
        date = findViewById(R.id.detail_bill_date);
        items = findViewById(R.id.detail_bill_items);
        total = findViewById(R.id.detail_bill_total);

        // Menerima data dari Intent (Dikirim dari ReportsAdapter)
        if(getIntent().hasExtra("customer")) {
            customer.setText(getIntent().getStringExtra("customer"));
            date.setText(getIntent().getStringExtra("date"));
            total.setText("Total: Rp " + getIntent().getStringExtra("total"));
            items.setText(getIntent().getStringExtra("items"));
        }
    }
}