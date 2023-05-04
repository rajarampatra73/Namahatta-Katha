package com.Gangasagar.namahatta_katha.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Gangasagar.namahatta_katha.WebViewActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.Gangasagar.namahatta_katha.Model.OnlineModel;
import com.Gangasagar.namahatta_katha.OnlineViewActivity;
import com.Gangasagar.namahatta_katha.R;

import java.util.ArrayList;


public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {

    private ArrayList<OnlineModel> list;

    public OnlineAdapter(ArrayList<OnlineModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public OnlineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_recy_layout,parent,false);

        return new OnlineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineAdapter.ViewHolder holder, int position) {
        OnlineModel model = list.get(position);
        holder.tv.setText(model.getTitle());
        Glide.with(holder.icon.getContext()).load(model.getIcon())
                .into(holder.icon);
        holder.setIcon(model.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra("url",model.getUrl());
                intent.putExtra("CategoryName",model.getTitle());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), OnlineViewActivity.class);
                intent.putExtra("url",model.getUrl());
                intent.putExtra("h1",model.getH1());
                intent.putExtra("con",model.getCon());
                intent.putExtra("CategoryName",model.getTitle());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.online_icon);
            tv = itemView.findViewById(R.id.online_tv);

        }
        public void setIcon(String url) {
            Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.loading)).into(icon);
        }

    }
}
