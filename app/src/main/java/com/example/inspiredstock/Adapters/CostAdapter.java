package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.CostModel;
import com.example.inspiredstock.R;
import java.util.List;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.ViewHolder> {
    private Context context;
    private List<CostModel> list;

    public CostAdapter(Context context, List<CostModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<CostModel> list) { this.list = list; notifyDataSetChanged(); }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Bisa pakai layout sample expenses biar hemat file xml
        View view = LayoutInflater.from(context).inflate(R.layout.expenses_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CostModel item = list.get(position);
        holder.name.setText(item.costName);
        holder.amount.setText("Rp " + item.costAmount);
        holder.desc.setText(item.costDescription);
    }

    @Override public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, amount, desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID xml
            name = itemView.findViewById(R.id.expense_category_display);
            amount = itemView.findViewById(R.id.expense_amount_display);
            desc = itemView.findViewById(R.id.expense_note_display);
        }
    }
}