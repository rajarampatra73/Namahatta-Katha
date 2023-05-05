package com.Gangasagar.namahatta_katha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.Gangasagar.namahatta_katha.databinding.ActivityOnlineViewBinding;
import com.Gangasagar.namahatta_katha.databinding.ActivityWebViewBinding;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;

public class WebViewActivity extends AppCompatActivity {
    private String url;

    private WebView webView;

    private LottiDialog dialog;
    private @NonNull ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        this.url = bundle.getString("url");
        setContentView(R.layout.activity_web_view);

        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                this,
                getLifecycle()
        );

        DialogPropertiesSignal properties = builder.getDialogProperties();

        properties.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });

        properties.setCancelable(false); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional

        builder.build();


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWeb);
        setSupportActionBar(toolbar);
//        toolbar.setTitle(getResources().getString(R.string.s5));
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new LottiDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        webView = findViewById(R.id.web);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                dialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}