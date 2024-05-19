package com.example.happymaskshopofhorrors;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/*
Basically just a toaster. I thought I might add more but for now it just cooks bread twice.
 */
public class Util{

    public static void makeToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.NO_GRAVITY,0,0);
        toast.show();
    }
}
