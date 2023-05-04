package com.Gangasagar.namahatta_katha.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.Gangasagar.namahatta_katha.Model.NotiModel;
import com.Gangasagar.namahatta_katha.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {
    ArrayList<NotiModel> notiModels;

    public NotiAdapter( ArrayList<NotiModel> notiModels) {
        this.notiModels = notiModels;
    }

    @NonNull
    @Override
    public NotiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiAdapter.ViewHolder holder, int position) {
        final NotiModel model = notiModels.get(position);
        holder.notiTitle.setText(notiModels.get(position).getTitle());
        holder.notiDesc.setText(notiModels.get(position).getDesc());
        Picasso.get().load(model.getImg()).into(holder.notiImg);
        Picasso.get().load(model.getImg())
                .into(holder.notiImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Hide progress bar
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.VISIBLE);

                        // Handle error
                    }
                });
    }

    @Override
    public int getItemCount() {
        return notiModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView notiTitle,notiDesc;
        private ImageView notiImg;
        LottieAnimationView progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notiImg = itemView.findViewById(R.id.notiImg);
            notiTitle = itemView.findViewById(R.id.notiTitle);
            notiDesc = itemView.findViewById(R.id.notiDesc);
            progressBar = itemView.findViewById(R.id.animation_views);
        }
    }
}
