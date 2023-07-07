package com.lamvo.groupproject_flowershop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.SignInActivity;
import com.lamvo.groupproject_flowershop.TestConnectDBApiActivity;
import com.lamvo.groupproject_flowershop.adapters.CustomerAdapter;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.models.Customer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChatListActivity extends AppCompatActivity {
    ListView lvCustomers;
    ArrayList<Customer> customerList;
    ArrayList<String> listDataTest;
    ArrayAdapter adapter;
    CustomerAdapter customerAdapter;
    CustomerService customerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat_list);

        lvCustomers = findViewById(R.id.lvCustomerList);
        customerService = CustomerRepository.getCustomerService();
        LoadCustomerList();

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String senderRoom = "";
                String receiveRoom = "";
            }
        });
    }

    private void LoadCustomerList() {
        try {
            Call<Customer[]> call = customerService.getAllCustomers();
            call.enqueue(new Callback<Customer[]>() {
                @Override
                public void onResponse(Call<Customer[]> call, Response<Customer[]> response) {
                    Customer[] customers = response.body();
                    if (customers == null) {
                        return;
                    }
                    customerList = new ArrayList<>();
                    for (Customer customer: customers) {
                        customerList.add(customer);
                    }
                    customerAdapter = new CustomerAdapter(AdminChatListActivity.this, R.layout.customer_list_item, customerList);
                    lvCustomers.setAdapter(customerAdapter);
                }

                @Override
                public void onFailure(Call<Customer[]> call, Throwable t) {
                    Toast.makeText(AdminChatListActivity.this, "Load data test failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Load data test error", e.getMessage());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_profile) {
            // profile setting processor
        }
        else if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminChatListActivity.this, SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}