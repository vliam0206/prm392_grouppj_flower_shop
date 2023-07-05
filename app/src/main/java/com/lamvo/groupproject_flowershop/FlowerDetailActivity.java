package com.lamvo.groupproject_flowershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.models.Flower;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;

public class FlowerDetailActivity extends AppCompatActivity {
    FlowerService flowerService;
    ImageView ivFlower;
    TextView tvFlowerName, tvFlowerDescription, tvFlowerPrice;
    EditText etQuantity;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_detail);
        ivFlower = (ImageView) findViewById(R.id.ivFlower);
        tvFlowerName = (TextView) findViewById(R.id.tvFlowerName);
        tvFlowerDescription = (TextView) findViewById(R.id.tvFlowerDescription);
        tvFlowerPrice = (TextView) findViewById(R.id.tvFlowerPrice);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        flowerService = FlowerRepository.getFlowerService();
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        if (id != -1) {
            viewFlower(id);
        }
    }

    public void viewFlower(long id) {
        try {
            Call<Flower> call = flowerService.getFlower(id);
            call.enqueue(new Callback<Flower>() {
                @Override
                public void onResponse(Call<Flower> call, Response<Flower> response) {
                    if (response.body() != null) {
                        Flower flower = response.body();
                        Picasso.get().load(flower.getImageUrl()).into(ivFlower);
                        tvFlowerName.setText(flower.getFlowerName());
                        tvFlowerDescription.setText(flower.getDescription());
                        tvFlowerPrice.setText("Price: " + flower.getUnitPrice() + "VNĐ");
                    }
                }

                @Override
                public void onFailure(Call<Flower> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Loi", e.getMessage());
        }
    }
}