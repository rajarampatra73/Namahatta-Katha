package com.Gangasagar.namahatta_katha.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.Gangasagar.namahatta_katha.LottiDialog;
import com.Gangasagar.namahatta_katha.Adapter.NotiAdapter;
import com.Gangasagar.namahatta_katha.Model.NotiModel;
import com.Gangasagar.namahatta_katha.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
   private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private NotiAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<NotiModel> notiModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.notiRecy);

        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.notitoolbar);
        toolbar.setTitle(getResources().getString(R.string.notification));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        emptyView = findViewById(R.id.emptyLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        notiModelsList = new ArrayList<>();
        adapter = new NotiAdapter(notiModelsList);
     //   recyclerView.setAdapter(adapter);


        // Step 4: Check the data source of the RecyclerView
        if (notiModelsList.isEmpty()) {
            // Show the empty view
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            // Show the RecyclerView
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

// Set up the RecyclerView adapter and layout manager
        recyclerView.setAdapter(adapter);

        //////////////////
        db= FirebaseFirestore.getInstance();
        db.collection("Notification").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            NotiModel obj = d.toObject(NotiModel.class);
                            notiModelsList.add(obj);
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        }

                        adapter.notifyDataSetChanged();
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