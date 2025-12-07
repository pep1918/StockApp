package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SuppliersAdapter extends RecyclerView.Adapter<SuppliersAdapter.ViewHolder> {

    private Context context;
    private List<SuppliersModelClass> list;

    public SuppliersAdapter(Context context, List<SuppliersModelClass> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<SuppliersModelClass> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan layout XML ini ada: suppliers_recyclerview_sample.xml (atau nama sejenis di folder layout Anda)
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuppliersModelClass item = list.get(position);
        holder.name.setText(item.supplierName);
        holder.phone.setText(item.supplierPhone);
        holder.email.setText(item.supplierEmail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan file XML item supplier Anda
            name = itemView.findViewById(R.id.supplier_name_text);
            phone = itemView.findViewById(R.id.supplier_phone_text);
            email = itemView.findViewById(R.id.supplier_email_text);
        }
    }
}