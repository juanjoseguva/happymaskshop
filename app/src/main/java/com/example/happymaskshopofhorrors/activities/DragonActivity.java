package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.Util;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;

public class DragonActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Intent intent;
    private int mUserId;

    private TextView goldHoard;
    private TextView treasureHoard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragon);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        goldHoard = findViewById(R.id.hoardGold);
        treasureHoard = findViewById(R.id.hoardGoods);
        String result = String.valueOf(mHappyMaskDAO.getUserByUserId(mUserId).isBanned());
        goldHoard.setText(result);
        treasureHoard.setText(mHappyMaskDAO.getUserByUserId(mUserId).getAddress());
        Util.makeToast(getApplicationContext(), "Stay a while, master. Rest on your laurels.");
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, DragonActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
