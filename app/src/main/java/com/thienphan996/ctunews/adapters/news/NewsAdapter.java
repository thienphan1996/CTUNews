package com.thienphan996.ctunews.adapters.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.models.ImageNewsModel;
import com.thienphan996.ctunews.models.NewsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    ArrayList<ImageNewsModel> data;
    AdapterView.OnItemClickListener onClick;

    public NewsAdapter(ArrayList<ImageNewsModel> data, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        ImageNewsModel item = data.get(position);
        if (item instanceof  ImageNewsModel){
            holder.tvTitle.setText(item.getTitle());
            holder.tvContent.setText(item.getContent());
            holder.imgNews.setImageBitmap(item.getImgBitmap());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(null, v, holder.getAdapterPosition(), 0);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        ImageView imgNews;
        View container;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvContent = itemView.findViewById(R.id.item_news_tv_content);
            tvTitle = itemView.findViewById(R.id.item_news_tv_title);
            imgNews = itemView.findViewById(R.id.item_news_img);
        }
    }
}
