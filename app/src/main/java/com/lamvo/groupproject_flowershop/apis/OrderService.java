package com.lamvo.groupproject_flowershop.apis;

import com.lamvo.groupproject_flowershop.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderService {
    String ORDERS = "OrderTbl";
    @GET(ORDERS)
    Call<Order[]> getAllOrders();

    @GET(ORDERS + "/{id}")
    Call<Order> getOrder(@Path("id") Object id);

    @POST(ORDERS)
    Call<Order> createOrder(@Body Order order);

    @PUT(ORDERS + "/{id}")
    Call<Order> updateOrder(@Path("id") Object id, @Body Order order);

    @DELETE(ORDERS + "/{id}")
    Call<Order> deleteOrder(@Path("id") Object id);
}
