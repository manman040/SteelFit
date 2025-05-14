package com.kelasandroidappsirhafizee.steelfit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddTrainer extends AppCompatActivity {

    /// Declare

    Connection con;
    String str;
    private TextView connectionStat;

    EditText AddTrainerName, AddTrainerAge, AddTrainerExp;
    Spinner spGender, spSpecial;
    Button btnSubmitImg, btnSave;
    ImageView profileimg;

    Bitmap profileImg1;

    public static final int IMAGE_PICK_REQUEST = 101;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_trainer);

        AddTrainerName = findViewById(R.id.AddTrainerName);
        AddTrainerAge = findViewById(R.id.AddTrainerAge);
        AddTrainerExp = findViewById(R.id.AddTrainerExp);
        spGender = findViewById(R.id.spGender);
        spSpecial = findViewById(R.id.spSpecial);
        btnSubmitImg = findViewById(R.id.btnSubmitImg);
        btnSave = findViewById(R.id.btnSave);
        btnSubmitImg.setOnClickListener(v -> {
            Toast.makeText(this, "Insert Image", Toast.LENGTH_SHORT).show();
            selectImage();
        });

        btnSave.setOnClickListener(v -> {
            Toast.makeText(this, "Data save!", Toast.LENGTH_SHORT).show();
            insertData();
        });


        connectionStat = findViewById(R.id.connectionstatus);
        Connectionclass connectionclass = new Connectionclass();
        con = connectionclass.CONN();
        connect();
        if (btnSave == null) {
            Log.e("ButtonError", "btnSubmit is NULL. Check R.id.btnSubmit in XML");
        }
        btnSave.setEnabled(true);

        ArrayAdapter<CharSequence> genderadapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderadapter);

        ArrayAdapter<CharSequence> specialadapter = ArrayAdapter.createFromResource(this,
                R.array.special_array, android.R.layout.simple_spinner_item);
        specialadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpecial.setAdapter(specialadapter);

    }

    private void navigateTo(Class<?> Activity, int selectedItem) {
        Intent intent = new Intent(this, Activity);
        intent.putExtra("selected_item", selectedItem);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_REQUEST);
    }
    private byte[] imageToByteArray(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }
    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                if (con == null) {
                    str = "Error";
                } else {
                    connectionStat.setText("Connnection Success!");
                    str = "Connection with SQL Server";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_REQUEST){
            Uri imageUri = data.getData();
                try {
                    profileImg1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    profileimg.setImageBitmap(profileImg1);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
     private void insertData(){
         Log.d("Submit", "Insert Data");

         //Retrieve value from ui Component
         String name = AddTrainerName.getText().toString().trim(); //edittext
         String age = AddTrainerAge.getText().toString().trim();
         String experience = AddTrainerExp.getText().toString().trim();
         String gender = spGender.getSelectedItem().toString(); // spinner
         String specialization = spSpecial.getSelectedItem().toString(); // spinner2

         new Thread(()->{
            try (Connection con = new Connectionclass().CONN()){
                String checkQuery = "SELECT COUNT(*) FROM SteelFit.dbo.trainers WHERE name = ?";
                PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                checkStmt.setString(1, name);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                if (count > 0 ) {
                    runOnUiThread(()->
                            Toast.makeText(AddTrainer.this,"Name Already Exist!", Toast.LENGTH_SHORT).show()

                            );
                    return;
                }
                ///insert new user if name does not exist
                String query = "INSERT INTO SteelFit.dbo.trainers (name, age, specialization, experience_years, gender) VALUES (?,?,?,?,?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1,name);
                stmt.setInt(2, Integer.parseInt(age));
                stmt.setString(3,specialization);
                stmt.setString(4,experience);
                stmt.setString(5, gender);
                stmt.executeUpdate();
                runOnUiThread(()->{
                    Toast.makeText(AddTrainer.this, "Data successful save", Toast.LENGTH_SHORT).show();

                    AddTrainerName.setText("");
                    AddTrainerAge.setText("");
                    AddTrainerExp.setText("");
                    spGender.setSelection(0);
                    spSpecial.setSelection(0);
                    profileimg.setImageResource(android.R.color.transparent);
                    profileImg1 = null;

                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
         }).start();


     }
}