package com.kelasandroidappsirhafizee.steelfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends AppCompatActivity {

    Handler handler;
     EditText UserLogin, UserPass;
     Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        UserLogin= findViewById(R.id.UserLogin);
        UserPass = findViewById(R.id.UserPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> LoginUser());

    }

    private void LoginUser(){
        String Username = UserLogin.getText().toString();
        String password = UserPass.getText().toString();

        if (Username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(()->{
           try (Connection conn = new  Connectionclass().CONN();
           PreparedStatement stmt = conn.prepareStatement("SELECT username, role FROM SteelFit.dbo.users WHERE username=? AND password=?")){
            stmt.setString(1,Username);
            stmt.setString(2,password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    String role = rs.getString("role");

                    if (role.equals("admin")){
                        /// Go to AdminInterface
                        Intent intent = new Intent(Login.this, AdminInterface.class);
                        startActivity(intent);
                    }else if (role.equals("user")){
                        /// Go to UserInterface
                        Intent intent = new Intent(Login.this, UserInterface.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                }


            } catch (SQLException e){
            throw new RuntimeException(e);
            }
        }).start();
    }
    public void SignupHere(View view){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();

            }
        },3000);
    }

}