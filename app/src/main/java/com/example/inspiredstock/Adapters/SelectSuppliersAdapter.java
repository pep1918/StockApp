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
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SelectSuppliersAdapter extends RecyclerView.Adapter<SelectSuppliersAdapter.ViewHolder> {
    Context context;
    List<SuppliersModelClass> list;

    public SelectSuppliersAdapter(Context context, List<SuppliersModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_sample, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuppliersModelClass item = list.get(position);
        holder.name.setText(item.supplierName);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("selected_supplier_name", item.supplierName);
            ((Activity)context).setResult(Activity.RESULT_OK, intent);
            ((Activity)context).finish();
        });
    }

    @Override public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.supplier_name_text);
        }
    }
}