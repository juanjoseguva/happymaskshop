package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.Util;

public class AccountActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";


    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;
    private int mUserId;
    private User mUser;

    private Button changePasswordButton;
    private Button changeAddressButton;
    private Button employButton;
    private Button autobanButton;
    private Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        mUser = mHappyMaskDAO.getUserByUserId(mUserId);
        wireUpDisplay();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    private void wireUpDisplay(){
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changeAddressButton = findViewById(R.id.changeAddressButton);
        employButton = findViewById(R.id.employButton);
        autobanButton = findViewById(R.id.autobanButton);
        backButton = findViewById(R.id.backfromacctbutton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChangePasswordActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        changeAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChangeAddressActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        employButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUser.isAdmin() == 1){
                    Util.makeToast(getApplicationContext(), "You're already working for me.");
                }else {
                    mUser.setAdmin(1);//make user an admin
                    mHappyMaskDAO.update(mUser);//save change
                    Util.makeToast(getApplicationContext(), "You now have administrative privileges.");
                }
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        autobanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.banHammer();//Ban user from shop
                mHappyMaskDAO.update(mUser);//save change
                Intent intent = BannedActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);//Go to exiled page
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);//back to the main menu
            }
        });

    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, AccountActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
