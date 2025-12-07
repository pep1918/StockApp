package com.example.inspiredstock.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SelectCustomersAdapter extends RecyclerView.Adapter<SelectCustomersAdapter.ViewHolder> {
    Context context;
    List<CustomersModelClass> list;

    public SelectCustomersAdapter(Context context, List<CustomersModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customers_recyclerview_sample, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomersModelClass item = list.get(position);
        holder.name.setText(item.customerName);

        // Saat customer dipilih -> Kirim data balik ke Activity sebelumnya (misal Billing)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("selected_customer_name", item.customerName);
            ((Activity)context).setResult(Activity.RESULT_OK, intent);
            ((Activity)context).finish();
        });
    }

    @Override public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name_display);
        }
    }
}