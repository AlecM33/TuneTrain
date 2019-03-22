package com.example.alec.tunetrain;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alec.tunetrain.Entities.Template;
import java.util.ArrayList;
import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static final String TAG = "LandingPageActivity";
    private Button mStatsButton;
    private Button mTrainingButton;
    private Button mSandboxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.landing_page);

        //event listener for statsButton
        mStatsButton = (Button)findViewById(R.id.stats_button);
        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatsActivity
                Intent intent = new Intent(LandingPageActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        mTrainingButton = (Button)findViewById(R.id.training_button);
        mTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start StatsActivity
                Intent intent = new Intent(LandingPageActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        mSandboxButton = (Button)findViewById(R.id.sandbox_button);
        mSandboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SandboxActivity
                Intent intent = new Intent(LandingPageActivity.this, SandboxActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
