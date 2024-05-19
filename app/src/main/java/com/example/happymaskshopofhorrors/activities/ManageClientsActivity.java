package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.Util;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.User;

import java.util.ArrayList;
import java.util.List;

public class ManageClientsActivity extends AppCompatActivity{

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private AutoCompleteTextView banningClient;
    private Button backButton;
    private ImageView banhammer;
    private TextView activeClientDisplay;
    private TextView bannedClientDisplay;

    private Intent intent;
    private int mUserId;
    private String dekuName;
    private User deku;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clients);
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        getDatabase();
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        backButton = findViewById(R.id.backFromClientsButton);
        banhammer = findViewById(R.id.BANHAMMER);
        banningClient = findViewById(R.id.autoCompleteClientSearch);
        activeClientDisplay = findViewById(R.id.clientListText);
        bannedClientDisplay = findViewById(R.id.bannedListText);
        List<User> user = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        //show client list
        sb.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
        for(int i = 0; i < mHappyMaskDAO.getUserByBanStatus(0).size(); i++){
            user = mHappyMaskDAO.getUserByBanStatus(0);
            sb.append(user.get(i).getUserTitle() + " " + user.get(i).getUsersName() + "\n");
        }
        sb.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
        activeClientDisplay.setText(sb);
        //show banned list
        sb.delete(0,sb.length()-1);
        sb.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
        for(int i = 0; i < mHappyMaskDAO.getUserByBanStatus(1).size(); i++){
            user = mHappyMaskDAO.getUserByBanStatus(1);
            sb.append(user.get(i).getUserTitle() + " " + user.get(i).getUsersName() + "\n");
        }
        sb.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
        bannedClientDisplay.setText(sb);
        //setup ban bar
        String[] namestoBan = new String[mHappyMaskDAO.getUserByBanStatus(0).size()];
        for(int i = 0; i < mHappyMaskDAO.getUserByBanStatus(0).size(); i++){
            namestoBan[i] = mHappyMaskDAO.getUserByBanStatus(0).get(i).getUsersName();
        }
        banningClient.setAdapter(new ArrayAdapter<>(ManageClientsActivity.this, android.R.layout.simple_list_item_1, namestoBan));

        banhammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dekuName = banningClient.getText().toString();
                deku = mHappyMaskDAO.getUserByUsersName(dekuName);
                deku.banHammer();
                mHappyMaskDAO.update(deku);
                Util.makeToast(getApplicationContext(), deku.getUserTitle() + " " + dekuName + " was sent their Deku mask.");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkActivity.intentFactory(getApplicationContext(), mUserId);
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
        Intent newIntent = new Intent(context, ManageClientsActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
