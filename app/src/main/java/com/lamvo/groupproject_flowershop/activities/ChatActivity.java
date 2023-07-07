package com.lamvo.groupproject_flowershop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lamvo.groupproject_flowershop.FlowersList;
import com.lamvo.groupproject_flowershop.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(ChatActivity.this, FlowersList.class));
        }
        else if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
        }
        else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
        }
        return super.onOptionsItemSelected(item);
    }
}