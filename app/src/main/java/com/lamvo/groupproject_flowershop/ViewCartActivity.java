package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ListView;

import com.lamvo.groupproject_flowershop.adapter.CartAdapter;
import com.lamvo.groupproject_flowershop.db.AppDatabase;
import com.lamvo.groupproject_flowershop.db.AppExecutors;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    private RecyclerView cartFlowerList;
    private ArrayList<Flower> flowerArrayList;
    private CartAdapter cartAdapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartFlowerList = findViewById(R.id.cartFlowerList);
        cartFlowerList.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(this);
        cartFlowerList.setAdapter(cartAdapter);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-database").build();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Flower> flowers = cartAdapter.getFlowerList();
                        database.flowerDao().delete(flowers.get(position));
                        retrieveFlowers();
                    }
                });
            }
        }).attachToRecyclerView(cartFlowerList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveFlowers();
    }

    private void retrieveFlowers() {
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Flower> flowers = database.flowerDao().getAllFlower();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartAdapter.setFlowerList(flowers);
                    }
                });
            }
        });
    }
}