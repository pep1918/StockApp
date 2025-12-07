package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        // Gunakan layout 'expenses_recycler_sample.xml'
        View view = LayoutInflater.from(context).inflate(R.layout.expenses_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpensesModel item = list.get(position);
        holder.category.setText(item.expenseCategory);
        holder.amount.setText("Rp " + item.expenseAmount);
        holder.date.setText(item.expenseDate);
        holder.note.setText(item.expenseNote);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, amount, date, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan XML 'expenses_recycler_sample.xml' Anda
            category = itemView.findViewById(R.id.expense_category_display);
            amount = itemView.findViewById(R.id.expense_amount_display);
            date = itemView.findViewById(R.id.expense_date_display);
            note = itemView.findViewById(R.id.expense_note_display);
        }
    }
}