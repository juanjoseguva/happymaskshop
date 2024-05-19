package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.Util;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;

import java.util.List;

public class ManageOrdersActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;

    private Order managedOrder;
    int mUserId;

    private Button approveButton;
    private Button denyButton;
    private Button backToCounterButton;
    private EditText orderNum;
    private TextView orderDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);
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
        orderDisplay = findViewById(R.id.ordersDisplay);
        orderDisplay.setText(displayOrders());
        orderNum = findViewById(R.id.orderNum);
        approveButton = findViewById(R.id.approveButton);
        denyButton = findViewById(R.id.denyButton);
        backToCounterButton = findViewById(R.id.backFromManageOrdersButton);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managedOrder = mHappyMaskDAO.getOrderByOrderId(Integer.parseInt(orderNum.getText().toString()));
                if(managedOrder == null){
                    Util.makeToast(getApplicationContext(), "Couldn't find that order. Check the number again.");
                }else {
                    managedOrder.setStatus(2);
                    mHappyMaskDAO.update(managedOrder);
                    Util.makeToast(getApplicationContext(), "Order #" + managedOrder.getOrderId() + " approved.");
                }
            }
        });

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    managedOrder = mHappyMaskDAO.getOrderByOrderId(Integer.parseInt(orderNum.getText().toString()));
                }catch(NumberFormatException e){
                    Util.makeToast(getApplicationContext(), "Couldn't find that order. Check the number again.");
                }
                if(managedOrder == null){
                    Util.makeToast(getApplicationContext(), "Couldn't find that order. Check the number again.");
                }else {
                    managedOrder.setStatus(3);
                    mHappyMaskDAO.update(managedOrder);
                    Util.makeToast(getApplicationContext(), "Order #" + managedOrder.getOrderId() + " denied.");
                }
            }
        });

        backToCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

    }

    private String displayOrders(){
        StringBuilder sb = new StringBuilder();
        List<Order> pendingOrders = mHappyMaskDAO.getOrdersByStatus(1);
        List<Integer> orderItemIds;
        sb.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
        for(Order order : pendingOrders){
            sb.append("----------------------------------------------------\nOrder #");
            sb.append(order.getOrderId() + "\nItems: ");
            orderItemIds = order.getItemList();
            for(Integer integer : orderItemIds){
                sb.append(" " + mHappyMaskDAO.getItemById(integer).getName() + ",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n" + mHappyMaskDAO.getUserByUserId(order.getUserId()).getUserTitle() + " " +
                    mHappyMaskDAO.getUserByUserId(order.getUserId()).getUsersName() + " offered:\n");
            sb.append(order.getOffer() + "\n");
        }
        sb.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
        return sb.toString();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, ManageOrdersActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
