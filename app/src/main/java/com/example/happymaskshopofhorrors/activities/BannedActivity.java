package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happymaskshopofhorrors.R;

public class BannedActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private ImageView dekuMask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banned);
        dekuMask = findViewById(R.id.dekuMask);

        dekuMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.intentFactory(getApplicationContext(), -1);
                startActivity(intent);//send them out with a consolation prize
            }
        });


    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, BannedActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
