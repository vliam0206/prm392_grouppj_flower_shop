package com.lamvo.groupproject_flowershop.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lamvo.groupproject_flowershop.dao.FlowerDao;
import com.lamvo.groupproject_flowershop.models.Flower;

@Database(entities = {Flower.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlowerDao flowerDao();
}
