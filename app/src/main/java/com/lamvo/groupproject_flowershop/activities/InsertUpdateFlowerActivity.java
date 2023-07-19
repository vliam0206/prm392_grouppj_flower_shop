package com.lamvo.groupproject_flowershop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.constants.AppConstants;
import com.lamvo.groupproject_flowershop.models.Flower;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertUpdateFlowerActivity extends AppCompatActivity {
    EditText etName, etPrice, etUnitInStock, etDescription, etImageUrl;
    Button btnSave, btnCancel;
    Flower updatedFlower;
    FlowerService flowerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_update_flower);

        etName = findViewById(R.id.etFlowerName);
        etPrice = findViewById(R.id.etPrice);
        etUnitInStock = findViewById(R.id.etUnitInStock);
        etDescription = findViewById(R.id.etDescription);
        etImageUrl = findViewById(R.id.etImageUrlFlower);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        flowerService = FlowerRepository.getFlowerService();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AppConstants.UPDATED_FLOWER)) {
            updatedFlower = (Flower) intent.getSerializableExtra(AppConstants.UPDATED_FLOWER);
            generateUI(updatedFlower);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Flower flower = getFlowerObj();
                    flower.setId(updatedFlower.getId());
                    // update flower
                    updateFlower(flower);
                }
            });
        } else {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // add flower
                    Flower flower = getFlowerObj();
                    addFlower(flower);
                }
            });
        }
    }

    private void addFlower(Flower flower) {
        Call<Flower> call = flowerService.createFlower(flower);
        call.enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                if (response.body() == null) {
                    return;
                }
                Toast.makeText(InsertUpdateFlowerActivity.this, "Insert flower successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InsertUpdateFlowerActivity.this, AdminFlowerMngActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(InsertUpdateFlowerActivity.this, "Insert flower failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFlower(Flower flower) {
        Call<Flower> call = flowerService.updateFlower(updatedFlower.getId(), flower);
        call.enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                if (response.body() == null) {
                    return;
                }
                Toast.makeText(InsertUpdateFlowerActivity.this, "Update flower successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(InsertUpdateFlowerActivity.this, AdminFlowerMngActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(InsertUpdateFlowerActivity.this, "Update flower failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUI(Flower flower) {
        etName.setText(flower.getFlowerName());
        etPrice.setText(flower.getUnitPrice()+"");
        etUnitInStock.setText(flower.getUnitInStock()+"");
        etDescription.setText(flower.getDescription());
        etImageUrl.setText(flower.getImageUrl());
    }
    private Flower getFlowerObj() {
        Flower flower = new Flower();
        flower.setFlowerName(etName.getText().toString());
        flower.setUnitPrice(Double.parseDouble(etPrice.getText().toString()));
        flower.setUnitInStock(Integer.parseInt(etUnitInStock.getText().toString()));
        flower.setDescription(etDescription.getText().toString());
        flower.setImageUrl(etImageUrl.getText().toString());
        return flower;
    }
}