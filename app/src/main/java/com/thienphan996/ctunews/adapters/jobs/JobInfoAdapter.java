package com.thienphan996.ctunews.adapters.jobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.models.NewsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobInfoAdapter extends RecyclerView.Adapter<JobInfoAdapter.JobInfoViewHolder> {

    ArrayList<NewsModel> data;
    AdapterView.OnItemClickListener onClick;

    public JobInfoAdapter(ArrayList<NewsModel> data, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public JobInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_job_info, parent, false);
        return new JobInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobInfoViewHolder holder, int position) {
        if (data.get(position) instanceof NewsModel){
            holder.tvTitle.setText(data.get(position).getTitle());
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
        return data != null ? data.size() : 0;
    }

    public class JobInfoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        View container;
        public JobInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvTitle = itemView.findViewById(R.id.item_job_info_tv_title);
        }
    }
}
