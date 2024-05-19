package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.Util;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.User;

public class WorkActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;
    private int mUserId;

    private Button manageItemsButton;
    private Button manageOrdersButton;
    private Button manageClientsButton;
    private Button clockOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        manageItemsButton = findViewById(R.id.manageInventoryButton);
        manageOrdersButton = findViewById(R.id.manageOrdersButton);
        manageClientsButton = findViewById(R.id.manageClientsButton);
        clockOutButton = findViewById(R.id.endShiftButton);

        manageItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.makeToast(getApplicationContext(), "Not implemented yet.");
                //Intent intent = ManageItemsActivity.intentFactory(getApplicationContext(), mUserId);
                //startActivity(intent);
            }
        });

        manageOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ManageOrdersActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        manageClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ManageClientsActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        clockOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);//back to the other side of the counter
            }
        });;
    }

    private void getDatabase(){
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, WorkActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}
