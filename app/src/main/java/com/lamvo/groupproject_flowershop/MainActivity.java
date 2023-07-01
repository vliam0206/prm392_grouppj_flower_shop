package com.lamvo.groupproject_flowershop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lamvo.groupproject_flowershop.app_services.CredentialService;

public class MainActivity extends AppCompatActivity {
    private CredentialService credentialService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test get current logged in UserID
        credentialService = new CredentialService(MainActivity.this);
        long currentId = credentialService.getCurrentUserId();
        TextView txt = findViewById(R.id.txtWelcome);
        txt.setText("Welcome, userId: "+currentId);
    }
}