package com.lamvo.groupproject_flowershop.apis;

import com.lamvo.groupproject_flowershop.models.OrderDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderDetailService {
    String ORDER_DETAIL = "OrderDetailTbl";
    @GET(ORDER_DETAIL)
    Call<OrderDetail[]> getAllOrderDetails();

    @GET(ORDER_DETAIL + "/{id}")
    Call<OrderDetail> getOrderDetail(@Path("id") Object id);

    @POST(ORDER_DETAIL)
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    @PUT(ORDER_DETAIL + "/{id}")
    Call<OrderDetail> updateOrderDetail(@Path("id") Object id, @Body OrderDetail orderDetail);

    @DELETE(ORDER_DETAIL + "/{id}")
    Call<OrderDetail> deleteOrderDetail(@Path("id") Object id);
}
