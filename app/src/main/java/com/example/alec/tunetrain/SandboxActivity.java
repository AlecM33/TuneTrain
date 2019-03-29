package com.example.alec.tunetrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutionException;

public class SandboxActivity extends AppCompatActivity {

    private String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.newName = extras.getString("newTemplate");
            //The key argument here must match that used in the other activity
        }
        loadSoundBoardFragment();
    }

    public void loadSoundBoardFragment () {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new SoundBoardFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Mode", "Sandbox");
            bundle.putString("templateName", this.newName);

            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
