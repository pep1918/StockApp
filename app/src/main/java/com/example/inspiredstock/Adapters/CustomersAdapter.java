package com.example.inspiredstock.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {

    private List<CustomersModelClass> list;
    private Context context;

    public CustomersAdapter(List<CustomersModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reuse layout product item agar konsisten
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CustomersModelClass item = list.get(position);

        holder.name.setText(item.getCustomerName());
        holder.phone.setText(item.getCustomerPhone());
        holder.price.setVisibility(View.GONE); // Sembunyikan harga karena ini customer

        // --- FITUR EDIT (KLIK BIASA) ---
        holder.itemView.setOnClickListener(v -> showEditDialog(item, position));

        // --- FITUR DELETE (KLIK TAHAN) ---
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Hapus Pelanggan")
                    .setMessage("Hapus " + item.getCustomerName() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).customersDao().deleteCustomer(item);
                        list.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Terhapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
            return true;
        });
    }

    // Fungsi Dialog Edit
    private void showEditDialog(CustomersModelClass item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_customer_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText etName = view.findViewById(R.id.etCustName);
        EditText etPhone = view.findViewById(R.id.etCustPhone);
        EditText etAddress = view.findViewById(R.id.etCustAddress);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        TextView tvTitle = view.findViewById(R.id.tvTitle); // Jika ada ID title di XML dialog

        // Isi data lama
        etName.setText(item.getCustomerName());
        etPhone.setText(item.getCustomerPhone());
        etAddress.setText(item.getCustomerAddress());
        btnSave.setText("UPDATE"); // Ubah teks tombol

        btnSave.setOnClickListener(v -> {
            // Update Data di Model
            item.setCustomerName(etName.getText().toString());
            item.setCustomerPhone(etPhone.getText().toString());
            item.setCustomerAddress(etAddress.getText().toString());

            // Update ke Database
            AppDatabase.getDbInstance(context).customersDao().updateCustomer(item);

            // Update List Tampilan
            notifyItemChanged(position);
            dialog.dismiss();
            Toast.makeText(context, "Data Diupdate", Toast.LENGTH_SHORT).show();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            phone = itemView.findViewById(R.id.tvProductStock);
            price = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}