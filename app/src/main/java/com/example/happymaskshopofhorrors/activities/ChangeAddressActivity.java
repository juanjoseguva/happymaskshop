package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.User;

public class ChangeAddressActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Button confirmChangeAddressButton;
    private Button cancelChangeAddressButton;

    private EditText newAddress;

    private Intent intent;
    private int mUserId;
    private String newAddy;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        mUser = mHappyMaskDAO.getUserByUserId(mUserId);
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        confirmChangeAddressButton = findViewById(R.id.changeAddyButton);
        cancelChangeAddressButton = findViewById(R.id.cancelAddy);
        newAddress = findViewById(R.id.newAddressInput);

        confirmChangeAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAddy = newAddress.getText().toString();
                mUser.setAddress(newAddy);
                mHappyMaskDAO.update(mUser);
                Intent intent = AccountActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        cancelChangeAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, ChangeAddressActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
