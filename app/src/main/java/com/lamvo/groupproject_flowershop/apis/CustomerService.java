package com.lamvo.groupproject_flowershop.apis;

import com.lamvo.groupproject_flowershop.models.Customer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerService {
    String CUSTOMERS = "CustomerTbl";
    @GET(CUSTOMERS)
    Call<Customer[]> getAllCustomers();

    @GET(CUSTOMERS + "/{id}")
    Call<Customer> getCustomer(@Path("id") Object id);

    @GET(CUSTOMERS)
    Call<Customer[]> getCustomerByEmail(@Query("email") String email);

    @POST(CUSTOMERS)
    Call<Customer> createCustomer(@Body Customer customer);

    @PUT(CUSTOMERS + "/{id}")
    Call<Customer> updateCustomer(@Path("id") Object id, @Body Customer customer);

    @DELETE(CUSTOMERS + "/{id}")
    Call<Customer> deleteCustomer(@Path("id") Object id);
}
