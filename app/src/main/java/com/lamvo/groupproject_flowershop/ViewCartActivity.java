package com.lamvo.groupproject_flowershop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.lamvo.groupproject_flowershop.adapters.CartAdapter;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.db.AppDatabase;
import com.lamvo.groupproject_flowershop.db.AppExecutors;
import com.lamvo.groupproject_flowershop.models.Cart;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    private RecyclerView cartFlowerList;
    private ArrayList<Flower> flowerArrayList;
    private CartAdapter cartAdapter;
    private AppDatabase database;
    long userId;
    CredentialService credentialService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartFlowerList = findViewById(R.id.cartFlowerList);
        cartFlowerList.setLayoutManager(new LinearLayoutManager(this));


        cartAdapter = new CartAdapter(this);
        cartFlowerList.setAdapter(cartAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        ItemSpacingDecoration itemSpacingDecoration = new ItemSpacingDecoration(spacingInPixels);
        cartFlowerList.addItemDecoration(itemSpacingDecoration);

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-database").build();

        credentialService = new CredentialService(this);
        userId = credentialService.getCurrentUserId();

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
                        List<Cart> flowers = cartAdapter.getFlowerList();
                        database.cartDao().delete(flowers.get(position));
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
                final List<Cart> flowers = database.cartDao().getAllFlowersByUserID(userId);
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