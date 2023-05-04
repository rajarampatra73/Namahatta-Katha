package com.Gangasagar.namahatta_katha.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.Gangasagar.namahatta_katha.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {
    private PhotoView image_view_activity_image;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        this.url = bundle.getString("url");
        setContentView(R.layout.activity_photo_view);



//        if (getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.photoToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.image_view_activity_image = (PhotoView) findViewById(R.id.image_view_activity_image);
        Glide.with(this).load(url).into(image_view_activity_image);

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
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}