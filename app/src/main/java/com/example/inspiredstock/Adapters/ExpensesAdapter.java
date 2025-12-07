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
import com.example.inspiredstock.Models.ExpensesModel;
import com.example.inspiredstock.R;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {

    private Context context;
    private List<ExpensesModel> list;

    public ExpensesAdapter(Context context, List<ExpensesModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<ExpensesModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expenses_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpensesModel item = list.get(position);
        holder.category.setText(item.expenseCategory);
        holder.amount.setText("Rp " + item.expenseAmount);
        holder.date.setText(item.expenseDate);

        // LOGIKA HAPUS EXPENSE
        holder.deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Pengeluaran")
                    .setMessage("Hapus data pengeluaran " + item.expenseCategory + "?")
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).expensesDao().deleteExpense(item);
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                        Toast.makeText(context, "Data Dihapus", Toast.LENGTH_SHORT).show();
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
        TextView category, amount, date;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.expense_category_display);
            amount = itemView.findViewById(R.id.expense_amount_display);
            date = itemView.findViewById(R.id.expense_date_display);
            deleteBtn = itemView.findViewById(R.id.btn_delete_expense); // ID dari XML expenses
        }
    }
}