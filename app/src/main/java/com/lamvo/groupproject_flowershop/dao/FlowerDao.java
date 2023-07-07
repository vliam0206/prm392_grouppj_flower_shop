package com.lamvo.groupproject_flowershop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lamvo.groupproject_flowershop.models.Flower;

import java.util.List;

@Dao
public interface FlowerDao {

    @Query("SElECT * FROM flower")
    List<Flower> getAllFlower();

    @Query("SELECT * FROM flower WHERE id IN (:flowerId)")
    Flower getFlowerById(int flowerId);

    @Insert
    void insert (Flower flower);

    @Update
    void update (Flower flower);

    @Delete
    void delete (Flower flower);
}
