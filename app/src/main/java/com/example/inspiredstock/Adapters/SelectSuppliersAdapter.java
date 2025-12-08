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

    private List<SuppliersModelClass> list;
    private Context context;

    // Constructor
    public SelectSuppliersAdapter(List<SuppliersModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menggunakan layout row_supplier.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_supplier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuppliersModelClass item = list.get(position);

        // Set Data ke View (Pastikan menggunakan Getter)
        holder.name.setText(item.getSupplierName());
        holder.phone.setText(item.getSupplierContact());

        // Logika Klik Item -> Kirim Nama Supplier kembali ke Activity Sebelumnya
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("selected_supplier_name", item.getSupplierName());

            // Mengirim hasil kembali (Set Result OK)
            ((Activity) context).setResult(Activity.RESULT_OK, intent);
            ((Activity) context).finish(); // Menutup layar pilih supplier
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // --- BAGIAN INI YANG SERING LUPA DIBUAT ---
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Menghubungkan ID dari row_supplier.xml
            name = itemView.findViewById(R.id.tvSuppName);
            phone = itemView.findViewById(R.id.tvSuppContact);
        }
    }
}