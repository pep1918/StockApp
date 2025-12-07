package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Adapters.ReportsAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.BillingModel;

import java.util.ArrayList;
import java.util.List;

public class ReportsDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportsAdapter adapter;
    private TextView totalSalesTxt;
    private List<BillingModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_details);

        recyclerView = findViewById(R.id.recycler_reports); // Pastikan ID ini ada di XML
        totalSalesTxt = findViewById(R.id.reports_total_sales); // Jika ada textview total di atas

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReportsAdapter(this, list);
        recyclerView.setAdapter(adapter);

        loadReports();
    }

    private void loadReports() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        list = db.billingDao().getAllBills();
        adapter.setList(list);

        // Hitung Total Penjualan Keseluruhan
        double grandTotal = 0;
        for(BillingModel bill : list){
            try {
                grandTotal += Double.parseDouble(bill.totalAmount);
            } catch (Exception e){}
        }

        if(totalSalesTxt != null){
            totalSalesTxt.setText("Total Pendapatan: Rp " + String.format("%.0f", grandTotal));
        }
    }
}