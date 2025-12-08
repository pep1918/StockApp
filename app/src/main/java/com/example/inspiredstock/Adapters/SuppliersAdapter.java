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
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SuppliersAdapter extends RecyclerView.Adapter<SuppliersAdapter.ViewHolder> {

    private List<SuppliersModelClass> list;
    private Context context;

    public SuppliersAdapter(List<SuppliersModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_supplier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SuppliersModelClass item = list.get(position);

        holder.name.setText(item.getSupplierName());
        holder.contact.setText(item.getSupplierContact());

        // --- EDIT (Klik Biasa) ---
        holder.itemView.setOnClickListener(v -> showEditDialog(item, position));

        // --- DELETE (Klik Tahan) ---
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Hapus Supplier")
                    .setMessage("Hapus " + item.getSupplierName() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).suppliersDao().deleteSupplier(item); // Pastikan DAO ada @Delete
                        list.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Terhapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null).show();
            return true;
        });
    }

    private void showEditDialog(SuppliersModelClass item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_supplier_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if(dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText etName = view.findViewById(R.id.etSuppName);
        EditText etContact = view.findViewById(R.id.etSuppContact);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        etName.setText(item.getSupplierName());
        etContact.setText(item.getSupplierContact());
        btnSave.setText("UPDATE");

        btnSave.setOnClickListener(v -> {
            // Update logika manual (karena DAO mungkin belum ada updateSupplier)
            // Hapus yang lama, insert yang baru (cara cepat jika malas nambah DAO Update)
            // ATAU Cara Benar: Tambahkan @Update di DAO

            // Kita pakai cara delete-insert agar tidak perlu ubah DAO (sedikit hacky tapi jalan)
            AppDatabase.getDbInstance(context).suppliersDao().deleteSupplier(item);

            item.setSupplierName(etName.getText().toString());
            item.setSupplierContact(etContact.getText().toString());

            AppDatabase.getDbInstance(context).suppliersDao().insertSupplier(item);

            notifyItemChanged(position);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, contact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvSuppName);
            contact = itemView.findViewById(R.id.tvSuppContact);
        }
    }
}