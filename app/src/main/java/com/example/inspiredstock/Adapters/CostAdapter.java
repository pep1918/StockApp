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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CostModel item = list.get(position);

        // Akses langsung (jika public) atau Getter
        holder.name.setText(item.costName);
        holder.desc.setText(item.description);
        holder.amount.setText("Rp " + item.amount);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName);
            desc = itemView.findViewById(R.id.tvProductStock);
            amount = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}