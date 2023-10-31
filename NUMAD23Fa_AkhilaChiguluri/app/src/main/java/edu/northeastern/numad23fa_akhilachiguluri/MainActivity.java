package edu.northeastern.numad23fa_akhilachiguluri;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
Button aboutMe;
Button Clicky;
Button LC;
Button prime_num;
Button location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         aboutMe = findViewById(R.id.aboutMe);

        aboutMe .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutMeActivity();
                //String my_name = "Akhila Chiguluri";
                //String my_emailId = "chiguluri.a@northeastern.edu";
                //String message_to_be_toasted = "Name: " + my_name + "\nEmail Id: "+ my_emailId;
                //Toast.makeText(MainActivity.this, message_to_be_toasted, Toast.LENGTH_SHORT).show();
            }
        });

        Clicky = findViewById(R.id.Clicky);
        Clicky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();
            }
        });

        LC = findViewById(R.id.LC);
        LC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLcActivity();
            }
        });
        prime_num = findViewById(R.id.prime_num);
        prime_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrimeActivity();
            }
        });
        location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLocationActivity();
            }
        });
        }



        public void openAboutMeActivity(){
        Intent i = new Intent(this, AboutMeActivity.class);
        startActivity(i);
        }
    public void openNewActivity(){
        Intent i = new Intent(this,NewActivity.class);
        startActivity(i);
    }
    public void openLcActivity(){
        Intent i = new Intent(this,LcActivity.class);
       startActivity(i);
    }
    public void openPrimeActivity(){
        Intent i = new Intent(this, Prime_num_activity.class);
        startActivity(i);
    }
    public void openLocationActivity(){
        Intent i = new Intent(this, LocationActivity.class);
        startActivity(i);
    }

}