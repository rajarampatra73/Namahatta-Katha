package com.Gangasagar.namahatta_katha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.Gangasagar.namahatta_katha.Model.qnaModel;
import com.Gangasagar.namahatta_katha.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class QnaAdapter extends RecyclerView.Adapter<QnaAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<qnaModel> arrayList;

    public QnaAdapter(Context context, ArrayList<qnaModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void data(ArrayList<qnaModel> list) {
        list = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qn_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        qnaModel model = arrayList.get(position);
        holder.qnaTitle.setText(model.getQnaTitle());
        holder.qna.setText(model.getQna());
        Glide.with(holder.qnaIcon.getContext()).load(model.getQnaIcon())
                .into(holder.qnaIcon);
        holder.setIcon(model.getQnaIcon());


        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private FoldingCell foldingCell;
        private TextView qna, qnaTitle;
        private ImageView qnaIcon;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            qna = itemView.findViewById(R.id.qna);
            qnaTitle = itemView.findViewById(R.id.cell_title);
            qnaIcon = itemView.findViewById(R.id.cell_icon);

        }
        public void setIcon(String url) {
            Glide.with(itemView.getContext()).load(url).apply(new RequestOptions().placeholder(R.drawable.loading)).into(qnaIcon);

        }
    }
}
