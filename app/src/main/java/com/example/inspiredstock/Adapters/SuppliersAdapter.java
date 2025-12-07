package com.example.inspiredstock.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Database.AppDatabase;
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
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuppliersModelClass item = list.get(position);
        holder.name.setText(item.supplierName);
        holder.phone.setText(item.supplierPhone);

        // LOGIKA HAPUS SUPPLIER
        holder.deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Supplier")
                    .setMessage("Yakin hapus " + item.supplierName + "?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).suppliersDao().deleteSupplier(item);
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                        Toast.makeText(context, "Supplier Dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pastikan ID ini sesuai dengan recycler_view_sample.xml yang baru
            name = itemView.findViewById(R.id.customer_name_display); // Kita pakai ID yang sama dgn customer biar ringkas
            phone = itemView.findViewById(R.id.customer_phone_display);
            deleteBtn = itemView.findViewById(R.id.btn_delete_item);
        }
    }
}