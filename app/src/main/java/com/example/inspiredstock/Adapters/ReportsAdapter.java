package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.R;

import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private Context context;
    private List<BillingModel> list;

    public ReportsAdapter(Context context, List<BillingModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<BillingModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gunakan layout sample yang sudah ada atau buat reports_detail_recycler_sample.xml
        View view = LayoutInflater.from(context).inflate(R.layout.reports_detail_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillingModel item = list.get(position);

        holder.customerName.setText(item.customerName);
        holder.date.setText(item.date);
        holder.total.setText("Rp " + item.totalAmount);

        // Menampilkan ringkasan barang yang dibeli (opsional)
        holder.items.setText(item.itemsSummary);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, date, total, items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan file XML reports_detail_recycler_sample.xml
            customerName = itemView.findViewById(R.id.report_customer_name);
            date = itemView.findViewById(R.id.report_date);
            total = itemView.findViewById(R.id.report_total_amount);
            items = itemView.findViewById(R.id.report_items_summary); // Tambahkan TextView ini di XML jika ingin lihat detail
        }
    }
}