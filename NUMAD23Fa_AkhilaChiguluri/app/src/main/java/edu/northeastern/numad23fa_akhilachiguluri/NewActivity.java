package edu.northeastern.numad23fa_akhilachiguluri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Button A = findViewById(R.id.A);
        Button B = findViewById(R.id.B);
        Button C = findViewById(R.id.C);
        Button D = findViewById(R.id.D);
        Button E = findViewById(R.id.E);
        Button F = findViewById(R.id.F);
        TextView P = findViewById(R.id.P);
        A. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: A");
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: B");
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: C");
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: D");
            }
        });
        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: E");
            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P.setText("Pressed: F");
            }
        });
    }
}