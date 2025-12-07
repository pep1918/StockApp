package com.example.inspiredstock.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Database.AppDatabase;
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
        View view = LayoutInflater.from(context).inflate(R.layout.customers_recyclerview_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomersModelClass item = list.get(position);
        holder.name.setText(item.customerName);
        holder.phone.setText(item.customerPhone);

        // LOGIKA HAPUS CUSTOMER
        holder.deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Pelanggan")
                    .setMessage("Yakin hapus " + item.customerName + "?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).customersDao().deleteCustomer(item);
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                        Toast.makeText(context, "Pelanggan Dihapus", Toast.LENGTH_SHORT).show();
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
            name = itemView.findViewById(R.id.customer_name_display);
            phone = itemView.findViewById(R.id.customer_phone_display);
            deleteBtn = itemView.findViewById(R.id.btn_delete_item); // ID dari XML
        }
    }
}