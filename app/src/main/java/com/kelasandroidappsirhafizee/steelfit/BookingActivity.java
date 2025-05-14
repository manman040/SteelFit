package com.kelasandroidappsirhafizee.steelfit;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    ///  DECLARE LU

    private RecyclerView recyclerView;
    private BookingAdapter BookingAdapter;
    private List<BookingModel> trainerList = new ArrayList<>();

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainerList = new ArrayList<>();
        BookingAdapter = new BookingAdapter(trainerList);
        recyclerView.setAdapter(BookingAdapter);

        con = new Connectionclass().CONN();

        fetchData();
    }
    private void fetchData(){
        new Thread(()->{
           try {
               Connectionclass connectionclass = new Connectionclass();
               Connection con = connectionclass.CONN();
               if (con == null){
                   runOnUiThread(()-> Toast.makeText(this, "Database Connection failed!", Toast.LENGTH_SHORT).show());
                   return;
               }
               String query = "SELECT id, name, age, specialization, experience_years, profileimage FROM SteelFit.dbo.trainers";
               PreparedStatement stmt = con.prepareStatement(query);
               ResultSet rs = stmt.executeQuery();

               List<BookingModel> updatedTrainerList = new ArrayList<>();
               while (rs.next()){
                   updatedTrainerList.add(new BookingModel(
                           rs.getInt("id"),
                           rs.getString("name"),
                           rs.getInt("age"),
                           rs.getString("specialization"),
                           rs.getString("experience_years"),
                           rs.getBytes("profileimage")
                   ));
               }
               runOnUiThread(()->{
                   trainerList.clear();
                   trainerList.addAll(updatedTrainerList);
                   BookingAdapter.notifyDataSetChanged();
               });
           }catch (Exception e){
               e.printStackTrace();
               runOnUiThread(()-> Toast.makeText(BookingActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show());
           }
        }).start();
    }
    protected void onResume(){
        super.onResume();
        fetchData();
    }
}