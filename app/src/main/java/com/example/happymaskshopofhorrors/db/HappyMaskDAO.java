package com.example.happymaskshopofhorrors.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.model.User;

import java.util.List;

@Dao
public interface HappyMaskDAO {

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " ORDER BY  mPrice DESC")
    List<Item> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mItemId = :itemId")
    Item getItemById(int itemId);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mCategory = :category  ORDER BY  mPrice DESC")
    List<Item> getItemsByCategory(int category);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE mName = :name")
    Item getItemByName(String name);

    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsersName = :usersName")
    User getUserByUsersName(String usersName);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE isBanned = :banStatus")
    List<User> getUserByBanStatus(int banStatus);

    @Insert
    void insert(Order...orders);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE)
    List<Order> getAllOrders();

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE mUserId = :userId")
    List<Order> getOrdersByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE mOrderId = :orderId")
    Order getOrderByOrderId(int orderId);

    @Query(" SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE mStatus = :status ORDER BY mOrderId")
    List<Order> getOrdersByStatus(int status);

    @Query(" SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE mStatus = :status " + " AND mUserId = :userId ORDER BY mOrderId")
    List<Order> getUserOrdersByStatus(int status, int userId);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE mStatus = 0 " + " AND mUserId = :userId")
    Order getCurrentOrder(int userId);

}
