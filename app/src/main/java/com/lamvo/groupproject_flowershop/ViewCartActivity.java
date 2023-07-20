package com.lamvo.groupproject_flowershop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.adapters.CartAdapter;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.db.AppDatabase;
import com.lamvo.groupproject_flowershop.db.AppExecutors;
import com.lamvo.groupproject_flowershop.models.Cart;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;
    private RecyclerView cartFlowerList;
    private ArrayList<Flower> flowerArrayList;
    private CartAdapter cartAdapter;
    private AppDatabase database;
    long userId;
    CredentialService credentialService;
    List<Cart> myCart;
    TextView textviewTotal;
    Button btnCheckout;

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
        btnCheckout = (Button) findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(this);

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
                        runOnUiThread(() -> calcTotal(userId));
                    }
                });
            }
        }).attachToRecyclerView(cartFlowerList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(ViewCartActivity.this, FlowersList.class));
            }
            if (item.getItemId() == R.id.menu_order) {
                startActivity(new Intent(ViewCartActivity.this, IndividualOrderActivity.class));
            }
            if (item.getItemId() == R.id.menu_map) {
                startActivity(new Intent(ViewCartActivity.this, ViewMapActivity.class));
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveFlowers();
        try {
            calcTotal(userId);
        } catch (Exception e) {
            Log.e("e", e.getMessage());
        }

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
                        cartAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void calcTotal(long userId) {
        textviewTotal = findViewById(R.id.tvTotal);
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                myCart = database.cartDao().getAllFlowersByUserID(userId);
                double total = 0;
                for (Cart c : myCart) {
                    total += c.getUnitPrice() * c.getQuantity();
                }
                textviewTotal.setText(String.valueOf(total));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCheckout.getId()) {
            if (myCart.size() == 0) {
                Toast.makeText(ViewCartActivity.this, "Cart is empty", Toast.LENGTH_SHORT);
                return;
            }
            Intent intent = new Intent(ViewCartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
            startActivity(new Intent(ViewCartActivity.this, ViewCartActivity.class));
        } else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ViewCartActivity.this, SignInActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}