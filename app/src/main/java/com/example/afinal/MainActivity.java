package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;

public class MainActivity extends AppCompatActivity {

    private static int splash = 4000;

    TextView textview_english_java,textview_kannada_java;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        activitdelay();
    }

    private void animation(){
        Animation english_animate = AnimationUtils.loadAnimation(this,R.anim.english_animtion);
        Animation kannada_animate = AnimationUtils.loadAnimation(this,R.anim.kannada_animation);
        textview_english_java.startAnimation(english_animate);
        textview_kannada_java.startAnimation(kannada_animate);
    }
    private void activitdelay() {
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,UserPage.class));
                finish();
            }
        },splash);
    }
    private void init(){
        textview_english_java = findViewById(R.id.textview_english_xml);
        textview_kannada_java = findViewById(R.id.textview_kannada_xml);
    }
}
