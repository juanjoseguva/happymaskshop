package com.example.happymaskshopofhorrors.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.db.ItemListConverter;

import java.util.List;

/*
An auxiliary object tying a user with one to many items, which includes a status variable for differentiation
 */
@Entity(tableName = AppDatabase.ORDER_TABLE)
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int mOrderId;

    private int mUserId;
    private int mStatus;
    private String mItems;
    private String mOffer;

    public Order(int userId) {
        mUserId = userId;
        mItems = "";
        mStatus = 0; //Default to Current (0), can change to Pending (1), Approved (2), or Denied (3)
        mOffer = "";
    }

    public String getOffer() {
        return mOffer;
    }

    public void setOffer(String mOffer) {
        this.mOffer = mOffer;
    }
    public int getOrderId() {
        return mOrderId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getItems(){return this.mItems;}

    public void setItems(String mItems) {
        this.mItems = mItems;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public int getStatus() {
        return mStatus;
    }

    public List<Integer> getItemList() {return ItemListConverter.convertStringToItemList(mItems);} //returns a list of item Ids as Integers

    public void addItemToOrder(Item item){
        if(mItems.equals("")){
            mItems += item.getItemId();
        }else{
            mItems += ", " + item.getItemId();
        }
    }

    public void removeItem(Item item){//remove a specific Item from the order. //TODO Stretch goal: give users ability to delete specific items from current orders.
        if(ItemListConverter.convertStringToItemList(mItems).contains(item.getItemId())){
            List<Integer> itemIdList = ItemListConverter.convertStringToItemList(mItems);
            itemIdList.remove(item.getItemId());
            mItems = ItemListConverter.convertItemListToString(itemIdList);
            System.out.println(item.getName() + " removed.");
            return;
        }
        System.out.println("Item not found in your order.");
        return;
    }

    public void setOrderId(int mOrderId) {
        this.mOrderId = mOrderId;
    }


}
