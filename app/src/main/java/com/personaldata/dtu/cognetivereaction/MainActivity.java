package com.personaldata.dtu.cognetivereaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int TOTAL_TRIALS = 10;

    private RelativeLayout bgRelativeLayout;
    private TextView tvTitle, tvTries, tvBullet1, tvBullet2;
    private boolean firstBoot = true; // First run flag
    private boolean counterStarted = false; // Reaction time counter started flag
    long tStart; // Reaction time
    int tries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity", "Test");

        bgRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        tvTitle = (TextView) findViewById(R.id.textView);
        tvTries = (TextView) findViewById(R.id.textView2);
        tvBullet1 = (TextView) findViewById(R.id.editText);
        tvBullet2 = (TextView) findViewById(R.id.editText2);

        final Context c = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bgRelativeLayout.setBackgroundColor(Color.RED);
                //startActivity(new Intent(c, Reaction.class));

                // First boot -> Wait for it -> Click too soon OR Click! -> ms -> Wait for it

                if (firstBoot == true) {
                    firstBoot = false;

                    // Change textviews
                    tvTitle.setText("WAIT FOR IT...");
                    tvBullet1.setVisibility(View.INVISIBLE);
                    tvBullet2.setVisibility(View.INVISIBLE);

                    // Random number between 1-3 seconds
                    Random rand = new Random();
                    int randSwitcher = rand.nextInt(3000 - 1000) + 1000;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            bgRelativeLayout.setBackgroundColor(Color.RED);
                            tvTitle.setText("CLICK!");

                            counterStarted = true;
                            tStart = SystemClock.elapsedRealtime();
                        }
                    }, randSwitcher);

                } else if (counterStarted == true && tries != TOTAL_TRIALS) {
                    long endTime = SystemClock.elapsedRealtime();
                    long elapsedMilliSeconds = endTime - tStart;
                    double elapsedSeconds = elapsedMilliSeconds / 1000.0;
                    tries++;

                    tvTitle.setText(elapsedSeconds + " ms");
                    tvBullet2.setVisibility(View.VISIBLE);
                    tvBullet2.setText("Tap again for next trial");
                    tvTries.setText(tries + " / " + TOTAL_TRIALS);
                    tStart = 0;
                    counterStarted = false;
                    firstBoot = true;
                    bgRelativeLayout.setBackgroundColor(Color.parseColor("#FF0099CC"));
                }

                if(tries >= TOTAL_TRIALS) {
                    tvTitle.setText("FINISHED. AVG etc.");
                    tvBullet2.setVisibility(View.INVISIBLE);
                    counterStarted = false;
                    firstBoot = false;
                }


            }
        };

        bgRelativeLayout.setOnClickListener(listener);
    }
}
