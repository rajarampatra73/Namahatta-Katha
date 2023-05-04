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

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWeb);
        setSupportActionBar(toolbar);
//        toolbar.setTitle(getResources().getString(R.string.s5));
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new LottiDialog(this);
        dialog.show();
        webView = findViewById(R.id.web);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
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