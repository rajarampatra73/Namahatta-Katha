package com.Gangasagar.namahatta_katha.Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.Gangasagar.namahatta_katha.LottiDialog;
import com.Gangasagar.namahatta_katha.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.ByteArrayOutputStream;

public class DonationActivity extends AppCompatActivity {
    private LinearLayout Ads;
    private TextView donation;
    private TextView adsCount;
    private TextView conDecs,conTitle;
    private ImageView conIcon;
   // LottieAnimationView progressBar;
    private ImageButton copy,adsShare;
    private RewardedAd mRewardedAd;
    private final String TAG = "--->AdMob";
    private LottiDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_donation);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conDecs = findViewById(R.id.donDecs);
        conTitle = findViewById(R.id.donTitle);
        conIcon = findViewById(R.id.donIcon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Don_toolbar);
        toolbar.setTitle(getResources().getString(R.string.support));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Ads = (LinearLayout) findViewById(R.id.Adses_Button);
        adsCount = (TextView) findViewById(R.id.adses_count);
        copy = findViewById(R.id.copy);
        adsShare = findViewById(R.id.adses_share);

        dialog = new LottiDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        adsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BitmapDrawable drawable=(BitmapDrawable) conIcon.getDrawable();
//                Bitmap bitmap=drawable.getBitmap();
//
//                String title = getString(R.string.app_name);
//                String bitp= MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"Namahatta Katha","Namahatta Katha App");
//                Uri uri = Uri.parse(bitp);
                final String appPackageName = getApplication().getPackageName();
                String shareBody = getString(R.string.donation) + " " + "http://play.google.com/store/apps/details?id=" + appPackageName;
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/png");
//                intent.putExtra(Intent.EXTRA_STREAM,uri);
//                intent.putExtra(Intent.EXTRA_TEXT, title);
//                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(intent,"share"));
                // Get a reference to the image you want to share
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.qr);

                String title = getString(R.string.app_name);
// Create a sharing intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");

// Add the image to the intent as a URI
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), image, title, "");
                Uri imageUri = Uri.parse(path);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, title);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
// Launch the share menu
                startActivity(Intent.createChooser(shareIntent, "Share Image"));

            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copy",conTitle.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(DonationActivity.this,"Successfully Copy",Toast.LENGTH_SHORT).show();
            }
        });
        //castom Toast sms
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_sms,
                (ViewGroup) findViewById(R.id.toasts));
        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);

//        progressBar = findViewById(R.id.LottieAnimationView);



//
//        try {
////            conTitle.setText(upi);
////            conDecs.setText(content);
////          //  Glide.with(this).load(qr).into(conIcon);
////            dialog.dismiss();
//        } catch (Exception e) {
//            Toast.makeText(DonationActivity.this,"Connectivity issue",Toast.LENGTH_SHORT).show();
//            dialog.show();
//            throw new RuntimeException(e);
//
//        }

//        Picasso.get().load(qr).into(conIcon);
//        Picasso.get().load(qr)
//                .into(conIcon, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        // Hide progress bar
//                        progressBar.setVisibility(View.GONE);
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        progressBar.setVisibility(View.VISIBLE);
//                        Toast.makeText(DonationActivity.this,"Connectivity issue",Toast.LENGTH_SHORT).show();
//                        // Handle error
//                    }
//                });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Adsloading();
            }
        });
        Ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (mRewardedAd != null) {
                    Activity activityContext = DonationActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                            int Reward =Integer.parseInt(adsCount.getText().toString().trim());

                            adsCount.setText(String.valueOf(Reward +1));
                            dialog.dismiss();
                        }
                    });
                } else {
                    toast.show();
                    dialog.dismiss();
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });

    }

    private void Adsloading() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, getString(R.string.RewardedAdsId),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;

                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        dialog.dismiss();
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
//                                mRewardedAd = null;
                                //Click on Ads Reloaded
                                // Adsloading();
                            }
                        });
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}