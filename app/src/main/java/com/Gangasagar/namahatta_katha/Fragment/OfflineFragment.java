package com.Gangasagar.namahatta_katha.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.Gangasagar.namahatta_katha.Activity.NineActivity;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.Gangasagar.namahatta_katha.Activity.EightActivity;
import com.Gangasagar.namahatta_katha.Activity.FirstActivity;
import com.Gangasagar.namahatta_katha.Activity.FiveActivity;
import com.Gangasagar.namahatta_katha.Activity.FourthActivity;
import com.Gangasagar.namahatta_katha.Activity.PhotoViewActivity;
import com.Gangasagar.namahatta_katha.Activity.SecondActivity;
import com.Gangasagar.namahatta_katha.Activity.SevenActivity;
import com.Gangasagar.namahatta_katha.Activity.SisActivity;
import com.Gangasagar.namahatta_katha.Activity.ThirdActivity;
import com.Gangasagar.namahatta_katha.LottiDialog;
import com.Gangasagar.namahatta_katha.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfflineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfflineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout maingridlayout;
    LottiDialog dialog;
    ImageSlider slider;

    public OfflineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfflineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OfflineFragment newInstance(String param1, String param2) {
        OfflineFragment fragment = new OfflineFragment();
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
        View view = inflater.inflate(R.layout.fragment_offline, container, false);


        slider = view.findViewById(R.id.image_slider);

        dialog = new LottiDialog(getContext());
        dialog.setCancelable(false);
        maingridlayout = view.findViewById(R.id.maingridlayout);
        setSingleEvent(maingridlayout);

        setSlider();
        return view;
    }

    private void setSlider() {
        ArrayList<SlideModel> models = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Images").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        models.add(new SlideModel(queryDocumentSnapshot.getString("url"), ScaleTypes.FIT));
                        slider.setImageList(models, ScaleTypes.FIT);

                        slider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
//                                models.get(i).getImagePath().toString();
                                Intent intent = new Intent(getContext(), PhotoViewActivity.class);
                                intent.putExtra("url", models.get(i).getImageUrl().toString());

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }

                            @Override
                            public void doubleClick(int i) {

                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setSingleEvent(@NonNull LinearLayout maingridlayout) {

        for (int i = 0; i < maingridlayout.getChildCount(); i++) {
            CardView cardView = (CardView) maingridlayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(v -> {
                // 1st click
                if (finalI == 0) {

                    Intent intent = new Intent(getActivity(), FirstActivity.class);
                    startActivity(intent);

                }
                //2nd click
                else if (finalI == 1) {

                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    startActivity(intent);
                } else if (finalI == 2) {
                    Intent intent = new Intent(getActivity(), ThirdActivity.class);
                    startActivity(intent);

                }
                //
                else if (finalI == 3) {
                    Intent intent = new Intent(getActivity(), FourthActivity.class);
                    startActivity(intent);

                }
                //
                else if (finalI == 4) {
                    Intent intent = new Intent(getActivity(), FiveActivity.class);
                    startActivity(intent);

                } else if (finalI == 5) {
                    Intent intent = new Intent(getActivity(), SisActivity.class);
                    startActivity(intent);
                } else if (finalI == 6) {
                    Intent intent = new Intent(getActivity(), SevenActivity.class);
                    startActivity(intent);
                } else if (finalI == 7) {
                    Intent intent = new Intent(getActivity(), EightActivity.class);
                    startActivity(intent);
                } else if (finalI == 8) {
                    Intent intent9 = new Intent(getActivity(), NineActivity.class);
                    startActivity(intent9);

                }
            });
        }
    }

}