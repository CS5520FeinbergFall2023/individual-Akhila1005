package edu.northeastern.numad23fa_akhilachiguluri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Prime_num_activity extends AppCompatActivity {
    private TextView current_num_text_view;
    private TextView recent_prime_text_view;
    private Button find_prime_num;
    private Button terminate_search;
    private CheckBox pacify_search;
    private volatile boolean isSearching;
    private volatile int current_num = 3;
    private volatile int latest_prime;
    private Handler main_handler;
    private Thread primary_search_thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_num);

        current_num_text_view = findViewById(R.id.current_num);
        recent_prime_text_view = findViewById(R.id.latest_num);
        pacify_search = findViewById(R.id.pacifier_search);
        find_prime_num = findViewById(R.id.find_prime_num);
        terminate_search = findViewById(R.id.terminate_search);

        main_handler = new Handler(Looper.getMainLooper());
        isSearching = false;


        find_prime_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_num = 3;
                latest_prime = 3;
                updateUI();
                startPrimeSearch();
            }
        });

        terminate_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopPrimeSearch();
            }
        });

        if (savedInstanceState != null) {
            isSearching = savedInstanceState.getBoolean("isSearching");
            current_num = savedInstanceState.getInt("currentNumber");
            latest_prime = savedInstanceState.getInt("latestPrime");

            updateUI();
            if (isSearching) {
                find_prime_num.setEnabled(false);
                startPrimeSearch();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearching) {
            showTerminationConfirmationDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showTerminationConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to terminate the search?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopPrimeSearch();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nothing as of now
            }
        });
        builder.create().show();
    }

    private void startPrimeSearch() {
        isSearching = true;
        find_prime_num.setEnabled(false);
        terminate_search.setEnabled(true);


        primary_search_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int numbersChecked = 0;

                while (isSearching) {
                    if (isPrime(current_num)) {
                        latest_prime = current_num;


                        updateUI();
                    }
                    current_num+=1;
                    for (int i=0;i<10000000;i++){
                        int z=0;
                        z+=1;
                    }
                }

            }


        });
        primary_search_thread.start();
    }

    private void stopPrimeSearch() {
        isSearching = false;


        if (primary_search_thread != null) {
            primary_search_thread.interrupt();
        }


        primary_search_thread = null;
        updateUI();
        find_prime_num.setEnabled(true);
        terminate_search.setEnabled(false);
    }

    private void updateUI() {
        main_handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                current_num_text_view.setText("Current Number: " + current_num);
                recent_prime_text_view.setText("Latest Prime: " + latest_prime);
            }
        });
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;

        for (int i = 2; i < num; i++)
            if (num % i == 0)
                return false;

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isSearching", isSearching);
        outState.putInt("currentNumber", current_num);
        outState.putInt("latestPrime", latest_prime);
    }
}