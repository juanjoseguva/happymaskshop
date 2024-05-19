package com.example.happymaskshopofhorrors.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.model.User;

@Database(entities = {Item.class, User.class, Order.class}, version = 5)
@TypeConverters(ItemListConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "HAPPYMASK_DATABASE";
    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String ORDER_TABLE = "ORDER_TABLE";

    public abstract HappyMaskDAO getHappyMaskDAO();
}