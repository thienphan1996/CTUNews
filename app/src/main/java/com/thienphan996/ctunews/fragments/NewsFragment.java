package com.thienphan996.ctunews.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.adapters.news.NewsAdapter;
import com.thienphan996.ctunews.models.ImageNewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends BaseFragment {

    ArrayList<ImageNewsModel> data;
    NewsAdapter adapter;
    RecyclerView rcvNews;

    public static NewsFragment newInstance(){
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }

    @Override
    protected int getProgressBarId() {
        return R.id.pro_news;
    }

    @Override
    protected void onBindViewModels() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void setActionViews() {
        getWebsite(5);
    }

    @Override
    protected void initViews(View view) {
        data = new ArrayList<>();
        adapter = new NewsAdapter(data, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        rcvNews = view.findViewById(R.id.rcv_news);
        rcvNews.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    private void getWebsite(int param) {
        data.clear();
        if (isNetworkConnected()){
            showProgressBar();
            final String newsUrl = getString(R.string.NEWS_URL) + param;
            final String homeUrl = getString(R.string.CTU_URL);
            new AsyncTask<Void, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    try {
                        Document doc = Jsoup.connect(newsUrl).get();
                        Elements links = doc.select("h2.art-postheader > a[href^=/tin-tuc/]");
                        Elements imgUrls = doc.select("div.art-postcontent > div.img-intro-left > img[src]");
                        Elements contents = doc.select("article.art-post > div.art-postcontent > p");
                        for (int i = 0; i < links.size(); i++){
                            String targetUrl = homeUrl + links.get(i).attr("href");
                            String title = links.get(i).text();
                            String imgUrl = homeUrl + imgUrls.get(i).attr("src");
                            String content = contents.get(i).text();
                            InputStream inputStream = new URL(imgUrl).openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            ImageNewsModel model = new ImageNewsModel(title, imgUrl, targetUrl, content, bitmap);
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
                        adapter.notifyDataSetChanged();
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
            adapter.notifyDataSetChanged();
            showNotInternetDialog();
        }
    }
}
