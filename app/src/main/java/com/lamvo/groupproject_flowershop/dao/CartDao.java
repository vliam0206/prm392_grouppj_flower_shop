package com.lamvo.groupproject_flowershop.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lamvo.groupproject_flowershop.models.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SElECT * FROM cart")
    List<Cart> getAllFlower();

    @Query("SELECT * FROM cart WHERE id IN (:cartId)")
    Cart getCartById(int cartId);

    @Insert
    void insert (Cart cart);

    @Update
    void update (Cart cart);

    @Delete
    void delete (Cart cart);
    @Query("SELECT Max(id) From cart")
    long maxId();
}
