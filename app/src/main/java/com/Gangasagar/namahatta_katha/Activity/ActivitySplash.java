package com.Gangasagar.namahatta_katha.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.Gangasagar.namahatta_katha.MainActivity;
import com.Gangasagar.namahatta_katha.OnBoardingActivity;
import com.Gangasagar.namahatta_katha.R;
import com.airbnb.lottie.LottieAnimationView;

public class ActivitySplash extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.splash_img);
        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        // requestWindowFeature(1);

//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (preferences.getBoolean("Intro",false)){
//                    startActivity(new Intent(ActivitySplash.this,MainActivity.class));
//                    finish();
//                }else {
//                    editor.putBoolean("Intro",true);
//                    editor.apply();
//
//                    TaskStackBuilder.create(ActivitySplash.this)
//                            .addNextIntentWithParentStack(new Intent(ActivitySplash.this,MainActivity.class))
//                            .addNextIntent(new Intent(ActivitySplash.this, MainIntroActivity.class))
//                            .startActivities();
//                }
//            }
//        },1500);


   Thread thread = new Thread(){
            public void run(){
                try {  sleep(1500);

                }catch (Exception exception){
                    exception.printStackTrace();

                }finally {
                    preferences = getSharedPreferences("onBoarding",MODE_PRIVATE);
                    boolean isFirst = preferences.getBoolean("first",true);

                    if (isFirst) {
                        editor = preferences.edit();
                        editor.putBoolean("first",false);
                        editor.commit();
                        Intent intent = new Intent(ActivitySplash.this, OnBoardingActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        };
        thread.start();


    }

}