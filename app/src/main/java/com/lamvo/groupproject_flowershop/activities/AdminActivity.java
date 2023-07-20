package com.lamvo.groupproject_flowershop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.AdminOrderAcitivity;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.SignInActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnChatScreen = findViewById(R.id.btnCustomerchat);
        btnChatScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminChatListActivity.class));
            }
        });

        Button btnFlowerMng = findViewById(R.id.btnFlowerMng);
        btnFlowerMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminFlowerMngActivity.class));
            }
        });
        Button btnOrder = findViewById(R.id.viewAllOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminOrderAcitivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home_admin) {
            // profile setting processor
        }
        else if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminActivity.this, SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}