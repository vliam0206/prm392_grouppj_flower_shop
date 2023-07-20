package com.lamvo.groupproject_flowershop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.R;
import com.lamvo.groupproject_flowershop.SignInActivity;
import com.lamvo.groupproject_flowershop.adapters.FlowerMngAdapter;
import com.lamvo.groupproject_flowershop.apis.FlowerRepository;
import com.lamvo.groupproject_flowershop.apis.FlowerService;
import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.ArrayList;

import kotlinx.coroutines.flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFlowerMngActivity extends AppCompatActivity {
    FlowerService flowerService;
    ListView lvFlowers;
    ArrayList<Flower> flowerList;
    FlowerMngAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_flower_mng);

        flowerService = FlowerRepository.getFlowerService();
        lvFlowers = findViewById(R.id.lvFlowerMng);
        flowerList = new ArrayList<>();
        adapter = new FlowerMngAdapter(flowerList, R.layout.flower_mng_row, AdminFlowerMngActivity.this);
        lvFlowers.setAdapter(adapter);
        loadFlower();

        ImageView imgAdd = findViewById(R.id.imgAddFlower);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFlowerMngActivity.this, InsertUpdateFlowerActivity.class));
            }
        });

        lvFlowers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Flower flower = (Flower) adapter.getItem(position);
                dialogDeleteFlower(flower);
                return false;
            }
        });
    }

    private void dialogDeleteFlower(Flower flower) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Do you want to delete flower "+flower.getFlowerName()+"?");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFlower(flower.getId());
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogXoa.show();
    }

    private void deleteFlower(long id) {
        Call<Flower> call = flowerService.deleteFlower(id);
        call.enqueue(new Callback<Flower>() {
            @Override
            public void onResponse(Call<Flower> call, Response<Flower> response) {
                Toast.makeText(AdminFlowerMngActivity.this, "Delete flower"+id+"successfully!", Toast.LENGTH_SHORT).show();
                loadFlower();
            }
            @Override
            public void onFailure(Call<Flower> call, Throwable t) {
                Toast.makeText(AdminFlowerMngActivity.this, "Delete flower failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFlower() {
        Call<Flower[]> call = flowerService.getAllFlowers();
        call.enqueue(new Callback<Flower[]>() {
            @Override
            public void onResponse(Call<Flower[]> call, Response<Flower[]> response) {
                Flower[] flowers = response.body();
                if (flowers== null || flowers.length == 0) {
                    return;
                }
                flowerList.clear();
                for ( Flower f: flowers) {
                    flowerList.add(f);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Flower[]> call, Throwable t) {
                Toast.makeText(AdminFlowerMngActivity.this, "Laod flower list failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home_admin) {
            // admin home
            startActivity(new Intent(AdminFlowerMngActivity.this, AdminActivity.class));
        }
        else if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminFlowerMngActivity.this, SignInActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}