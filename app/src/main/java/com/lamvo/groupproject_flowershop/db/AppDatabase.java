package com.lamvo.groupproject_flowershop.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lamvo.groupproject_flowershop.dao.CartDao;
import com.lamvo.groupproject_flowershop.models.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
