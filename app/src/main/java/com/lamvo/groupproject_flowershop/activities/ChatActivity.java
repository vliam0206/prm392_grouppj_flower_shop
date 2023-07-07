package com.lamvo.groupproject_flowershop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamvo.groupproject_flowershop.FlowersList;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.adapters.MessageAdapter;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.constants.AppConstants;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.lamvo.groupproject_flowershop.models.MessageModel;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    CustomerService customerService;
    ListView lvChats;
    EditText etMessage;
    ImageButton btnSendChat;
    String receiverUid;
    long receiverId;
    Intent intent;
    DatabaseReference senderDatabaseReference, recieverDatabaseReference;
    String senderRoom, recieverRoom;
    MessageAdapter messageAdapter;
    ArrayList<MessageModel> messageList;
    Customer mCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intent = getIntent();
        if (intent == null) {
            return;
        }
        receiverId = intent.getLongExtra(AppConstants.USER_ID, -1);
        receiverUid = intent.getStringExtra(AppConstants.USER_UID);

        getCustomer();

        initilize();

        senderRoom = FirebaseAuth.getInstance().getUid() + receiverUid;
        recieverRoom = receiverUid + FirebaseAuth.getInstance().getUid();

        senderDatabaseReference = FirebaseDatabase.getInstance()
                .getReference(AppConstants.CHAT_DB).child(senderRoom);
        recieverDatabaseReference = FirebaseDatabase.getInstance()
                .getReference(AppConstants.CHAT_DB).child(recieverRoom);

        senderDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapShot : snapshot.getChildren()) {
                    MessageModel messageModel = dataSnapShot.getValue(MessageModel.class);
                    messageList.add(messageModel);
                }
                messageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnSendChat.setOnClickListener(this);

    }
    private void getCustomer() {
        Call<Customer> call = customerService.getCustomer(receiverId);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                if (customer == null) {
                    return;
                }
                mCustomer = customer;
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.d("Get Receiver chat", t.getMessage());
            }
        });
    }
    private void initilize() {
        customerService = CustomerRepository.getCustomerService();
        lvChats = findViewById(R.id.lvChatScreen);
        etMessage = findViewById(R.id.etChatContent);
        btnSendChat = findViewById(R.id.btnSendChat);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(ChatActivity.this,
                R.layout.message_row_right,
                R.layout.message_row_left,
                messageList,
                mCustomer.getAvatar());
        lvChats.setAdapter(messageAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendChat) {
            String message = etMessage.getText().toString();
            etMessage.setText("");
            if (message.trim().isEmpty()) {
                return;
            }
            // send message
            String msgId = UUID.randomUUID().toString();

            MessageModel msgModel = new MessageModel(msgId, FirebaseAuth.getInstance().getUid(), message);

            senderDatabaseReference
                    .child(msgId)
                    .setValue(msgModel);
            recieverDatabaseReference
                    .child(msgId)
                    .setValue(msgModel);

            messageList.add(msgModel);
            messageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (currentEmail.equals(AppConstants.ADMIN_ACCOUNT)) {
            getMenuInflater().inflate(R.menu.admin_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.sub_menu, menu);
        }
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