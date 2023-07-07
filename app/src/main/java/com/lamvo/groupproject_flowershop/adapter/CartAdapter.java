package com.lamvo.groupproject_flowershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Flower> flowerList;

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,
                            viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        holder.flowerName.setText(flowerList.get(position).getFlowerName());
        String unitPrice = String.valueOf(flowerList.get(position).getUnitPrice());
        holder.unitPrice.setText(unitPrice);
    }

    public void setFlowerList(List<Flower> flowerList) {
        this.flowerList = flowerList;
        notifyDataSetChanged();
    }

    public List<Flower> getFlowerList() {
        return flowerList;
    }

    @Override
    public int getItemCount() {
        if (flowerList == null) {
            return 0;
        }
        return flowerList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView flowerName, unitPrice;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            flowerName = itemView.findViewById(R.id.textViewFlowerName);
            unitPrice = itemView.findViewById(R.id.textViewUnitPrice);
        }
    }
}