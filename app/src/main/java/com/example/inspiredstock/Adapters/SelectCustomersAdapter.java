package com.example.inspiredstock.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.R;
import java.util.List;

public class SelectCustomersAdapter extends RecyclerView.Adapter<SelectCustomersAdapter.ViewHolder> {

    private List<CustomersModelClass> list;
    private Context context;

    public SelectCustomersAdapter(List<CustomersModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menggunakan layout row yang sudah ada
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomersModelClass item = list.get(position);

        holder.name.setText(item.getCustomerName());  // Getter
        holder.phone.setText(item.getCustomerPhone()); // Getter

        // Saat diklik, kirim data balik
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("selected_customer_name", item.getCustomerName());
            intent.putExtra("selected_customer_id", item.getId());

            // Set Result OK untuk Activity pemanggil
            ((Activity) context).setResult(Activity.RESULT_OK, intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvProductName); // Reuse ID
            phone = itemView.findViewById(R.id.tvProductStock); // Reuse ID
            itemView.findViewById(R.id.tvProductPrice).setVisibility(View.GONE); // Sembunyikan harga
        }
    }
}