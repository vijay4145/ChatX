package com.example.chatx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chatx.account_setup.login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LottieAnimationView textView = findViewById(R.id.lottieAnimationView);
                Intent intent = null;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }else{
                    intent = new Intent(SplashScreen.this, login.class);
                }
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, new Pair<View, String>(textView, "app_name"));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000L);
                startActivity(intent, options.toBundle());
            }
        },2000L);
    }
}