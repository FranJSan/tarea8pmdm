package sanchezfernandez.franciscojose.tarea08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView evSplash = findViewById(R.id.ivSplash);
        evSplash.setImageResource(R.drawable.splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        animation.start();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, MainActivity.class
                );

                startActivity(mainIntent);

                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 2000);

    }
}