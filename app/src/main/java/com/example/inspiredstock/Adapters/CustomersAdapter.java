package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    private Context context;
    private List<CustomersModelClass> list;

    public CustomersAdapter(Context context, List<CustomersModelClass> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<CustomersModelClass> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan layout XML ini ada: customers_recyclerview_sample.xml
        View view = LayoutInflater.from(context).inflate(R.layout.customers_recyclerview_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomersModelClass item = list.get(position);
        holder.name.setText(item.customerName);
        holder.phone.setText(item.customerPhone);
        holder.address.setText(item.customerAddress);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan file XML customers_recyclerview_sample.xml
            name = itemView.findViewById(R.id.customer_name_display);
            phone = itemView.findViewById(R.id.customer_phone_display);
            address = itemView.findViewById(R.id.customer_address_display);
        }
    }
}