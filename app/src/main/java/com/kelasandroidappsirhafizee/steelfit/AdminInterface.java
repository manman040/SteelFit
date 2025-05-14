package com.kelasandroidappsirhafizee.steelfit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminInterface extends AppCompatActivity {

    Button btnAddTrainer, btnManageTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_int);

        btnAddTrainer = findViewById(R.id.btnAddTrainer);
        btnManageTrainer = findViewById(R.id.btnManageTrainer);

        btnAddTrainer.setOnClickListener(v->{
            Intent intent = new Intent(AdminInterface.this, AddTrainer.class);
            startActivity(intent);
        });
        btnManageTrainer.setOnClickListener(v -> {
            Intent intent = new Intent(AdminInterface.this, ManageTrainer.class);
            startActivity(intent);
        });



    }
}