package com.example.happymaskshopofhorrors.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.example.namespace.R;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.Util;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.happymaskshopofhorrors.PREFERENCES_KEY";

    private HappyMaskDAO mHappyMaskDAO;

    private Button mWorkButton;

    private SharedPreferences mPreferences = null;

    private Intent intent;

    private int mUserId;
    private User mUser;

    //TODO Miraak/Cubone shift + others

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    private void wireUpDisplay() {
        Button mSearchButton = findViewById(R.id.searchButton);
        Button mSuggestButton = findViewById(R.id.suggestItemButton);
        Button mAccountButton = findViewById(R.id.accountButton);
        Button mCartButton = findViewById(R.id.myCartButton);
        Button mOrdersButton = findViewById(R.id.myOrdersButton);
        Button mLogOutButton = findViewById(R.id.logOutButton);
        mWorkButton = findViewById(R.id.workButton);

        TextView custTitleNameField = findViewById(R.id.custTitleName);
        String custTitleName = mUser.getUserTitle() + " " + mUser.getUsersName() + "?";
        custTitleNameField.setText(custTitleName);

        //Show/Hide entry to back of house
        if (mUser.isAdmin() == 1) {
            mWorkButton.setVisibility(View.VISIBLE);
        } else {
            mWorkButton.setVisibility(View.INVISIBLE);
        }

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        //recomends an item from the shop at random
        mSuggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int rando = rand.nextInt(28)+1;
                Item randItem = mHappyMaskDAO.getItemById(rando);
                Intent intent = PresentItemActivity.intentFactory(getApplicationContext(), mUser.getUserId(), randItem.getItemId());
                startActivity(intent);//to item display screen
            }
        });

        mAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        mCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheckoutActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);//to the checkout screen
            }
        });

        mOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DisplayOrdersActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

        //exit the shop
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        //work a shift as a shopkeeper
        mWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });

    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        Intent intent = LoginActivity.intentFactory(getApplicationContext(), -1);
                        startActivity(intent);
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertBuilder.create().show();
    }

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    @NonNull
    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
