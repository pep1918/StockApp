package com.example.inspiredstock.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SuppliersAdapter extends RecyclerView.Adapter<SuppliersAdapter.MyViewHolder> {

    private Context context;
    private List<SuppliersModelClass> list;

    public SuppliersAdapter(List<SuppliersModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_supplier, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SuppliersModelClass item = list.get(position);
        holder.name.setText(item.getSupplierName());
        holder.contact.setText(item.getSupplierContact());

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Hapus " + item.getSupplierName() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).suppliersDao().deleteSupplier(item); // Pastikan ada method deleteSupplier di DAO
                        list.remove(position);
                        notifyItemRemoved(position);
                    }).show();
            return true;
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, contact;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvSuppName);
            contact = itemView.findViewById(R.id.tvSuppContact);
        }
    }
}