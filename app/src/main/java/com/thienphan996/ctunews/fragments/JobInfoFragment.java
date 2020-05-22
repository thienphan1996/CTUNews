package com.thienphan996.ctunews.fragments;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.adapters.jobs.JobInfoAdapter;
import com.thienphan996.ctunews.models.ImageNewsModel;
import com.thienphan996.ctunews.models.NewsModel;
import com.thienphan996.ctunews.views.DetailActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class JobInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int SCIENCE = 0;
    private static final int TECHNOLOGY = 1;
    private static final int ECONOMY = 2;
    private static final int SEAFOOD = 3;
    private static final int SOCIETY = 4;
    private static final int AGRICULTURE = 5;

    TabLayout tabLayout;
    RecyclerView rcvJobInfo;
    ArrayList<NewsModel> data;
    JobInfoAdapter jobInfoAdapter;
    SwipeRefreshLayout swipeRefreshJobInfo;
    String currentURL = "";
    int currentPage = 0;

    @Override
    protected int getProgressBarId() {
        return R.id.pro_job_info;
    }

    @Override
    protected void onBindViewModels() {
        data = new ArrayList<>();
        jobInfoAdapter = new JobInfoAdapter(data, new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position) instanceof NewsModel){
                    ImageNewsModel model = new ImageNewsModel();
                    model.setActionMode(3);
                    model.setTitle(data.get(position).getTitle());
                    model.setTargetUrl(getString(R.string.JOB_CTU_URL) + data.get(position).getTargetUrl());
                    Gson gson = new Gson();
                    String json = gson.toJson(model);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(getString(R.string.NEWS_MODEL), json);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            }
        });
        rcvJobInfo.setAdapter(jobInfoAdapter);
        getWebsite(getString(R.string.SCIENCE_URL));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job_info;
    }

    @Override
    protected void setActionViews() {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case SCIENCE:
                        getWebsite(getString(R.string.SCIENCE_URL));
                        break;
                    case TECHNOLOGY:
                        getWebsite(getString(R.string.TECHNOLOGY_URL));
                        break;
                    case ECONOMY:
                        getWebsite(getString(R.string.ECONOMY_URL));
                        break;
                    case SOCIETY:
                        getWebsite(getString(R.string.SOCIETY_URL));
                        break;
                    case SEAFOOD:
                        getWebsite(getString(R.string.SEAFOOD_URL));
                        break;
                    case AGRICULTURE:
                        getWebsite(getString(R.string.AGRICULTURE_URL));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getWebsite(final String url) {
        data.clear();
        jobInfoAdapter.notifyDataSetChanged();
        if (isNetworkConnected()){
            showProgressBar();
            currentPage = 0;
            currentURL = url;
            new AsyncTask<Void, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements contents = doc.select("article.sinhvien-post > div.sinhvien-postmetadataheader > h2.sinhvien-postheader > a[href]");
                        for (int i = 0; i < contents.size(); i++){
                            NewsModel model = new NewsModel();
                            model.setTitle(contents.get(i).text());
                            model.setTargetUrl(contents.get(i).attr("href"));
                            data.add(model);
                        }
                    } catch (IOException e) {
                        Log.d("Error: " , e.toString());
                        return false;
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    if (aBoolean){
                        rcvJobInfo.getLayoutManager().scrollToPosition(0);
                        jobInfoAdapter.notifyDataSetChanged();
                        hideInternetError();
                    }
                    else {
                        showNotInternetDialog();
                    }
                    swipeRefreshJobInfo.setRefreshing(false);
                    hideProgressBar();
                }
            }.execute();
        }
        else {
            swipeRefreshJobInfo.setRefreshing(false);
            showNotInternetDialog();
        }
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tab_job_info);
        rcvJobInfo = view.findViewById(R.id.rcv_job_info);
        progressBar.setVisibility(View.GONE);
        swipeRefreshJobInfo = view.findViewById(R.id.swipe_job_info);
        swipeRefreshJobInfo.setOnRefreshListener(this);
        swipeRefreshJobInfo.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    public static JobInfoFragment newInstance(){
        JobInfoFragment jobInfoFragment = new JobInfoFragment();
        return jobInfoFragment;
    }

    @Override
    public void onRefresh() {
        getWebsite(currentURL);
    }
}
