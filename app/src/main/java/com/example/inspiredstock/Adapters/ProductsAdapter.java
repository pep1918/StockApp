package com.example.inspiredstock.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inspiredstock.Database.AppDatabase;
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.R;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    private List<ProductsModel> productsList;

    public ProductsAdapter(List<ProductsModel> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductsModel item = productsList.get(position);

        // PERBAIKAN: GUNAKAN GETTER
        holder.productName.setText(item.getProductName());
        holder.productQty.setText("Stok: " + item.getStock());
        holder.productPrice.setText("Rp " + item.getPrice());

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Glide.with(context).load(Uri.parse(item.getImagePath())).into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.item);
        }

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Hapus " + item.getProductName() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        AppDatabase.getDbInstance(context).productsDao().deleteProduct(item);
                        productsList.remove(position);
                        notifyItemRemoved(position);
                    }).show();
            return true;
        });
    }

    @Override
    public int getItemCount() { return productsList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQty, productPrice;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productQty = itemView.findViewById(R.id.tvProductStock);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productImage = itemView.findViewById(R.id.imgProduct);
        }
    }
}