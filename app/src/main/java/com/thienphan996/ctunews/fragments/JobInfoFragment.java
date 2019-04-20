package com.thienphan996.ctunews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.tabs.TabLayout;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.adapters.jobs.JobInfoAdapter;
import com.thienphan996.ctunews.models.JobInfoModel;
import com.thienphan996.ctunews.views.HomeActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class JobInfoFragment extends BaseFragment {

    private static final int SCIENCE = 0;
    private static final int TECHNOLOGY = 1;
    private static final int ECONOMY = 2;
    private static final int AGRICULTURE = 3;
    private static final int SOCIETY = 4;
    private static final int SEAFOOD = 5;

    TabLayout tabLayout;
    RecyclerView rcvJobInfo;
    ArrayList<JobInfoModel> data;
    JobInfoAdapter jobInfoAdapter;
    boolean isLoading = false;
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                    case AGRICULTURE:
                        getWebsite(getString(R.string.AGRICULTURE_URL));
                        break;
                    case SOCIETY:
                        getWebsite(getString(R.string.SOCIETY_URL));
                        break;
                    case SEAFOOD:
                        getWebsite(getString(R.string.SEAFOOD_URL));
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
        rcvJobInfo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == data.size() - 1) {
                        //loadMore();
                        //isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        if (currentPage < 50){
            data.add(null);
            jobInfoAdapter.notifyItemInserted(data.size() - 1);
            getMoreData();
        }
    }

    private void getMoreData() {
        if (isNetworkConnected()){
            currentPage += 10;
            final String url = currentURL + "?start=" + currentPage;
            new AsyncTask<Void, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try{
                        data.remove(data.size() - 1);
                        Document doc = Jsoup.connect(url).get();
                        Elements contents = doc.select("article.sinhvien-post > div.sinhvien-postmetadataheader > h2.sinhvien-postheader > a[href]");
                        for (int i = 0; i < contents.size(); i++){
                            JobInfoModel model = new JobInfoModel();
                            model.setTitle(contents.get(i).text());
                            model.setUrl(contents.get(i).attr("href"));
                            data.add(model);
                        }
                    }
                    catch (Exception e){
                        return false;
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    if (aBoolean){
                        jobInfoAdapter.notifyDataSetChanged();
                    }
                    isLoading = false;
                }
            }.execute();
        }
    }

    private void getWebsite(final String url) {
        data.clear();
        if (isNetworkConnected()){
            showProgressBar();
            currentPage = 0;
            currentURL = url;
            jobInfoAdapter.notifyDataSetChanged();
            new AsyncTask<Void, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements contents = doc.select("article.sinhvien-post > div.sinhvien-postmetadataheader > h2.sinhvien-postheader > a[href]");
                        for (int i = 0; i < contents.size(); i++){
                            JobInfoModel model = new JobInfoModel();
                            model.setTitle(contents.get(i).text());
                            model.setUrl(contents.get(i).attr("href"));
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
                    hideProgressBar();
                }
            }.execute();
        }
        else {
            jobInfoAdapter.notifyDataSetChanged();
            showNotInternetDialog();
        }
    }

    @Override
    protected void initViews(View view) {
        tabLayout = view.findViewById(R.id.tab_job_info);
        rcvJobInfo = view.findViewById(R.id.rcv_job_info);
        progressBar.setVisibility(View.GONE);
    }

    public static JobInfoFragment newInstance(){
        JobInfoFragment jobInfoFragment = new JobInfoFragment();
        return jobInfoFragment;
    }
}
