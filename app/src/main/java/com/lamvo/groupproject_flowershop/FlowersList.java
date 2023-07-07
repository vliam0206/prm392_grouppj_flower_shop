package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.activities.ChatActivity;
import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FlowersList.this, FlowerDetailActivity.class);
                intent.putExtra("id", arrayFlowers.get(position).getId());
                startActivity(intent);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_chat) {
            // start chat activity
            startActivity(new Intent(FlowersList.this, ChatActivity.class));
        }
        else if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
        }
        else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FlowersList.this, SignInActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}