package com.Gangasagar.namahatta_katha;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.Gangasagar.namahatta_katha.databinding.ActivityOnlineViewBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class OnlineViewActivity extends AppCompatActivity {
    private String url,h1,desc;
    private TextView tv_h1,tv_desc;
    private ImageView icon;
    private LottiDialog dialog;

    private @NonNull ActivityOnlineViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnlineViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        this.url = bundle.getString("url");
        this.h1 = bundle.getString("h1");
        this.desc = bundle.getString("con");


        setContentView(R.layout.activity_online_view);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
//        toolbar.setTitle(getResources().getString(R.string.s5));
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new LottiDialog(this);
        dialog.show();

            tv_h1 = findViewById(R.id.tv_h1);
            tv_desc = findViewById(R.id.tv_desc);
            icon = findViewById(R.id.con_imageView);



   try {
        tv_h1.setText(h1);
        tv_desc.setText(desc);
       Glide.with(this).load(url).into(icon);
       setIcon(url);
       dialog.dismiss();
   } catch (Exception e) {
       throw new RuntimeException(e);
   }
       /* webView=findViewById(R.id.online_web);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
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
        });*/
    }

    public void setIcon(String url) {
        Glide.with(icon.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.loading)).into(icon);

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