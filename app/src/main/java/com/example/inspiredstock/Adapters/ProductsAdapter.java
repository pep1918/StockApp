package com.example.inspiredstock.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.R;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<ProductsModel> productList;

    public ProductsAdapter(Context context, List<ProductsModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    // Method untuk update data realtime saat ada perubahan
    public void setList(List<ProductsModel> list) {
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_recycler_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsModel product = productList.get(position);

        holder.productName.setText(product.productName);
        holder.productQty.setText("Qty: " + product.productQuantity);
        holder.productPrice.setText("Rp " + product.productPrice);

        // Load Gambar dari Path Lokal
        if (product.imagePath != null && !product.imagePath.isEmpty()) {
            Glide.with(context).load(Uri.parse(product.imagePath)).into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.products_icon); // Gambar default
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQty, productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pastikan ID ini sesuai dengan 'products_recycler_sample.xml' Anda
            productName = itemView.findViewById(R.id.product_name_display);
            productQty = itemView.findViewById(R.id.product_qty_display);
            productPrice = itemView.findViewById(R.id.product_price_display);
            productImage = itemView.findViewById(R.id.product_image_display);
        }
    }
}