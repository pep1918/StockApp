package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class BillingDetail extends AppCompatActivity {

    TextView tvId, tvCustomer, tvItems, tvTotal, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail);

        tvId = findViewById(R.id.detail_bill_id);
        tvCustomer = findViewById(R.id.detail_bill_customer);
        tvItems = findViewById(R.id.detail_bill_items);
        tvTotal = findViewById(R.id.detail_bill_total);
        tvDate = findViewById(R.id.detail_bill_date);

        if (getIntent().getExtras() != null) {
            String id = getIntent().getStringExtra("id");
            String cust = getIntent().getStringExtra("customer");
            String items = getIntent().getStringExtra("items");
            String totalStr = getIntent().getStringExtra("total");
            String date = getIntent().getStringExtra("date");

            tvId.setText("#" + id);
            tvCustomer.setText(cust);

            // Format item agar rapi (misal ganti koma dengan baris baru)
            String formattedItems = items.replace(", ", "\n").replace("),", ")\n");
            tvItems.setText(formattedItems);

            // Format Rupiah
            double total = Double.parseDouble(totalStr);
            tvTotal.setText(formatRupiah(total));

            tvDate.setText(date);
        }
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}