package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.activities.InsertUpdateFlowerActivity;
import com.lamvo.groupproject_flowershop.adapters.CustomerOrderAdapter;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.apis.OrderRepository;
import com.lamvo.groupproject_flowershop.apis.OrderService;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.dao.OrderViewDao;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.lamvo.groupproject_flowershop.models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderAcitivity extends AppCompatActivity {
    private RecyclerView orderListView;
    private ArrayList<OrderViewDao> orderArrayList;
    private CustomerOrderAdapter customerOrderAdapter;
    CredentialService credentialService;
    CustomerService customerService;
    OrderService orderService;
    long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_acitivity);
        orderListView =  findViewById(R.id.rvOrderAdmin);
        credentialService = new CredentialService(this);
        userId = credentialService.getCurrentUserId();
        customerService = CustomerRepository.getCustomerService();
        orderService = OrderRepository.getOrderService();
        orderListView.setLayoutManager(new LinearLayoutManager(this));
        customerOrderAdapter = new CustomerOrderAdapter(this);
        orderListView.setAdapter(customerOrderAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveOrders();
    }
    private void retrieveOrders(){
        Call<Order[]> orderCall = orderService.getAllOrders();
        orderCall.enqueue(new Callback<Order[]>() {
            @Override
            public void onResponse(Call<Order[]> call, Response<Order[]> response) {
                Order[] orders = response.body();
                List<Order> orderList = new ArrayList<>();
                for (int i = 0; i < orders.length ; i++) {
                    orderList.add(orders[i]);
                }
                List<OrderViewDao> orderViewDaos = new ArrayList<>();
                for (Order o:orderList) {
                    OrderViewDao orderViewDao = new OrderViewDao(o.getOrderStatus(),o.getTotal(),"CUSTOMER");
                    Call<Customer> customerCall = customerService.getCustomer(o.getCustomerId());
                    orderViewDaos.add(orderViewDao);
                }
                List<OrderViewDao> orderViewDaos1 = orderViewDaos;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerOrderAdapter.setOrderList(orderViewDaos);
                        customerOrderAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<Order[]> call, Throwable t) {

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
            startActivity(new Intent(AdminOrderAcitivity.this, SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}