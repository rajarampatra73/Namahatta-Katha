package com.Gangasagar.namahatta_katha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class OnBoardingActivity extends AppCompatActivity {
    //Variables
    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderIntroAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    ImageView close;
    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        loadLocal();
        //Hooks
        viewPager = findViewById(R.id.slider_intero);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_button);

        //Call adapter
        sliderAdapter = new SliderIntroAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        close = findViewById(R.id.skip_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                finish();
            }
        });
        letsGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLanguage();
                close.setVisibility(View.VISIBLE);
            }
        });
        //Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }
   /* public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
*/
    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.active));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0) {
                letsGetStarted.setVisibility(View.GONE);
                close.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                letsGetStarted.setVisibility(View.INVISIBLE);
                close.setVisibility(View.INVISIBLE);
            }  else {
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.slide_in_bottom);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void ChangeLanguage() {

        final String language[] = {"English", "বাংলা", "हिंदी"};
        AlertDialog.Builder builder = new AlertDialog.Builder(OnBoardingActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("en");
                    recreate();
                    Toast.makeText(OnBoardingActivity.this, "Set Language English", Toast.LENGTH_SHORT).show();
                } else if (which == 1) {
                    setLocale("bn");
                    recreate();
                    Toast.makeText(OnBoardingActivity.this, "Set Language বাংলা", Toast.LENGTH_SHORT).show();

                } else if (which == 2) {
                    setLocale("hi");
                    recreate();
                    Toast.makeText(OnBoardingActivity.this, "Set Language हिंदी", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OnBoardingActivity.this, "System Language Error", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().
                getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Language", MODE_PRIVATE).edit();
        editor.putString("app_lang", language);
        editor.apply();
    }

    private void loadLocal() {
        SharedPreferences preferences = getSharedPreferences("Language", MODE_PRIVATE);
        String lang = preferences.getString("app_lang", "");
        setLocale(lang);

    }
}