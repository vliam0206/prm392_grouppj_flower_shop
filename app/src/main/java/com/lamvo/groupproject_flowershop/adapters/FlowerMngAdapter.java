package com.lamvo.groupproject_flowershop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.activities.AdminFlowerMngActivity;
import com.lamvo.groupproject_flowershop.activities.InsertUpdateFlowerActivity;
import com.lamvo.groupproject_flowershop.constants.AppConstants;
import com.lamvo.groupproject_flowershop.models.Flower;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class FlowerMngAdapter extends BaseAdapter {
    private ArrayList<Flower> flowerList;
    private int layout;
    private Context context;

    public FlowerMngAdapter(ArrayList<Flower> flowerList, int layout, Context context) {
        this.flowerList = flowerList;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return flowerList.size();
    }

    @Override
    public Object getItem(int position) {
        return flowerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return flowerList.get(position).getId();
    }
    public class ViewHolder {
        ImageView imgFLower, imgEdit;
        TextView txtName, txtPrice, txtUnitInStock, txtDescribe;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.imgFLower = view.findViewById(R.id.imgFlower);
            holder.imgEdit = view.findViewById(R.id.imgEditFlower);
            holder.txtName = view.findViewById(R.id.txtFlowerName);
            holder.txtDescribe = view.findViewById(R.id.txtDescribeFlower);
            holder.txtPrice = view.findViewById(R.id.txtFlowerPrice);
            holder.txtUnitInStock = view.findViewById(R.id.txtUnitInStock);


        Flower flower = flowerList.get(position);
        holder.txtName.setText(flower.getFlowerName());
        holder.txtPrice.setText(flower.getUnitPrice()+"");
        holder.txtDescribe.setText(flower.getDescription());
        holder.txtUnitInStock.setText(flower.getUnitInStock()+"");
        if (!flower.getImageUrl().isEmpty()) {
            Picasso.get().load(flower.getImageUrl()).into(holder.imgFLower);
        }

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsertUpdateFlowerActivity.class);
                intent.putExtra(AppConstants.UPDATED_FLOWER, (Serializable) flower);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
