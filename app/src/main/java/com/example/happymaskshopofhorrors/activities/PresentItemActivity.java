package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;

/*
This displays the item, the description and price, and lets the user add or not to their order.
 */
public class PresentItemActivity extends AppCompatActivity {

    private static final String ITEM_ID_KEY = "com.example.happymaskshopofhorrors.itemIdKey";
    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.happymaskshopofhorrors.PREFERENCES_KEY";

    private HappyMaskDAO mHappyMaskDAO;

    private SharedPreferences mPreferences = null;
    private int mUserId;
    private User mUser;
    private int mItemId;
    private Item mItem;

    TextView itemDescription;

    ImageView dekuShield;
    ImageView dragonpriestMask;
    ImageView manaPotion;
    ImageView pegasusBoots;
    ImageView phoenixDown;
    ImageView woodenShield;
    ImageView meridiasBeacon;
    ImageView masterSword;
    ImageView healthPotion;
    ImageView heartContainer;
    ImageView hookshot;
    ImageView hylianShield;
    ImageView ironBoots;
    ImageView majorasMask;
    ImageView portalGun;
    ImageView blueShell;
    ImageView busterSword;
    ImageView cubone;
    ImageView fairy;
    ImageView loki;
    ImageView lPiece;
    ImageView miraak;
    ImageView nihilus;
    ImageView ocarina;
    ImageView pipBoy;
    ImageView predator;
    ImageView wabbajack;
    ImageView windwaker;


    private Button addButton;
    private Button backButton;

    private ImageView backImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_item);
        getDatabase();
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = mHappyMaskDAO.getUserByUserId(mUserId);
        mItemId = getIntent().getIntExtra(ITEM_ID_KEY, -1);
        mItem = mHappyMaskDAO.getItemById(mItemId);
        itemDescription = findViewById(R.id.itemDescription);
        //all the images of the items on sale
        dekuShield = findViewById(R.id.dekushield);
        dragonpriestMask = findViewById(R.id.dragonpriestMask);
        manaPotion = findViewById(R.id.manaPotion);
        pegasusBoots = findViewById(R.id.pegasusBoots);
        phoenixDown = findViewById(R.id.phoenixDown);
        woodenShield = findViewById(R.id.woodenShield);
        meridiasBeacon = findViewById(R.id.meridiasBeacon);
        masterSword = findViewById(R.id.masterSword);
        healthPotion = findViewById(R.id.healthPotion);
        heartContainer = findViewById(R.id.heartContainer);
        hookshot = findViewById(R.id.hookshot);
        hylianShield = findViewById(R.id.hylianShield);
        ironBoots = findViewById(R.id.ironBoots);
        majorasMask = findViewById(R.id.majorasMask);
        portalGun = findViewById(R.id.portalGun);
        blueShell = findViewById(R.id.blueShell);
        busterSword = findViewById(R.id.busterSword);
        cubone = findViewById(R.id.cubone);
        fairy = findViewById(R.id.fairy);
        loki = findViewById(R.id.loki);
        lPiece = findViewById(R.id.lpiece);
        miraak = findViewById(R.id.miraak);
        nihilus = findViewById(R.id.nihilus);
        ocarina = findViewById(R.id.ocarina);
        pipBoy = findViewById(R.id.pipBoy);
        predator = findViewById(R.id.predator);
        wabbajack = findViewById(R.id.wabbajack);
        windwaker = findViewById(R.id.windwaker);
        setItemVisibility();//only show the one we want
        wireUpDisplay();
    }

    private void getDatabase() {
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    private void wireUpDisplay(){

        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        backImage = findViewById(R.id.backImage);

        if(mItemId == 7){
            backButton.setVisibility(View.INVISIBLE);
            backImage.setVisibility(View.INVISIBLE);
        }else{
            backButton.setVisibility(View.VISIBLE);
            backImage.setVisibility(View.VISIBLE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = mHappyMaskDAO.getCurrentOrder(mUserId);//get user's active order
                order.addItemToOrder(mHappyMaskDAO.getItemById(mItemId));//adds item to order
                mHappyMaskDAO.update(order);//saves change to order
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent);//to main menu
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent);//to main menu
            }
        });

    }

    //This sets the visibility of the items on the page and displays the relevant description
    private void setItemVisibility() {
        if(mItem.getItemId() == 1){
            dekuShield.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.deku_shield_desc);
        }else{
            dekuShield.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 2){
            dragonpriestMask.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.dragonpriest_mask_description);
        }else{
            dragonpriestMask.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 3){
            manaPotion.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.mana_potion_description);
        }else{
            manaPotion.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 4){
            pegasusBoots.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.pegasus_description);
        }else{
            pegasusBoots.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 5){
            phoenixDown.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.phoenix_description);
        }else{
            phoenixDown.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 6){
            woodenShield.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.wooden_shield_description);
        }else{
            woodenShield.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 7){
            meridiasBeacon.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.meridia_description);
        }else{
            meridiasBeacon.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 8){
            masterSword.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.master_sword_description);
        }else{
            masterSword.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 9){
            healthPotion.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.health_potion_description);
        }else{
            healthPotion.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 10){
            heartContainer.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.heart_container_description);
        }else{
            heartContainer.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 11){
            hookshot.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.hookshot_description);
        }else{
            hookshot.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 12){
            hylianShield.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.hylian_shield_description);
        }else{
            hylianShield.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 13){
            ironBoots.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.iron_boots_description);
        }else{
            ironBoots.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 14){
            majorasMask.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.majoras_description);
        }else{
            majorasMask.setVisibility(View.INVISIBLE);
        }if(mItem.getItemId() == 15){
            portalGun.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.portal_gun_description);
        }else{
            portalGun.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 16){
            blueShell.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.blue_shell_description);
        }else{
            blueShell.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 17){
            busterSword.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.buster_sword_description);
        }else{
            busterSword.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 18){
            cubone.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.cubone_description);
        }else{
            cubone.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 19){
            fairy.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.fairy_description);
        }else{
            fairy.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 20){
            loki.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.loki_description);
        }else{
            loki.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 21){
            lPiece.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.l_piece_description);
        }else{
            lPiece.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 22){
            miraak.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.miraak_description);
        }else{
            miraak.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 23){
            nihilus.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.nihilus_description);
        }else{
            nihilus.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 24){
            ocarina.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.ocarina_description);
        }else{
            ocarina.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 25){
            pipBoy.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.pipBoy_description);
        }else{
            pipBoy.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 26){
            predator.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.predator_description);
        }else{
            predator.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 27){
            wabbajack.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.wabbajack_description);
        }else{
            wabbajack.setVisibility(View.INVISIBLE);
        }
        if(mItem.getItemId() == 28){
            windwaker.setVisibility(View.VISIBLE);
            itemDescription.setText(R.string.windwaker_description);
        }else{
            windwaker.setVisibility(View.INVISIBLE);
        }
    }


    public static Intent intentFactory(Context context, int userId, int itemId) {
        Intent intent = new Intent(context, PresentItemActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        intent.putExtra(ITEM_ID_KEY, itemId);
        return intent;
    }
}
