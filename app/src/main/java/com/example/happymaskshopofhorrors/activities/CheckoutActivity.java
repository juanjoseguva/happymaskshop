package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.Util;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;

    private Order currentOrder;
    private int mUserId;

    private int total;

    private Button checkoutButton;
    private Button backButton;
    private Button cancelOrderButton;
    private Button barterButton;

    TextView itemsSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        wireUpDisplay();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    private void wireUpDisplay(){
        itemsSummary = findViewById(R.id.itemsSummary);
        checkoutButton = findViewById(R.id.checkoutButton);
        backButton = findViewById(R.id.backButton);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        barterButton = findViewById(R.id.barterButton);
        currentOrder = mHappyMaskDAO.getCurrentOrder(mUserId);
        List<Integer> orderItemIds = currentOrder.getItemList();
        List<Item> orderItems = new ArrayList<>();
        for(int i = 0; i < orderItemIds.size(); i++){
            orderItems.add(mHappyMaskDAO.getItemById(orderItemIds.get(i)));
        }
        StringBuilder sb = new StringBuilder();
        total = 0;
        for (Item item : orderItems) {
            sb.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~\nItemName: " + item.getName() +
                    "\nPrice: " + item.getPrice() + "\nDescription: " + item.getDescription());//build the item list to display
            total += item.getPrice();
        }
        sb.append("\n~~~~~~~~~~~~~~~~~~~~~~~~~~\nTotal Due: " + total + " gold.");
        itemsSummary.setText(sb);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentOrder.setStatus(2);
                mHappyMaskDAO.update(currentOrder);
                //Dragon gets their due
                User dragon = mHappyMaskDAO.getUserByUserId(0);
                dragon.setIsBanned(dragon.isBanned()+total);
                mHappyMaskDAO.update(dragon);
                Order order = new Order(mUserId);
                mHappyMaskDAO.insert(order);
                Util.makeToast(getApplicationContext(), "Your order has been placed.");
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHappyMaskDAO.delete(currentOrder);
                Order nextOrder = new Order(mUserId);
                mHappyMaskDAO.insert(nextOrder);
                Util.makeToast(getApplicationContext(), "Order cancelled.");
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        barterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BarterActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, CheckoutActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
