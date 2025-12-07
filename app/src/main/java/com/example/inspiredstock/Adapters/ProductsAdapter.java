package com.example.inspiredstock.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<ProductsModel> list;

    public ProductsAdapter(Context context, List<ProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<ProductsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menggunakan layout products_recycler_sample.xml yang baru (ada tombol hapus)
        View view = LayoutInflater.from(context).inflate(R.layout.products_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsModel item = list.get(position);

        holder.productName.setText(item.productName);
        holder.productQty.setText("Stok: " + item.productQuantity);
        holder.productPrice.setText("Rp " + item.productPrice);

        // Load Gambar
        if (item.imagePath != null && !item.imagePath.isEmpty()) {
            try {
                Glide.with(context).load(Uri.parse(item.imagePath)).into(holder.productImage);
            } catch (Exception e) {
                holder.productImage.setImageResource(R.drawable.item);
            }
        } else {
            holder.productImage.setImageResource(R.drawable.item);
        }

        // --- LOGIKA HAPUS ---
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Munculkan Dialog Konfirmasi
                new AlertDialog.Builder(context)
                        .setTitle("Hapus Produk")
                        .setMessage("Apakah Anda yakin ingin menghapus " + item.productName + "?")
                        .setPositiveButton("Ya, Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 1. Hapus dari Database Room
                                AppDatabase.getDbInstance(context).productsDao().deleteProduct(item);

                                // 2. Hapus dari List Tampilan
                                list.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());

                                Toast.makeText(context, "Produk Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQty, productPrice;
        ImageView productImage;
        ImageButton deleteBtn; // Tombol Hapus

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name_display);
            productQty = itemView.findViewById(R.id.product_qty_display);
            productPrice = itemView.findViewById(R.id.product_price_display);
            productImage = itemView.findViewById(R.id.product_image_display);
            deleteBtn = itemView.findViewById(R.id.btn_delete_product); // ID dari XML
        }
    }
}