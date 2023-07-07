package com.lamvo.groupproject_flowershop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.activities.AdminChatListActivity;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    private AdminChatListActivity context;
    private int layout;
    private ArrayList<Customer> customerList;

    public CustomerAdapter(AdminChatListActivity context, int layout, ArrayList<Customer> customerList) {
        this.context = context;
        this.layout = layout;
        this.customerList = customerList;
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customerList.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // bindings view
        ImageView avatar = view.findViewById(R.id.imgAvatar);
        TextView cusName = view.findViewById(R.id.txtCustomerName);
        TextView cusEmail = view.findViewById(R.id.txtCustomerEmail);

        Customer customer =(Customer)getItem(position);
        Picasso.get().load(customer.getAvatar()).into(avatar);
        cusName.setText(customer.getCustomerName());
        cusEmail.setText(customer.getEmail());
        return view;
    }
}
