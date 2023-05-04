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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.Gangasagar.namahatta_katha.Adapter.QnaAdapter;
import com.Gangasagar.namahatta_katha.R;
import com.Gangasagar.namahatta_katha.Model.qnaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QandNFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QandNFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView progressBar;
    private RelativeLayout relativeLayout;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private QnaAdapter adapter;
    private LinearLayout emptyView;
    ArrayList<qnaModel> models;

    public QandNFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QandNFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QandNFragment newInstance(String param1, String param2) {
        QandNFragment fragment = new QandNFragment();
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
        View view = inflater.inflate(R.layout.fragment_qand_n, container, false);

        recyclerView = view.findViewById(R.id.qnaRecy);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        models = new ArrayList<>();
        adapter = new QnaAdapter(getContext(), models);
        adapter.data(models);

        emptyView = view.findViewById(R.id.emptyLayout);

        progressBar = view.findViewById(R.id.progressBar1);
        relativeLayout = view.findViewById(R.id.progressLayout1);
        relativeLayout.setVisibility(View.VISIBLE);

        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (models.isEmpty()) {
                    swipeRefreshLayout.setRefreshing(true);
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                relativeLayout.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.setAdapter(adapter);
//        if (models.isEmpty()) {
//            // Show the empty view
//            emptyView.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            // Show the RecyclerView
//            emptyView.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
        db = FirebaseFirestore.getInstance();
        db.collection("QnA").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            qnaModel obj = d.toObject(qnaModel.class);
                            models.add(obj);
                            relativeLayout.setVisibility(View.GONE);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        relativeLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Data Loading Error", Toast.LENGTH_SHORT).show();

                    }
                });


        return view;
    }
}