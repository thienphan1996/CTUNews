package com.thienphan996.ctunews.adapters.jobs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.models.JobInfoModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    ArrayList<JobInfoModel> data;
    AdapterView.OnItemClickListener onClick;

    public JobInfoAdapter(ArrayList<JobInfoModel> data, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.item_job_info, parent, false);
            return new JobInfoViewHolder(view);
        }
        else {
            View view = inflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof JobInfoViewHolder){
            populateItemRows((JobInfoViewHolder) holder, position);
        }
    }

    private void populateItemRows(final JobInfoViewHolder holder, int position) {
        if (data.get(position) instanceof JobInfoModel){
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
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
