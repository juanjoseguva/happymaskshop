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
import com.example.happymaskshopofhorrors.Util;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.User;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Button changePassword;
    private Button cancel;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText reNewPassword;

    private String oldPW;
    private String newPW;
    private String reNewPW;

    private Intent intent;
    private int mUserId;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        mUser = mHappyMaskDAO.getUserByUserId(mUserId);
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        changePassword = findViewById(R.id.changePassButt);
        cancel = findViewById(R.id.cancelPass);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        reNewPassword = findViewById(R.id.reNewPassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(oldPW.equals(mUser.getPassword())){
                    if(newPW.equals(reNewPW)){
                        mUser.setPassword(newPW);
                        mHappyMaskDAO.update(mUser);
                        Intent intent = AccountActivity.intentFactory(getApplicationContext(), mUserId);
                        startActivity(intent);
                    }else{
                        Util.makeToast(getApplicationContext(), "Passwords don't match");
                    }
                }else{
                    Util.makeToast(getApplicationContext(), "Incorrect password");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });
    }

    private void getValuesFromDisplay(){
        oldPW = oldPassword.getText().toString();
        newPW = newPassword.getText().toString();
        reNewPW = reNewPassword.getText().toString();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, ChangePasswordActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
