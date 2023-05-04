package com.Gangasagar.namahatta_katha;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Gangasagar.namahatta_katha.Activity.DonationActivity;
import com.Gangasagar.namahatta_katha.Activity.FeedbackActivity;
import com.Gangasagar.namahatta_katha.Activity.NamhattaActivity;
import com.Gangasagar.namahatta_katha.Activity.Notification;
import com.Gangasagar.namahatta_katha.Activity.PolicyActivity;
import com.Gangasagar.namahatta_katha.Activity.WebActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FirebaseRemoteConfig firebaseRemoteConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                } catch (Exception exception) {
                    exception.printStackTrace();

                } finally {
                    // new version
                    int currentVersion;
                    currentVersion = getCurrentVersion();
                    Log.d("myapp", String.valueOf(currentVersion));
                    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(5).build();
                    firebaseRemoteConfig.setConfigSettingsAsync(configSettings);

                    firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            final String new_version = firebaseRemoteConfig.getString("new_update");
                            if (Integer.parseInt(new_version) > getCurrentVersion()) {
                                showUpdateDilog();
                            }
                        }
                    });
                }
            }
        };
        thread.start();

//
        loadLocal();

        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));


        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                if (id == R.id.nav_author) {
                    drawer.closeDrawer(GravityCompat.START);
                    Intent namtaInt = new Intent(MainActivity.this, NamhattaActivity.class);
                    startActivity(namtaInt);

                } else if (id == R.id.nav_share) {
                    final String appPackageName = getApplication().getPackageName();
                    String shareBody = getString(R.string.share) + " " + getString(R.string.app_name) + " From :  " + "http://play.google.com/store/apps/details?id=" + appPackageName;
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));

                } else if (id == R.id.nav_donation) {
                    drawer.closeDrawer(GravityCompat.START);
                    // replace HelpActivity to DonationActivity
                    Intent intent = new Intent(MainActivity.this, DonationActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_setting) {
                    drawer.closeDrawer(GravityCompat.START);
                    showBottomDialog();
                } else if (id == R.id.nav_language) {
                    drawer.closeDrawer(GravityCompat.START);
                    ChangeLanguage();
                    return true;
                } else if (id == R.id.nav_contact_us) {
                    drawer.closeDrawer(GravityCompat.START);
                    Dialog about = new Dialog(MainActivity.this);
                    about.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    about.setContentView(R.layout.about_app);
                    about.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    about.show();
                    Button wh_button = about.findViewById(R.id.wh_button);
                    Button play_button = about.findViewById(R.id.play_button);
                    Button email_button = about.findViewById(R.id.email_button);

                    wh_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String contact = "+91 9832608690"; // use country code with your phone number
                            String url = "https://api.whatsapp.com/send?phone=" + contact;
                            try {
                                PackageManager pm = getApplicationContext().getPackageManager();
                                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            } catch (PackageManager.NameNotFoundException e) {
                                Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            about.dismiss();
                        }
                    });
                    email_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "namahattakatha@gmail.com"));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                //TODO smth
                                Toast.makeText(MainActivity.this, "Gmail app not setup in your phone", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            about.dismiss();
                        }
                    });
                    play_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=" + getApplication().getPackageName())));
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
                            }
                            about.dismiss();
                        }
                    });
                } else if (id == R.id.nav_feedback) {
                    drawer.closeDrawer(GravityCompat.START);
                    Intent wayIntent = new Intent(MainActivity.this, FeedbackActivity.class);
                    startActivity(wayIntent);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    // new update version
    private void showUpdateDilog() {
        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("New Update Is Available")
                .setMessage("Update Now")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // cancel alert dialog
                        dialogInterface.cancel();
                    }
                })

                .setPositiveButton("Update", (dialogInterface, i) -> {
                    try {
                        final String appPackageName = getApplication().getPackageName();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }).show();

        dialog.setCancelable(false);

    }

    private int getCurrentVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (Exception e) {
            Log.d(getPackageName(), e.getMessage());
        }
        assert packageInfo != null;
        return packageInfo.versionCode;
    }

    //new update version end
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notification) {
            Intent intent = new Intent(this, Notification.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_languages) {
            ChangeLanguage();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void ChangeLanguage() {

        final String language[] = {"English", "বাংলা", "हिंदी"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Language");
        builder.setSingleChoiceItems(language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("en");
                    recreate();
                    Toast.makeText(MainActivity.this, "Set Language English", Toast.LENGTH_SHORT).show();
                } else if (which == 1) {
                    setLocale("bn");
                    recreate();
                    Toast.makeText(MainActivity.this, "Set Language বাংলা", Toast.LENGTH_SHORT).show();

                } else if (which == 2) {
                    setLocale("hi");
                    recreate();
                    Toast.makeText(MainActivity.this, "Set Language हिंदी", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "System Language Error", Toast.LENGTH_SHORT).show();

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

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_item_layout);

        LinearLayout privacyLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout contactLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        LinearLayout language = dialog.findViewById(R.id.layoutLanguage);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLanguage();
                dialog.dismiss();
            }
        });
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog about = new Dialog(MainActivity.this);
                about.requestWindowFeature(Window.FEATURE_NO_TITLE);
                about.setContentView(R.layout.about_app);
                about.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                about.show();
                Button wh_button = about.findViewById(R.id.wh_button);
                Button play_button = about.findViewById(R.id.play_button);
                Button email_button = about.findViewById(R.id.email_button);

                wh_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String contact = "+91 9832608690"; // use country code with your phone number
                        String url = "https://api.whatsapp.com/send?phone=" + contact;
                        try {
                            PackageManager pm = getApplicationContext().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (PackageManager.NameNotFoundException e) {
                            Toast.makeText(MainActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        about.dismiss();
                    }
                });
                email_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "namahattakatha@gmail.com"));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            //TODO smth
                            Toast.makeText(MainActivity.this, "Gmail app not setup in your phone", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        about.dismiss();
                    }
                });
                play_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getApplication().getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
                        }
                        about.dismiss();
                    }
                });
                dialog.dismiss();

            }
        });


        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wayIntent = new Intent(MainActivity.this, PolicyActivity.class);
                startActivity(wayIntent);
                dialog.dismiss();

            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(webIntent);
                dialog.dismiss();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
    }
}