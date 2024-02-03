package sanchezfernandez.franciscojose.tarea08;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Esta clase es el Splash de la aplicación.
 * Unicamente muestra el splash durante dos segundo y despueés lanza el intent con la activity princiapal
 * de la app. Durante el splash, se fuerza la orientación PORTRAIT de la pantalla
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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