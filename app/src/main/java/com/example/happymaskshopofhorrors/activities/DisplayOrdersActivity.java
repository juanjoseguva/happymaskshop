package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;

import java.util.ArrayList;
import java.util.List;

public class DisplayOrdersActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;

    private int mUserId;

    private Button backButton;

    private TextView pendingOrders;
    private TextView approvedOrders;
    private TextView deniedOrders;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_orders);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        backButton = findViewById(R.id.backFromOrdersDisplayButton);
        pendingOrders = findViewById(R.id.pendingOrders);
        approvedOrders = findViewById(R.id.approvedOrders);
        deniedOrders = findViewById(R.id.deniedOrders);
        address = findViewById(R.id.address);
        pendingOrders.setText(displayOrders(1));
        approvedOrders.setText(displayOrders(2));
        deniedOrders.setText(displayOrders(3));
        String addy = "and sent to: " + mHappyMaskDAO.getUserByUserId(mUserId).getAddress();
        address.setText(addy);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });
    }

    public String displayOrders(int status){
        StringBuilder sb = new StringBuilder();
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        List<Order> orders = mHappyMaskDAO.getUserOrdersByStatus(status, mUserId);
        List<Integer> orderItems;
        int total;
        int gTotal = 0;
        for (Order order: orders) {
            total = 0; //reset individual order totals
            sb.append("----------------------------------------------------\n");
            sb.append("Happy Mask Order#: " + order.getOrderId()+"\n");
            orderItems = order.getItemList();
            for (Integer itemId : orderItems){
                sb.append(" " + mHappyMaskDAO.getItemById(itemId).getName() + ", ");
                total += mHappyMaskDAO.getItemById(itemId).getPrice();
                gTotal += mHappyMaskDAO.getItemById(itemId).getPrice();
            }
            sb.deleteCharAt(sb.length()-2); //gets rid of the hanging comma.
            sb.append("\nTotal: " + total + " gold.\n");
            if(order.getStatus() == 1){
                sb.append("You offered: " + order.getOffer() + "\n");
            }
        }
        sb.append("\nGrand Total: " + gTotal + " gold.\n");
        return sb.toString();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }



    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, DisplayOrdersActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
