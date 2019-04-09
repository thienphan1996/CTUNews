package com.thienphan996.ctunews.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.adapters.home.HomeAdapter;
import com.thienphan996.ctunews.common.NotifyDialog;
import com.thienphan996.ctunews.common.ProgressDialog;
import com.thienphan996.ctunews.models.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends BaseFragment {

    RecyclerView rcvHome;
    ArrayList<NewsModel> data;
    HomeAdapter adapter;
    MaterialButton btnPrevious, btnNext;
    TextView tvCurrentPage;
    ScrollView scvHome;
    RelativeLayout relativeLayout;
    NotifyDialog dialog;
    int totalRecord;

    @Override
    protected void onBindViewModels() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setActionViews() {
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalRecord >= 15){
                    totalRecord -= 15;
                    getWebsite(totalRecord);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalRecord <= 150){
                    totalRecord += 15;
                    getWebsite(totalRecord);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initViews(View view) {
        rcvHome = view.findViewById(R.id.rcv_home);
        btnPrevious = view.findViewById(R.id.btn_home_previous);
        btnNext = view.findViewById(R.id.btn_home_next);
        tvCurrentPage = view.findViewById(R.id.tv_home_current_page);
        scvHome = view.findViewById(R.id.scv_home);
        relativeLayout = view.findViewById(R.id.rtl_home);
        dialog = new NotifyDialog(getContext());
        data = new ArrayList<>();
        adapter = new HomeAdapter(data, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Current position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvHome.setLayoutManager(layoutManager);
        rcvHome.setAdapter(adapter);
        getWebsite(totalRecord);
    }

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    private void getWebsite(int param) {
        relativeLayout.setVisibility(View.GONE);
        dialog.showProgressDialog();
        final Handler h = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                    dialog.showErrorDialog(getString(R.string.INTERNET_ERROR));
                }
            }
        };
        h.sendMessageDelayed(new Message(), 30000);
        final String newsUrl = getString(R.string.NOTIFY_URL) + param;
        new AsyncTask<Void, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    data.clear();
                    Document doc = Jsoup.connect(newsUrl).get();
                    Elements links = doc.select("td.list-title > a[href^=/thong-bao/]");
                    for (Element link : links) {
                        String targetUrl = link.attr("href");
                        String title = link.text();
                        NewsModel content = new NewsModel(title, targetUrl);
                        if (!data.contains(content) && data.size() < 15){
                            data.add(new NewsModel(title, targetUrl));
                        }
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
                    adapter.notifyDataSetChanged();
                    rcvHome.getLayoutManager().scrollToPosition(0);
                    scvHome.fullScroll(ScrollView.FOCUS_UP);
                    tvCurrentPage.setText("Trang " + (totalRecord / 15 + 1));
                    relativeLayout.setVisibility(View.VISIBLE);
                    if (totalRecord == 0) btnPrevious.setEnabled(false);
                    else if (totalRecord == 135) btnNext.setEnabled(false);
                    else {
                        btnNext.setEnabled(true);
                        btnPrevious.setEnabled(true);
                    }
                    dialog.dismiss();
                }
                else {
                    relativeLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                    dialog.showErrorDialog(getString(R.string.INTERNET_ERROR));
                }
            }
        }.execute();
    }
}
