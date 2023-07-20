package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.adapters.CartAdapter;
import com.lamvo.groupproject_flowershop.adapters.CustomerAdapter;
import com.lamvo.groupproject_flowershop.adapters.CustomerOrderAdapter;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.apis.OrderRepository;
import com.lamvo.groupproject_flowershop.apis.OrderService;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.dao.OrderViewDao;
import com.lamvo.groupproject_flowershop.models.Cart;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.lamvo.groupproject_flowershop.models.Flower;
import com.lamvo.groupproject_flowershop.models.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualOrderActivity extends AppCompatActivity {
    private RecyclerView orderListView;
    private ArrayList<OrderViewDao> orderArrayList;
    private CustomerOrderAdapter customerOrderAdapter;
    CredentialService credentialService;
    CustomerService customerService;
    OrderService orderService;
    long userId;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_order);
        orderListView =  findViewById(R.id.rvOrder);
        credentialService = new CredentialService(this);
        userId = credentialService.getCurrentUserId();
        customerService = CustomerRepository.getCustomerService();
        orderService = OrderRepository.getOrderService();
        orderListView.setLayoutManager(new LinearLayoutManager(this));
        customerOrderAdapter = new CustomerOrderAdapter(this);
        orderListView.setAdapter(customerOrderAdapter);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_order);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.menu_home){
                startActivity(new Intent(IndividualOrderActivity.this, FlowersList.class));
            }
            if(item.getItemId()==R.id.menu_order){
                startActivity(new Intent(IndividualOrderActivity.this, IndividualOrderActivity.class));
            }
            if(item.getItemId()==R.id.menu_map){
                startActivity(new Intent(IndividualOrderActivity.this, ViewMapActivity.class));
            }
            return true;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
            startActivity(new Intent(IndividualOrderActivity.this, ViewCartActivity.class));
        } else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(IndividualOrderActivity.this, SignInActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveOrders();

    }
    private void retrieveOrders(){
        Call<Customer> customerCall = customerService.getCustomer(userId);
        customerCall.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer = response.body();
                String customerName = customer.getCustomerName();
                Call<List<Order>> callOrder = orderService.getOrdersByCustomerId(userId);
                callOrder.enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        List<Order> orders = response.body();
                        List<OrderViewDao> orderViewDaos = new ArrayList<>();
                        for (Order o:orders) {
                             OrderViewDao orderViewDao = new OrderViewDao(o.getOrderStatus(),o.getTotal(),customerName);
                             orderViewDaos.add(orderViewDao);
                        }
                        List<OrderViewDao> x = orderViewDaos;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customerOrderAdapter.setOrderList(orderViewDaos);
                                customerOrderAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }
}