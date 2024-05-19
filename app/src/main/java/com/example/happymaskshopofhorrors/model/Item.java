package com.example.happymaskshopofhorrors.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.happymaskshopofhorrors.db.AppDatabase;
/*
Represents something on sale in a shop
 */
@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    private String mName;
    private int mPrice;
    private String mDescription;
    private String mCategory;
    private int mStock;
    private int mRestock;

    public Item(String name, int price, String description, String category, int stock) {
        this.mName = name;
        this.mPrice = price;
        this.mDescription = description;
        this.mCategory = category;
        this.mStock = stock;
        this.mRestock = stock;
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public int getStock() {
        return mStock;
    }

    public int getRestock() {
        return mRestock;
    }

    public void setRestock(int mRestock) {
        this.mRestock = mRestock;
    }
}
