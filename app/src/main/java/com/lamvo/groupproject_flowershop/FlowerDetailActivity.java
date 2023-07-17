package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.activities.ChatActivity;
import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.app_services.CredentialService;
import com.lamvo.groupproject_flowershop.db.AppDatabase;
import com.lamvo.groupproject_flowershop.db.AppExecutors;
import com.lamvo.groupproject_flowershop.models.Cart;
import com.lamvo.groupproject_flowershop.models.Flower;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlowerDetailActivity extends AppCompatActivity {
    FlowerService flowerService;
    ImageView ivFlower;
    TextView tvFlowerName, tvFlowerDescription, tvFlowerPrice;
    EditText etQuantity;
    ImageView ivAdd;
    CredentialService credentialService;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_detail);
        ivFlower = (ImageView) findViewById(R.id.ivFlower);
        tvFlowerName = (TextView) findViewById(R.id.tvFlowerName);
        tvFlowerDescription = (TextView) findViewById(R.id.tvFlowerDescription);
        tvFlowerPrice = (TextView) findViewById(R.id.tvFlowerPrice);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        flowerService = FlowerRepository.getFlowerService();
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        if (id != -1) {
            viewFlower(id);
        }
        credentialService = new CredentialService(FlowerDetailActivity.this);
        long userId = credentialService.getCurrentUserId();
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCart(id,userId);
                sendNotification();
            }
        });
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
                        tvFlowerPrice.setText("$ " + flower.getUnitPrice());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(FlowerDetailActivity.this, FlowersList.class));
        }
        else if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
            startActivity(new Intent(FlowerDetailActivity.this, ViewCartActivity.class));
        }
        else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FlowerDetailActivity.this, SignInActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void insertCart(long idFlower,long idCustomer){
        try {
            Call<Flower> call = flowerService.getFlower(idFlower);
            call.enqueue(new Callback<Flower>() {
                @Override
                public void onResponse(Call<Flower> call, Response<Flower> response) {
                    if (response.body() != null) {
                        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "app-database").build();
                        Flower flower = response.body();
                        int quantity = 0;
                        if(etQuantity.getText().toString().trim().equals("")){
                            quantity = 0;
                        } else {
                            quantity = Integer.parseInt(etQuantity.getText().toString());
                        }
                        if(quantity > flower.getUnitInStock()){
                            Toast.makeText(FlowerDetailActivity.this,"You can buy max " + flower.getUnitInStock(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(quantity <= 0){
                            etQuantity.setError("Quantity must be larger than 0");
                            return;
                        }
                         Cart cart = new Cart(1, flower.getFlowerName(),flower.getDescription(),flower.getImageUrl(),flower.getUnitPrice(),quantity,idFlower,idCustomer);
                        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    long maxId = database.cartDao().maxId();
                                    List<Cart> cartList = database.cartDao().getAllFlower();
                                    long cartId = cart.getIdFlower();
                                    List<Cart> cartx =  cartList.stream().filter(c -> c.getIdFlower() == cartId).collect(Collectors.toList());
                                    if(cartx.isEmpty()){
                                        cart.setId(maxId+1);
                                        database.cartDao().insert(cart);
                                        finish();
                                    } else {
                                        int quantity = cart.getQuantity();
                                        for (Cart cart:cartx ) {
                                              cart.setQuantity(cart.getQuantity() + quantity );
                                              AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      database.cartDao().update(cart);
                                                      finish();
                                                  }
                                              });
                                        }
                                    }
                                } catch (Exception e){
                                    Toast.makeText(FlowerDetailActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Flower> call, Throwable t) {
                    Log.e("errorx",t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Loi", e.getMessage());
        }
    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Title")
                .setContentText("Message")
                .setSmallIcon(R.drawable.icons8_shopping_basket_add_96___)
                .setColor(getResources().getColor(R.color.pink_pastel))
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(getNotificationId(), notification);
        }
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}