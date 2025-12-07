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
    private List<CartItem> cartList;

    public CartAdapter(Context context, List<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kita "pinjam" layout expenses_recycler_sample.xml
        View view = LayoutInflater.from(context).inflate(R.layout.expenses_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        // Mapping Data ke View
        holder.name.setText(item.product.productName);           // Nama Barang
        holder.price.setText("Rp " + String.format("%.0f", item.subtotal)); // Total Harga (Harga x Qty)
        holder.qty.setText(item.qty + " pcs");                   // Jumlah Beli

        // Sembunyikan field note karena tidak dipakai di keranjang
        holder.note.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, qty, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pastikan ID ini SESUAI dengan expenses_recycler_sample.xml
            name = itemView.findViewById(R.id.expense_category_display);
            price = itemView.findViewById(R.id.expense_amount_display);
            qty = itemView.findViewById(R.id.expense_date_display);
            note = itemView.findViewById(R.id.expense_note_display);
        }
    }
}