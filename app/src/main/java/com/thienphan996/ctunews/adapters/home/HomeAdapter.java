package com.thienphan996.ctunews.adapters.home;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.models.NewsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<NewsModel> data;
    AdapterView.OnItemClickListener onItemClickListener;
    Resources resources;

    public HomeAdapter(ArrayList<NewsModel> data, Resources resources, AdapterView.OnItemClickListener onItemClickListener) {
        this.data = data;
        this.resources = resources;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {
        if (this.data.get(position) instanceof  NewsModel){
            holder.tvTitle.setText(this.data.get(position).getTitle());
            setIconByTitle(this.data.get(position).getTitle().toLowerCase(), holder);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(null, v, holder.getAdapterPosition(), 0);
            }
        });
    }

    private void setIconByTitle(String title, HomeViewHolder holder) {
        if (!title.isEmpty()){
            if (title.contains("thông tin") || title.contains("thong tin")){
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_information));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorInfo));
            }
            else if (title.contains("thông báo") || title.contains("thong bao")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_notification));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorNotification));
            }
            else if (title.contains("lịch") || title.contains("lich")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_calendar));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorCalendar));
            }
            else if (title.contains("kế hoạch") || title.contains("ke hoach")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_organize));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorOrganize));
            }
            else if (title.contains("xóa") || title.equals("xoa") || title.contains("v/v xóa")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_delete));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorDelete));
            }
            else if (title.contains("thi") || title.contains("tổ chức") || title.contains("to chuc") || title.contains("v/v mở") || title.contains("v/v mo") || title.contains("hội thi") || title.contains("hoi thi")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_student_white));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorStudent));
            }
            else if (title.contains("đưa") || title.equals("dua")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_learning));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorLearning));
            }
            else if (title.contains("tuyển") || title.equals("tuyen")) {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_recruitment));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorRecruitment));
            }
            else {
                holder.iconTitle.setImageDrawable(resources.getDrawable(R.drawable.ic_notification));
                holder.bgIcon.setCardBackgroundColor(resources.getColor(R.color.colorNotification));
            }
        }
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView tvTitle;
        ImageView iconTitle;
        MaterialCardView bgIcon;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvTitle = itemView.findViewById(R.id.item_home_tv_title);
            iconTitle = itemView.findViewById(R.id.item_home_icon);
            bgIcon = itemView.findViewById(R.id.item_home_bg_icon);
        }
    }
}
