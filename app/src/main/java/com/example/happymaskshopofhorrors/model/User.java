package com.example.happymaskshopofhorrors.model;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.happymaskshopofhorrors.db.AppDatabase;
/*
Represents a user of the Happy Mask Shop, client or shopkeep
 */
@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;
    private String mUsersName;
    private String mUserTitle;
    private String mAddress;//if owner, this is the hoard in goods.
    private int isAdmin;
    private int isBanned;//if owner, this is hoard in gold.

    public User(String mUsername, String mPassword, String mUsersName, String mUserTitle, String mAddress, int isAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mUsersName = mUsersName;
        this.mUserTitle = mUserTitle;
        this.mAddress = mAddress;
        this.isAdmin = isAdmin;
        this.isBanned = 0;;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {return mUsername;}

    public void setUserName(String mUserName) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getUsersName() {
        return mUsersName;
    }

    public void setUsersName(String mUsersName) {
        this.mUsersName = mUsersName;
    }

    public String getUserTitle() {
        return mUserTitle;
    }

    public void setUserTitle(String mUserTitle) {
        this.mUserTitle = mUserTitle;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }

    public int isBanned(){
        return isBanned;
    }

    public void setIsBanned(int banned) {isBanned = banned;}

    public void banHammer() {
        isBanned = 1;
    }
}
