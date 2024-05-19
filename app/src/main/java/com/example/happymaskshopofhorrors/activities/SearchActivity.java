package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.db.ItemListConverter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private AutoCompleteTextView searchBar;
    private Button go;

    private Intent intent;
    private int mUserId;
    private String itemName;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
        getDatabase();
        wireUpDisplay();

        //String[] items = ItemListConverter.convertItemListToString(mHappyMaskDAO.getAllItems());
    }

    private void wireUpDisplay(){
        go = findViewById(R.id.goToItem);
        searchBar = findViewById(R.id.autoCompleteItemSearch);
        String[] itemNames = new String[mHappyMaskDAO.getAllItems().size()];
        //TODO Also add tags (but don't show???? Like, if user types wearable, items with tag wearable show up)
        for(int i = 0; i < mHappyMaskDAO.getAllItems().size(); i++){
            itemNames[i] = mHappyMaskDAO.getAllItems().get(i).getName();
        }
        searchBar.setAdapter(new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, itemNames));

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName = searchBar.getText().toString();
                itemId = mHappyMaskDAO.getItemByName(itemName).getItemId();
                Intent presentItem = PresentItemActivity.intentFactory(getApplicationContext(), mUserId, itemId);
                startActivity(presentItem);
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
        Intent newIntent = new Intent(context, SearchActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
}
