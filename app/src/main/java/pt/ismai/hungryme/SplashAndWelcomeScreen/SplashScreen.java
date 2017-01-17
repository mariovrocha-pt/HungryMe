package pt.ismai.hungryme.SplashAndWelcomeScreen;

import pt.ismai.hungryme.LoginAndRegister.Session;
import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.Recipes.RecipesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        session = new Session(this);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    if(session.loggedin() == true){
                        startActivity(new Intent(SplashScreen.this, RecipesActivity.class));
                        finish();
                    } else {
                    startActivity(new Intent(SplashScreen.this, WelcomeScreen.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
                finish();
            }
        };
        logoTimer.start();
    }

}