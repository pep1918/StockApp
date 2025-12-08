package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Adapters.ReportsAdapter;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.BillingModel;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReportsDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvGrandTotalRevenue, tvTotalTransactionCount;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_details);

        db = AppDatabase.getDbInstance(this);

        // Hubungkan ID
        recyclerView = findViewById(R.id.recycler_reports);
        tvGrandTotalRevenue = findViewById(R.id.tvGrandTotalRevenue);
        tvTotalTransactionCount = findViewById(R.id.tvTotalTransactionCount);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadReports();
    }

    private void loadReports() {
        // Ambil semua data transaksi (sorted by ID DESC)
        List<BillingModel> list = db.billingDao().getAllBills();

        // Hitung Ringkasan
        double totalRevenue = 0;
        for (BillingModel bill : list) {
            totalRevenue += bill.getTotalAmount();
        }

        // Tampilkan Ringkasan
        tvGrandTotalRevenue.setText(formatRupiah(totalRevenue));
        tvTotalTransactionCount.setText(list.size() + " Transaksi Berhasil");

        // Tampilkan List
        ReportsAdapter adapter = new ReportsAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}