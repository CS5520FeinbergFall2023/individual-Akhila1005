package edu.northeastern.numad23fa_akhilachiguluri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMeActivity extends AppCompatActivity {

    TextView About;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        About = findViewById(R.id.About);
        About.setText("Name:Akhila chiguluri\n Email:chiguluri.a@northeastern.edu");
    }
}