package com.Gangasagar.namahatta_katha.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Gangasagar.namahatta_katha.Adapter.ViewpagerAdapter;
import com.Gangasagar.namahatta_katha.R;
import com.google.android.material.tabs.TabLayout;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TabLayout tabLayout;
    ViewPager viewPager;

    private Fragment fragment;
    private Fragment fragment2;
    private Fragment fragment3;


    public HomeFragment() {
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
        View view =inflater.inflate(R.layout.fragment_home, container, false);


        tabLayout = view.findViewById(R.id.TabLayout);
        viewPager = view.findViewById(R.id.ViewPager);

        fragment = new OfflineFragment();
        fragment2 = new OnlineFragment();
        fragment3 = new QandNFragment();

        tabLayout.setupWithViewPager(viewPager);
        ViewpagerAdapter viewpagerAdapter =
                new ViewpagerAdapter(getActivity().getSupportFragmentManager(),
                        0);


        viewpagerAdapter.addFragment(fragment, "Primary");
        viewpagerAdapter.addFragment(fragment2, getString(R.string.extra));
        viewpagerAdapter.addFragment(fragment3 , getString(R.string.action_settings));


        viewPager.setAdapter(viewpagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.book);
        tabLayout.getTabAt(1).setIcon(R.drawable.ebook);
        tabLayout.getTabAt(2).setIcon(R.drawable.question);
        return view;
    }

    private void setTabLayout() {


    }
}