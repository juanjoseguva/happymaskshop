package com.example.happymaskshopofhorrors.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.happymaskshopofhorrors.model.Order;
import com.example.happymaskshopofhorrors.R;
import com.example.happymaskshopofhorrors.model.User;
import com.example.happymaskshopofhorrors.db.AppDatabase;
import com.example.happymaskshopofhorrors.db.HappyMaskDAO;
//import com.example.namespace.R;

public class CreateAccountActivity extends AppCompatActivity {

    private HappyMaskDAO mHappyMaskDAO;

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mRePasswordField;
    private EditText mNameField;
    private EditText mAddressField;
    private EditText mTitleField;

    private Button mSignUpButton;

    private String mUsername;
    private String mPassword;
    private String mRePassword;
    private String mName;
    private String mAddress;
    private String mTitle;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getDatabase();
        wireupDistplay();

    }

    private void wireupDistplay() {
        mUsernameField = findViewById(R.id.createUsername);
        mPasswordField = findViewById(R.id.createPass);
        mRePasswordField = findViewById(R.id.repeatPass);
        mNameField = findViewById(R.id.custName);
        mAddressField = findViewById(R.id.userAddress);
        mTitleField = findViewById(R.id.userTitle);

        mSignUpButton = findViewById(R.id.signUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(!checkForUserInDatabase()){
                    if(mPassword.equals(mRePassword)){//set validated user values
                        mHappyMaskDAO.insert(new User(mUsername, mPassword, mName, mTitle, mAddress, 0));//insert new user as client by default
                        Order firstOrder = new Order(mHappyMaskDAO.getUserByUsername(mUsername).getUserId());//create a blank order
                        mHappyMaskDAO.insert(firstOrder);//insert first order
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);//go to login screen
                    }else{// user can try again
                        Toast.makeText(CreateAccountActivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mRePassword = mRePasswordField.getText().toString();
        mName = mNameField.getText().toString();
        mTitle = mTitleField.getText().toString();
        mAddress = mAddressField.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = mHappyMaskDAO.getUserByUsername(mUsername);
        if(mUser != null){//checks if the username is taken already
            Toast.makeText(this, "Username " + mUsername + " already in use.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private void getDatabase(){
        mHappyMaskDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getHappyMaskDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, CreateAccountActivity.class);
        return intent;
    }
}
