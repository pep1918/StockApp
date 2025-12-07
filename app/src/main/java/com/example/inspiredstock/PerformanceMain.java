package com.example.inspiredstock;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel; // <--- SUDAH DIPERBAIKI
import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.Models.ProductsModel;

import java.util.List;

public class PerformanceMain extends AppCompatActivity {

    private TextView txtTotalSales, txtTotalExpenses, txtNetProfit, txtStockCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_main);

        txtTotalSales = findViewById(R.id.perf_total_sales);
        txtTotalExpenses = findViewById(R.id.perf_total_expenses);
        txtNetProfit = findViewById(R.id.perf_net_profit);
        txtStockCount = findViewById(R.id.perf_stock_count);

        calculatePerformance();
    }

    private void calculatePerformance() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        // 1. Hitung Total Penjualan (Income)
        List<BillingModel> bills = db.billingDao().getAllBills();
        double totalSales = 0;
        for (BillingModel bill : bills) {
            try {
                totalSales += Double.parseDouble(bill.totalAmount);
            } catch (Exception e) {}
        }

        // 2. Hitung Total Pengeluaran (Expenses)
        List<ExpensesModel> expenses = db.expensesDao().getAllExpenses();
        double totalExpenses = 0;
        for (ExpensesModel exp : expenses) {
            try {
                totalExpenses += Double.parseDouble(exp.expenseAmount);
            } catch (Exception e) {}
        }

        // 3. Hitung Jumlah Stok Barang
        List<ProductsModel> products = db.productsDao().getAllProducts();
        int totalStockItems = 0;
        for(ProductsModel p : products){
            try {
                totalStockItems += Integer.parseInt(p.productQuantity);
            } catch (Exception e){}
        }

        // 4. Hitung Profit
        double netProfit = totalSales - totalExpenses;

        // 5. Tampilkan
        txtTotalSales.setText("Rp " + String.format("%.0f", totalSales));
        txtTotalExpenses.setText("Rp " + String.format("%.0f", totalExpenses));
        txtNetProfit.setText("Rp " + String.format("%.0f", netProfit));
        txtStockCount.setText(totalStockItems + " Unit");

        if(netProfit >= 0){
            txtNetProfit.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            txtNetProfit.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
}