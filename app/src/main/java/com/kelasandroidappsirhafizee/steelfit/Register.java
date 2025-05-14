package com.kelasandroidappsirhafizee.steelfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ThemedSpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends AppCompatActivity {

    EditText RegUser, RegEmail, RegPhone, RegPass, RegConfPass;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        RegUser = findViewById(R.id.RegUser);
        RegEmail = findViewById(R.id.RegEmail);
        RegPhone = findViewById(R.id.RegPhone);
        RegPass = findViewById(R.id.RegPass);
        RegConfPass = findViewById(R.id.RegConfPass);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> registerUser());

    }
    private void registerUser(){
        String username = RegUser.getText().toString();
        String email = RegEmail.getText().toString();
        String phone = RegPhone.getText().toString();
        String password = RegPass.getText().toString();
        String confirmpassword = RegConfPass.getText().toString();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmpassword.isEmpty()){
            Toast.makeText(this, "Isi Tempat Kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(()->{
            try (Connection conn = new Connectionclass().CONN();
                 PreparedStatement stmt = conn.prepareStatement("" +
                         "INSERT INTO SteelFit.dbo.users (username, password, email, phone, role ) values (?,?,?,?,?)")){
                stmt.setString(1,username);
                stmt.setString(2,password);
                stmt.setString(3,email);
                stmt.setString(4,phone);
                stmt.setString(5,"user");

                int rowsAffected = stmt.executeUpdate();
                runOnUiThread(()->{
                    if (rowsAffected > 0) {
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    }else {
                        Toast.makeText(Register.this, "Registration Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    public void LogInhere(View view) {
        Intent intent = new Intent( Register.this, Login.class);
        startActivity(intent);
        finish();
    }
}