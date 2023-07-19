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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamvo.groupproject_flowershop.FlowersList;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.SignInActivity;
import com.lamvo.groupproject_flowershop.ViewCartActivity;
import com.lamvo.groupproject_flowershop.adapters.MessageAdapter;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.constants.AppConstants;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.lamvo.groupproject_flowershop.models.MessageModel;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    CustomerService customerService;
    ListView lvChats;
    EditText etMessage;
    ImageView btnSendChat;
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

        lvChats = findViewById(R.id.lvChatScreen);
        etMessage = findViewById(R.id.etChatContent);
        btnSendChat = findViewById(R.id.btnSendChat);

        intent = getIntent();
        if (intent == null) {
            return;
        }
        receiverId = intent.getLongExtra(AppConstants.USER_ID, -1);
        receiverUid = intent.getStringExtra(AppConstants.USER_UID);

        customerService = CustomerRepository.getCustomerService();
        getReceiver(receiverId);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(ChatActivity.this,
                R.layout.message_row_right,
                R.layout.message_row_left,
                messageList);
        lvChats.setAdapter(messageAdapter);

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
                messageAdapter.setReceiver(mCustomer);
                messageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnSendChat.setOnClickListener(this);
    }
    private void getReceiver(long id) {
        Call<Customer> call = customerService.getCustomer(id);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendChat) {
            String message = etMessage.getText().toString();
            etMessage.setText("");
            if (message.trim().isEmpty()) {
                return;
            }
            // generate message id by current date time
            // Get the current date and time
            Date currentDate = new Date();
            // Define the desired format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // Format the current date and time using the SimpleDateFormat
            String formattedDateTime = dateFormat.format(currentDate);
            String msgId = formattedDateTime;

            // send message
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
        else if (item.getItemId() == R.id.menu_home_admin) {
            startActivity(new Intent(ChatActivity.this, AdminActivity.class));
        }
        else if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
            startActivity(new Intent(ChatActivity.this, ViewCartActivity.class));
        }
        else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ChatActivity.this, SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}