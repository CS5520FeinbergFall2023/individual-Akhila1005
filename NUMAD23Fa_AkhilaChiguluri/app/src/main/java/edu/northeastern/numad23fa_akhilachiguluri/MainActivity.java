package edu.northeastern.numad23fa_akhilachiguluri;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutMe = findViewById(R.id.aboutMe);
        Button Clicky = findViewById(R.id.Clicky);
        aboutMe .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my_name = "Akhila Chiguluri";
                String my_emailId = "chiguluri.a@northeastern.edu";
                String message_to_be_toasted = "Name: " + my_name + "\nEmail Id: "+ my_emailId;
                Toast.makeText(MainActivity.this, message_to_be_toasted, Toast.LENGTH_SHORT).show();
            }
        });
        Clicky. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();
            }
        });

        }
    public void openNewActivity(){
        Intent i = new Intent(this,NewActivity.class);
        startActivity(i);
    }

}