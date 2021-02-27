package com.codefestfinal.codefest21_2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.codefestfinal.codefest21_2.databinding.ActivityMainBinding;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.tickt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("");
                Tickets t = new Tickets();
                fragmentTransaction.replace(R.id.mainLayout, t, "Tickets");
                fragmentTransaction.commit();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack("");
        LogIn logIn = new LogIn();
        fragmentTransaction.replace(R.id.mainLayout, logIn, "LogIn");
        fragmentTransaction.commit();



    }

    void hideToolbar(){
        mainBinding.tickt.setVisibility(View.GONE);


    }

    void showToolbar(){
        mainBinding.tickt.setVisibility(View.VISIBLE);


    }


}