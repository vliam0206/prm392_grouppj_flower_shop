package com.lamvo.groupproject_flowershop;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.FlowersList;
import com.lamvo.groupproject_flowershop.MainActivity;
import com.squareup.picasso.Picasso;

import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private FlowersList context;
    private int layout;
    private ArrayList<Flower> flowerArrayList;

    public ListAdapter(FlowersList context, int layout, ArrayList<Flower> flowerArrayList) {
        this.context = context;
        this.layout = layout;
        this.flowerArrayList = flowerArrayList;
    }

    @Override
    public int getCount() {
        return flowerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder {
        TextView tvName, tvDescription, tvPrice;
        ImageView imgAddToCart, imgThubnail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvDescription = convertView.findViewById(R.id.tvShortDescription);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            holder.imgAddToCart = convertView.findViewById(R.id.imageviewAddToCart);
            holder.imgThubnail = convertView.findViewById(R.id.imgThubnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Flower flower = flowerArrayList.get(position);
        holder.tvName.setText(flower.getFlowerName());
        holder.tvDescription.setText(flower.getDescription());
        holder.tvPrice.setText("Price: " + flower.getUnitPrice() + " VNĐ");
        Picasso.get().load(flower.getImageUrl()).into(holder.imgThubnail);

        //Event handler for add a flower to cart
        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                Log.d("myTag", "onClick() returned: " + flowerArrayList.get(position).getId());
//                intent.putExtra("choosenFlower", flowerArrayList.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}