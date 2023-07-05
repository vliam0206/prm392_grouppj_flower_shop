package com.lamvo.groupproject_flowershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;

import kotlinx.coroutines.flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowersList extends AppCompatActivity {

    static ListView listView;
    ArrayList<Flower> arrayFlowers;
    static ListAdapter adapter;
    static FlowerService flowerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers_list);

        flowerService = FlowerRepository.getFlowerService();
        listView = findViewById(R.id.listview);
        arrayFlowers = new ArrayList<>();

        getAllFlowers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllFlowers();
    }

    void getAllFlowers() {
        Call<Flower[]> call = flowerService.getAllFlowers();
        call.enqueue(new Callback<Flower[]>() {
            @Override
            public void onResponse(Call<Flower[]> call, Response<Flower[]> response) {
                Flower[] flowers = response.body();
                if (flowers == null) {
                    return;
                }

                //Clear the arrayFlowers list
                arrayFlowers.clear();

                for (Flower flower : flowers) {
                    arrayFlowers.add(flower);
                }

                //Notify the adapter everywhen the data change
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                adapter = new ListAdapter(FlowersList.this, R.layout.activity_list_item, arrayFlowers);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Flower[]> call, Throwable t) {
                Toast.makeText(FlowersList.this, "Get flowers fail", Toast.LENGTH_SHORT).show();

            }
        });
    }
}