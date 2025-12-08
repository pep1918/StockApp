package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.BillingDetail;
import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private List<BillingModel> list;
    private Context context;

    public ReportsAdapter(List<BillingModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kita gunakan layout row_product_item tapi kita sesuaikan isinya
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillingModel item = list.get(position);

        // Baris 1: Nama Customer (Bold)
        holder.title.setText(item.getCustomerName());

        // Baris 2: Tanggal & ID
        holder.subtitle.setText("Tgl: " + item.getDate() + " (ID: #" + item.getId() + ")");

        // Baris 3: Total Uang (Hijau)
        holder.amount.setText(formatRupiah(item.getTotalAmount()));

        // Klik untuk detail struk
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BillingDetail.class);
            intent.putExtra("id", String.valueOf(item.getId()));
            intent.putExtra("customer", item.getCustomerName());
            intent.putExtra("items", item.getItemsSummary());
            intent.putExtra("total", String.valueOf(item.getTotalAmount()));
            intent.putExtra("date", item.getDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvProductName);
            subtitle = itemView.findViewById(R.id.tvProductStock);
            amount = itemView.findViewById(R.id.tvProductPrice);
        }
    }

    private String formatRupiah(double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}