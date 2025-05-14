package com.kelasandroidappsirhafizee.steelfit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserInterface extends AppCompatActivity {

    Button btnViewBook, btnBookingTrainer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_int);
        btnViewBook = findViewById(R.id.btnViewBook);
        btnBookingTrainer = findViewById(R.id.btnAddTrainer);

        btnBookingTrainer.setOnClickListener(v -> {
            Intent intent = new Intent(UserInterface.this, BookingActivity.class);
            startActivity(intent);
        });

        btnViewBook.setOnClickListener( v -> {
            Intent intent = new Intent(UserInterface.this, ViewBooking.class);
            startActivity(intent);
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        int selectedItem = getIntent().getIntExtra("selected_item", R.id.nav_home);
        bottomNavigationView.setSelectedItemId(selectedItem);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home && selectedItem != R.id.nav_home){
                navigateTo (ProfileUser.class, item.getItemId());
            }else if (item.getItemId() == R.id.nav_profile && selectedItem != R.id.nav_profile){
                navigateTo(ProfileUser.class, item.getItemId());
            }
            return true;
            });
    }
    private void navigateTo(Class<?> Activity, int selectedItem){
        Intent intent = new Intent(this, Activity);
        intent.putExtra("selected_item", selectedItem);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}