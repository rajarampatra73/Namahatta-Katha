package com.Gangasagar.namahatta_katha.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.Gangasagar.namahatta_katha.LottiDialog;
import com.Gangasagar.namahatta_katha.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PolicyActivity extends AppCompatActivity {
    private WebView webView;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference ;
    private LottiDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.ptoolbar);
        toolbar.setTitle(getResources().getString(R.string.privacy_policy));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new LottiDialog(PolicyActivity.this);
        dialog.show();
        webView=findViewById(R.id.p_web);

        reference = FirebaseDatabase.getInstance().getReference().child("policy");

        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue(String.class);
                webView.loadUrl(url);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.show();
                Toast.makeText(PolicyActivity.this,"Failed to load",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();

    }
    @Override
    public void onBackPressed(){
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