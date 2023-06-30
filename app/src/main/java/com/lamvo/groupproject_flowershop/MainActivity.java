package com.lamvo.groupproject_flowershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.apis.OderDetailRepository;
import com.lamvo.groupproject_flowershop.apis.OrderDetailService;
import com.lamvo.groupproject_flowershop.apis.OrderRepository;
import com.lamvo.groupproject_flowershop.apis.OrderService;
import com.lamvo.groupproject_flowershop.models.Customer;
import com.lamvo.groupproject_flowershop.models.Flower;
import com.lamvo.groupproject_flowershop.models.Order;
import com.lamvo.groupproject_flowershop.models.OrderDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class nay dung de test du lieu ket noi tu MockAPI
 **/
public class MainActivity extends AppCompatActivity {
    CustomerService customerService;
    FlowerService flowerService;
    OrderService orderService;
    OrderDetailService orderDetailService;
    ListView lvTestData;
    ArrayList<String> listDataTest;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilize();

        // test customer table
//        showCustomerData();

        // test flower table
//        showFlowerData();

        // test order table
//        showOrderData();
        // test order detail table
        showOrderDetailData();
    }

    private void initilize() {
        lvTestData = findViewById(R.id.lvTestData);
        customerService = CustomerRepository.getCustomerService();
        flowerService = FlowerRepository.getFlowerService();
        orderService = OrderRepository.getOrderService();
        orderDetailService = OderDetailRepository.getOrderDetailService();
    }

    private void showCustomerData() {
        try {
            Call<Customer[]> call = customerService.getAllCustomers();
            call.enqueue(new Callback<Customer[]>() {
                @Override
                public void onResponse(Call<Customer[]> call, Response<Customer[]> response) {
                    Customer[] customers = response.body();
                    if (customers == null) {
                        return;
                    }
                    listDataTest = new ArrayList<>();
                    for (Customer customer: customers) {
                        listDataTest.add(customer.getCustomerName());
                    }
                    adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listDataTest);
                    lvTestData.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<Customer[]> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Load data test failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Load data test error", e.getMessage());
        }
    }
    private void showFlowerData() {
        try {
            Call<Flower[]> call = flowerService.getAllFlowers();
            call.enqueue(new Callback<Flower[]>() {
                @Override
                public void onResponse(Call<Flower[]> call, Response<Flower[]> response) {
                    Flower[] flowers = response.body();
                    if (flowers == null) {
                        return;
                    }
                    listDataTest = new ArrayList<>();
                    for (Flower flower: flowers) {
                        listDataTest.add(flower.getFlowerName()+", " + flower.getUnitPrice());
                    }
                    adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listDataTest);
                    lvTestData.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<Flower[]> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Load data test failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Load data test error", e.getMessage());
        }
    }
    private void showOrderData() {
        try {
            Call<Order[]> call = orderService.getAllOrders();
            call.enqueue(new Callback<Order[]>() {
                @Override
                public void onResponse(Call<Order[]> call, Response<Order[]> response) {
                    Toast.makeText(MainActivity.this, "Load order data test successfully!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Order[]> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Load data test failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Load data test error", e.getMessage());
        }
    }
    private void showOrderDetailData() {
        try {
            Call<OrderDetail[]> call = orderDetailService.getAllOrderDetails();
            call.enqueue(new Callback<OrderDetail[]>() {
                @Override
                public void onResponse(Call<OrderDetail[]> call, Response<OrderDetail[]> response) {
                    Toast.makeText(MainActivity.this, "Load order detail data test successfully!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<OrderDetail[]> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Load data test failed!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Load data test error", e.getMessage());
        }
    }
}