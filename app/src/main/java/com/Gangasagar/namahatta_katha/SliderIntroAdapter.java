package com.Gangasagar.namahatta_katha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderIntroAdapter extends PagerAdapter {
    private Context context;
    LayoutInflater layoutInflater;

    int images [] ={
            R.drawable.hands,
            R.drawable.qna,
            R.drawable.languages


    };

    int title [] = {
            R.string.app_name,
            R.string.app_name,
            R.string.language
    };
 int details [] = {
         R.string.intro_desc,
         R.string.qna_desc,
         R.string.language_desc

    };

    public SliderIntroAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view  == (ConstraintLayout) object ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_intro_layout,container,false);


        ImageView imageView = view.findViewById(R.id.imageView7);
        TextView heading = view.findViewById(R.id.textView37);
        TextView desc = view.findViewById(R.id.textView38);

        imageView.setImageResource(images[position]);
        heading.setText(title[position]);
        desc.setText(details[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ConstraintLayout)object);
    }
}
