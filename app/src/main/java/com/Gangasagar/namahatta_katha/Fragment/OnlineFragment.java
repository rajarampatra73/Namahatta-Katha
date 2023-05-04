package com.Gangasagar.namahatta_katha.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.Gangasagar.namahatta_katha.Adapter.OnlineAdapter;
import com.Gangasagar.namahatta_katha.Model.OnlineModel;
import com.Gangasagar.namahatta_katha.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OnlineFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout emptyView;
    private String mParam1;
    private String mParam2;

    private LottieAnimationView progressBar;
    private RelativeLayout relativeLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private ArrayList<OnlineModel> onlineModelArrayList;
    private OnlineAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public OnlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    @NonNull
    public static OnlineFragment newInstance(String param1, String param2) {
        OnlineFragment fragment = new OnlineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_online, container, false);
        emptyView = view.findViewById(R.id.On_emptyLayout);

        progressBar=view.findViewById(R.id.progressBar);
        relativeLayout=view.findViewById(R.id.progressLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (onlineModelArrayList.isEmpty()){
                  //  adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(true);
                    relativeLayout.setVisibility(View.VISIBLE);
                }else {
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    relativeLayout.setVisibility(View.GONE);
                }

            }
        });

        recyclerView = view.findViewById(R.id.onlineRecy);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        onlineModelArrayList = new ArrayList<>();
        adapter = new OnlineAdapter(onlineModelArrayList);
        recyclerView.setAdapter(adapter);


        db.collection("Online_Item").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            OnlineModel obj = d.toObject(OnlineModel.class);
                            onlineModelArrayList.add(obj);
                            relativeLayout.setVisibility(View.GONE);
                        }


                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Data Loading Error",Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}