package com.lamvo.groupproject_flowershop.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.models.MessageModel;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private int layoutRight;
    private int layoutLeft;
    private ArrayList<MessageModel> messageList;
    private String avatar;

    public MessageAdapter(Context context, int layoutRight, int layoutLeft,
                          ArrayList<MessageModel> messageList,
                          String avatar) {
        this.context = context;
        this.messageList = messageList;
        this.layoutRight = layoutRight;
        this.layoutLeft = layoutLeft;
        this.avatar = avatar;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }
    public String getItemStringId(int position) {
        return messageList.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        MessageModel message = (MessageModel) getItem(position);

        if (message.getSenderId().equals(FirebaseAuth.getInstance().getUid())) {
            view = inflater.inflate(layoutRight, null);
        } else {
            view = inflater.inflate(layoutLeft, null);
//            ImageView imgAvatar =
        }

        TextView txtMessage = view.findViewById(R.id.txtMessage);
        txtMessage.setText(message.getMessage());

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
