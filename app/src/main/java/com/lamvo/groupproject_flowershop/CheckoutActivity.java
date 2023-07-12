package com.lamvo.groupproject_flowershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lamvo.groupproject_flowershop.apis.OderDetailRepository;
import com.lamvo.groupproject_flowershop.apis.OrderDetailService;
import com.lamvo.groupproject_flowershop.apis.OrderRepository;
import com.lamvo.groupproject_flowershop.apis.OrderService;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.db.AppDatabase;
import com.lamvo.groupproject_flowershop.db.AppExecutors;
import com.lamvo.groupproject_flowershop.models.Cart;
import com.lamvo.groupproject_flowershop.models.Order;
import com.lamvo.groupproject_flowershop.models.OrderDetail;
import com.lamvo.groupproject_flowershop.models.OrderStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    Button btnPayAfter;
    long userId;
    CredentialService credentialService;
    private AppDatabase database;
    private OrderService orderService;
    private OrderDetailService orderDetailService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btnPayAfter = findViewById(R.id.checkout1);
        credentialService = new CredentialService(this);
        userId = credentialService.getCurrentUserId();
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-database").build();
        orderService = OrderRepository.getOrderService();
        orderDetailService = OderDetailRepository.getOrderDetailService();
        btnPayAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutAfter();
                deleteCart();
            }
        });
    }
    private void checkoutAfter(){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Cart> myCart = database.cartDao().getAllFlowersByUserID(userId);
                List<Order> orderList = new ArrayList<>();
                Call<Order[]> call = orderService.getAllOrders();
                call.enqueue(new Callback<Order[]>() {
                    @Override
                    public void onResponse(Call<Order[]> call, Response<Order[]> response) {
                         if(response.body() == null){
                             int maxId = 0;
                             double total = 0;
                             for (Cart c:myCart) {
                                 total += c.getUnitPrice() *  c.getQuantity();
                             }
                            Order order = new Order(maxId + 1, new Date(),new Date(),total, OrderStatus.UNPAID.toString());
                            Call<Order> orderCreateCall = orderService.createOrder(order);
                            orderCreateCall.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    if(response.body() != null){
                                        for (Cart cart:myCart) {
                                            OrderDetail orderDetail = new OrderDetail(1,cart.getIdFlower(),cart.getUnitPrice(),cart.getQuantity());
                                            Call<OrderDetail> orderDetailCall = orderDetailService.createOrderDetail(orderDetail);
                                            orderDetailCall.enqueue(new Callback<OrderDetail>() {
                                                @Override
                                                public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                                                     if(response.body() != null){
                                                         boolean bool = true;
                                                         try {
                                                             AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                                                                              @Override
                                                                                                              public void run() {
                                                                                                                  database.cartDao().delete(cart);
                                                                                                              }
                                                                                                          });
                                                         } catch (Exception e){
                                                             Log.e("e",e.getMessage());
                                                         }
                                                     }
                                                }

                                                @Override
                                                public void onFailure(Call<OrderDetail> call, Throwable t) {

                                                }
                                            });
                                        }
                                        Toast.makeText(CheckoutActivity.this,"Successfully",Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(CheckoutActivity.this,ViewCartActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {

                                }
                            });
                         } else {
                             List<Order> orders = new ArrayList<>();
                             int length = response.body().length;
                             Order[] orders1 = response.body();
                             for (int i = 0; i < response.body().length; i++) {
                                 orders.add(response.body()[i]);
                             }
                             long idMax = orders.stream().max(Comparator.comparingLong(o -> o.getId())).get().getId();
                             double total = 0;
                             for (Cart c:myCart) {
                                 total += c.getUnitPrice() *  c.getQuantity();
                             }
                             Order order = new Order(idMax + 1,userId, new Date(),new Date(),total, OrderStatus.UNPAID.toString());
                             final long orderId = idMax + 1;
                             Call<Order> orderCreateCall = orderService.createOrder(order);
                             orderCreateCall.enqueue(new Callback<Order>() {
                                 @Override
                                 public void onResponse(Call<Order> call, Response<Order> response) {
                                     if(response.body() != null){
                                         for (Cart cart:myCart) {
                                             OrderDetail orderDetail = new OrderDetail(orderId,cart.getIdFlower(),cart.getUnitPrice(),cart.getQuantity());
                                             Call<OrderDetail> orderDetailCall = orderDetailService.createOrderDetail(orderDetail);
                                             orderDetailCall.enqueue(new Callback<OrderDetail>() {
                                                 @Override
                                                 public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                                                     if(response.body() != null){
                                                         boolean bool = true;
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(Call<OrderDetail> call, Throwable t) {

                                                 }
                                             });
                                         }
                                         Toast.makeText(CheckoutActivity.this,"Successfully",Toast.LENGTH_SHORT);
                                         Intent intent = new Intent(CheckoutActivity.this,ViewCartActivity.class);
                                         startActivity(intent);
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Order> call, Throwable t) {

                                 }
                             });

                         }
                    }

                    @Override
                    public void onFailure(Call<Order[]> call, Throwable t) {

                    }
                });
            }
        });

    }
    private void deleteCart(){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Cart> myCart = database.cartDao().getAllFlowersByUserID(userId);
                for (Cart mCart:myCart) {
                    try {
                        database.cartDao().delete(mCart);
                    } catch (Exception e){
                        Log.e("tag",e.getMessage());
                    }
                }

            }
        });
    }
}