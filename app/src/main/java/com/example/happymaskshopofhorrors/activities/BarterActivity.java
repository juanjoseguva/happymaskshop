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

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.Util;

import java.util.ArrayList;
import java.util.List;

public class BarterActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private HappyMaskDAO mHappyMaskDAO;

    private Button offerButton;
    private Button cancelBarterButton;

    private EditText barterOffer;

    private TextView itemsToBarter;
//TODO Show barter offer.
    private Intent intent;
    private int mUserId;
    private Order order;
    private String offerText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barter);
        getDatabase();
        intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY,-1);
        wireUpDisplay();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    private void wireUpDisplay(){
        offerButton = findViewById(R.id.offerButton);
        cancelBarterButton = findViewById(R.id.cancelBarterButton);
        barterOffer = findViewById(R.id.barterOffer);
        itemsToBarter = findViewById(R.id.itemsToBarter);

        List<Integer> barterItemIds = mHappyMaskDAO.getCurrentOrder(mUserId).getItemList();
        List<Item> barterItems = new ArrayList<>();
        for(int i = 0; i < barterItemIds.size(); i++){
            barterItems.add(mHappyMaskDAO.getItemById(barterItemIds.get(i)));//generate an item list to pull names and prices.
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n*******************************");
        int total = 0;
        for (Item item : barterItems) {
            sb.append("\nItemName: " + item.getName() +
                    " at: " + item.getPrice() + " gold.");//build the abbreviated item list to display
            total += item.getPrice();
        }
        sb.append("\n_______________________________\nTotal Due: " + total + " gold.");
        itemsToBarter.setText(sb);

        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = mHappyMaskDAO.getCurrentOrder(mUserId);
                offerText = barterOffer.getText().toString();
                order.setOffer(offerText);//Save client offer for shopkeep to approve or not
                order.setStatus(1);//set order to pending
                mHappyMaskDAO.update(order);//save changes
                order = new Order(mUserId);//create new current order for client
                mHappyMaskDAO.insert(order);//save new current order
                Util.makeToast(getApplicationContext(), "Your offer has been sent to review.");
                Intent intent = MainActivity.intentFactory(getApplicationContext(),mUserId);
                startActivity(intent);//back to main menu
            }
        });

        cancelBarterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheckoutActivity.intentFactory(getApplicationContext(), mUserId);
                startActivity(intent);//back to checkout
            }
        });

    }

    public static Intent intentFactory(Context context, int userId) {
        Intent newIntent = new Intent(context, BarterActivity.class);
        newIntent.putExtra(USER_ID_KEY, userId);
        return newIntent;
    }
    
}
