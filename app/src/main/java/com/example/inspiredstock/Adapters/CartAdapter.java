package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inspiredstock.Models.CartItem;
import com.example.inspiredstock.R;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan layout 'billing_cart_item' ada di folder layout
        View view = LayoutInflater.from(context).inflate(R.layout.billing_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        // PERBAIKAN: Gunakan Getter dari ProductsModel
        holder.name.setText(item.getProduct().getProductName());

        // Hitung subtotal: Harga x Qty
        double subtotal = item.getProduct().getPrice() * item.getQuantity();
        holder.price.setText(item.getQuantity() + " x Rp " + item.getProduct().getPrice());
        holder.total.setText("Rp " + subtotal);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan file billing_cart_item.xml Anda
            name = itemView.findViewById(R.id.tvBillItemName);
            price = itemView.findViewById(R.id.tvBillItemPrice);
            total = itemView.findViewById(R.id.tvBillSubtotal);
        }
    }
}