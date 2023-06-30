package com.lamvo.groupproject_flowershop.apis;

import com.lamvo.groupproject_flowershop.models.Flower;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FlowerService {
    String FLOWERS = "FlowerTbl";
    @GET(FLOWERS)
    Call<Flower[]> getAllFlowers();

    @GET(FLOWERS + "/{id}")
    Call<Flower> getFlower(@Path("id") Object id);

    @POST(FLOWERS)
    Call<Flower> createFlower(@Body Flower customer);

    @PUT(FLOWERS + "/{id}")
    Call<Flower> updateFlower(@Path("id") Object id, @Body Flower customer);

    @DELETE(FLOWERS + "/{id}")
    Call<Flower> deleteFlower(@Path("id") Object id);
}
