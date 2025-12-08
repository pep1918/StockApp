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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ExpensesModel;
import com.example.inspiredstock.R;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {

    private List<ExpensesModel> list;
    private Context context;

    public ExpensesAdapter(List<ExpensesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kita menggunakan layout row_product_item agar tampilan seragam
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ExpensesModel item = list.get(position);

        // --- TAMPILKAN DATA (Gunakan Getter) ---
        holder.category.setText(item.getExpenseName()); // Nama Pengeluaran
        holder.amount.setText("Rp " + item.getAmount()); // Jumlah Uang

        // Menampilkan Tanggal (jika ada) atau Note di slot "Stok"
        String info = item.getDate();
        if (item.getNote() != null && !item.getNote().isEmpty()) {
            info += " (" + item.getNote() + ")";
        }
        holder.date.setText(info);

        // Ganti icon agar sesuai konteks expense
        if (holder.icon != null) {
            holder.icon.setImageResource(R.drawable.ex); // Pastikan drawable 'ex' ada
            holder.icon.setColorFilter(context.getResources().getColor(android.R.color.holo_red_dark));
            holder.amount.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }

        // --- FITUR EDIT (KLIK BIASA) ---
        holder.itemView.setOnClickListener(v -> {
            showEditDialog(item, position);
        });

        // --- FITUR DELETE (KLIK TAHAN) ---
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Hapus Pengeluaran")
                    .setMessage("Hapus data " + item.getExpenseName() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        // Hapus dari Database
                        AppDatabase.getDbInstance(context).expensesDao().deleteExpense(item);
                        // Hapus dari List Tampilan
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                        Toast.makeText(context, "Data Terhapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
            return true;
        });
    }

    private void showEditDialog(ExpensesModel item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_expense_input, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Hubungkan dengan ID di dialog_expense_input.xml
        EditText etDesc = view.findViewById(R.id.etExpDesc);
        EditText etAmount = view.findViewById(R.id.etExpAmount);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        TextView tvTitle = view.findViewById(R.id.tvTitle);

        // Isi data lama ke form
        if (tvTitle != null) tvTitle.setText("EDIT PENGELUARAN");
        etDesc.setText(item.getExpenseName());
        etAmount.setText(String.valueOf(item.getAmount()).replace(".0", ""));
        btnSave.setText("UPDATE");

        btnSave.setOnClickListener(v -> {
            String newDesc = etDesc.getText().toString();
            String newAmountStr = etAmount.getText().toString();

            if (newAmountStr.isEmpty()) {
                Toast.makeText(context, "Nominal wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update data di database (Hapus lama -> Insert baru adalah cara termudah jika tidak ada @Update di DAO)
            AppDatabase db = AppDatabase.getDbInstance(context);

            // 1. Hapus data lama
            db.expensesDao().deleteExpense(item);


            item.setExpenseName(newDesc);
            item.setAmount(Double.parseDouble(newAmountStr));


            db.expensesDao().insertExpense(item);


            notifyItemChanged(position);
            dialog.dismiss();
            Toast.makeText(context, "Data Diperbarui", Toast.LENGTH_SHORT).show();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, amount, date;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.tvProductName);
            amount = itemView.findViewById(R.id.tvProductPrice);
            date = itemView.findViewById(R.id.tvProductStock);
            icon = itemView.findViewById(R.id.imgProduct);
        }
    }
}