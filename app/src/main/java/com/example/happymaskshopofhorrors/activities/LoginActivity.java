package com.example.happymaskshopofhorrors.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
import com.example.happymaskshopofhorrors.model.Item;
import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.Util;

import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private static final String USER_ID_KEY = "com.example.happymaskshopofhorrors.userIdKey";

    private EditText mUsernameField;
    private EditText mPasswordField;

    private Button mLoginButton;
    private Button mFTCAccountButton;

    private HappyMaskDAO mHappyMaskDAO;

    private String mUserName;
    private String mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getDatabase();
        initializeDatabase();
        wireupDistplay();
    }

    private void getDatabase(){
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    // Initialize Base Database
    private void initializeDatabase() {
        List<User> users = mHappyMaskDAO.getAllUsers();
        if (users.size() <= 0) {//initialize some default users
            User altUser = new User("BeetleBeedle", "ohthankyou", "Beedle", "Shopkeeper", "321 Market Rd.", 1);
            User hotUser = new User("HoTLink", "Gannondork$ucks", "Link", "Hero of Time", "15 Deku Tree Ln.", 0);
            User zelUser = new User("HerHighness420", "HashiSheik", "Zelda", "Princess of Hyrule", "Hyrule Casle", 0);
            User ganUser = new User("GerudoKing69", "Hyruleismine", "Gannondorf", "King", "42 Gerudo Valley Way.", 0);
            User dragOwn = new User("dragownborn", "opensaysme", "Dragonborn Maskman", "Owner", "The Happy Mask Shop Basement", 1);//Easter Egg
            ganUser.banHammer();//Ban Gannondorf
            dragOwn.setIsBanned(44864);
            mHappyMaskDAO.insert(dragOwn, altUser, hotUser, zelUser, ganUser);//load into database
            int O1 = mHappyMaskDAO.getUserByUsername("BeetleBeedle").getUserId();
            int O2 = mHappyMaskDAO.getUserByUsername("HoTLink").getUserId();
            int O3 = mHappyMaskDAO.getUserByUsername("HerHighness420").getUserId();
            int O4 = mHappyMaskDAO.getUserByUsername("GerudoKing69").getUserId();// create some blank orders for default users
            mHappyMaskDAO.insert(new Order(O1), new Order(O2), new Order(O3), new Order(O4));// load 'em up too
        }
        List<Item> items = mHappyMaskDAO.getAllItems();
        if (items.size() <= 0) {//initialize some default items
            mHappyMaskDAO.insert(new Item("Deku Shield", 12, getString(R.string.deku_shield_desc), "Wearable, Shield", 20));
            mHappyMaskDAO.insert(new Item("Dragonpriest Mask", 300, getString(R.string.dragonpriest_mask_description), "Wearable, Mask", 8));
            mHappyMaskDAO.insert(new Item("Mana Potion", 15, getString(R.string.mana_potion_description), "Consumable, Potion", 100));
            mHappyMaskDAO.insert(new Item("Pegasus Boots", 200, getString(R.string.pegasus_description), "Wearable, Key Item", 4));
            mHappyMaskDAO.insert(new Item("Phoenix Down", 30, getString(R.string.phoenix_description), "Consumable", 100));
            mHappyMaskDAO.insert(new Item("Wooden Shield", 40, getString(R.string.wooden_shield_description), "Wearable, Shield", 10));
            mHappyMaskDAO.insert(new Item("Meridia's Beacon", 0, getString(R.string.meridia_description), "Key Item", 1));
            mHappyMaskDAO.insert(new Item("Master Sword", 400, getString(R.string.master_sword_description), "Weapon", 20));
            mHappyMaskDAO.insert(new Item("Health Potion", 10, getString(R.string.health_potion_description), "Consumable, Potion", 100));
            mHappyMaskDAO.insert(new Item("Heart Container", 150, getString(R.string.heart_container_description), "Consumable", 2));
            mHappyMaskDAO.insert(new Item("Hookshot", 100, getString(R.string.hookshot_description), "Weapon, Key Item", 1));
            mHappyMaskDAO.insert(new Item("Hylian Shield", 75, getString(R.string.hylian_shield_description), "Wearable, Shield", 5));
            mHappyMaskDAO.insert(new Item("Iron Boots", 150, getString(R.string.iron_boots_description), "Wearable, Key Item", 6));
            mHappyMaskDAO.insert(new Item("Majora's Mask", 666, getString(R.string.majoras_description), "Wearable, Mask", 1));
            mHappyMaskDAO.insert(new Item("Aperture Science Hand-held Portal Device", 1618, getString(R.string.portal_gun_description), "Key Item", 1));
            mHappyMaskDAO.insert(new Item("Blue Shell", 35, getString(R.string.blue_shell_description), "Weapon, Consumable", 20));
            mHappyMaskDAO.insert(new Item("Buster Sword", 450, getString(R.string.buster_sword_description), "Weapon", 10));
            mHappyMaskDAO.insert(new Item("Cubone Mask", 44, getString(R.string.cubone_description), "Wearable, Shield", 4));
            mHappyMaskDAO.insert(new Item("Fairy in a Bottle", 80, getString(R.string.fairy_description), "Consumable, Living Creature", 5));
            mHappyMaskDAO.insert(new Item("Loki Mask", 404, getString(R.string.loki_description), "Wearable, Weapon", 10));
            mHappyMaskDAO.insert(new Item("l Piece", 20, getString(R.string.l_piece_description), "Key Item", 4));
            mHappyMaskDAO.insert(new Item("Miraak Mask", 200, getString(R.string.miraak_description), "Wearable, Shield", 3));
            mHappyMaskDAO.insert(new Item("Nihilus Mask", 205, getString(R.string.nihilus_description), "Wearable", 2));
            mHappyMaskDAO.insert(new Item("Ocarina of Time", 330, getString(R.string.ocarina_description), "Key Item", 1));
            mHappyMaskDAO.insert(new Item("Pip Boy", 100, getString(R.string.pipBoy_description), "Wearable, Key Item", 10));
            mHappyMaskDAO.insert(new Item("Predator Mask", 175, getString(R.string.predator_description), "Wearable, Shield", 5));
            mHappyMaskDAO.insert(new Item("Wabbajack", 223, getString(R.string.wabbajack_description), "Weapon", 1));
            mHappyMaskDAO.insert(new Item("Windwaker", 300, getString(R.string.windwaker_description), "Key Item", 1));
        }
    }

    private void wireupDistplay(){
        mUsernameField = findViewById(R.id.userText);
        mPasswordField = findViewById(R.id.passwordText);

        mLoginButton = findViewById(R.id.loginButton);
        mFTCAccountButton = findViewById(R.id.FTCAccountButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){//if the user has an account and gives the wrong password
                        Util.makeToast(LoginActivity.this, "Incorrect password.");
                    }else if(mUser.isBanned()==1){//if a banned user tries to login they get a creepy message from MM
                        Util.makeToast(LoginActivity.this, "You've met with a terrible fate, haven't you? Scram, miscreant!");
                    }else if(mUser.getUserTitle().equals("Owner")){
                        Util.makeToast(LoginActivity.this, "Welcome, sire.");
                        Intent intent = DragonActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                        startActivity(intent);//go to Owner's room
                    }else{
                        Intent intent = MainActivity.intentFactory(getApplicationContext(),mUser.getUserId());
                        startActivity(intent);//to the main menu!
                    }
                }
            }
        });

        mFTCAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(intent);//to account creation menu
            }
        });

    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPassword);
    }

    // retrieve user input
    private void getValuesFromDisplay(){
        mUserName = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    // makes sure the user exists
    private boolean checkForUserInDatabase(){
        mUser = mHappyMaskDAO.getUserByUsername(mUserName);
        if(mUser == null){
            Util.makeToast(LoginActivity.this, "no user named " + mUserName + " was found");
            return false;
        }
        return true;
    }


    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

}
