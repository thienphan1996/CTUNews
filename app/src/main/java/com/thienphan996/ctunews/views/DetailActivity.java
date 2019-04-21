package com.thienphan996.ctunews.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.models.ImageNewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    LinearLayout viewTitle;
    Intent intent;
    ImageNewsModel dataModel;
    TextView tvTitle;
    Gson gson;
    WebView webView;
    ImageButton btnBack;
    LottieAnimationView bgNotInternet, aniNotInternet, progress;

    private static final int FROM_HOME = 1;
    private static final int FROM_NEWS = 2;
    private static final int FROM_JOB_INFO = 3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupWindowAnimations();
        initViews();
        bindDataModels();
    }

    private void bindDataModels() {
        if (dataModel != null){
            tvTitle.setText(dataModel.getTitle());
            if (isNetworkConnected()){
                showProgress();
                getDataFromURL(dataModel);
            }
            else {
                showNotInternet();
            }
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataFromURL(final ImageNewsModel model) {
        if (model.getActionMode() == FROM_HOME){
            getURL(model.getTargetUrl());
        }
        else {
            webView.loadUrl(model.getTargetUrl());
        }
    }

    private void getURL(final String targetUrl) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String response = targetUrl;
                try {
                    Document doc = Jsoup.connect(targetUrl).get();
                    Elements contents = doc.select("a");
                    for (int i = 0; i < contents.size(); i++){
                        Element item = contents.get(i);
                        if (item.text().contains("Xem chi tiết")){
                            String subUrl = item.attr("href");
                            if (subUrl.substring(subUrl.length()-3, subUrl.length()).equals("pdf")){
                                response = getString(R.string.CTU_URL) + item.attr("href");
                            }
                            else {
                                response = item.attr("href");
                            }
                            break;
                        }
                    }
                } catch (IOException e) {
                    return null;
                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && !s.isEmpty()){
                    if (s.substring(s.length()-3, s.length()).equals("pdf")){
                        String newUrl = getString(R.string.DRIVE_READ_PDF_URL) + s;
                        webView.loadUrl(newUrl);
                    }
                    else {
                        webView.loadUrl(s);
                    }
                }
            }
        }.execute();
    }

    private void initViews() {
        gson = new Gson();
        viewTitle = findViewById(R.id.lnl_detail_title);
        tvTitle = findViewById(R.id.tv_detail_title);

        bgNotInternet = findViewById(R.id.bg_detail_not_internet);
        aniNotInternet = findViewById(R.id.ani_detail_not_internet);
        progress = findViewById(R.id.pro_detail);
        progress.setVisibility(View.GONE);
        bgNotInternet.setVisibility(View.GONE);
        aniNotInternet.setVisibility(View.GONE);

        btnBack = findViewById(R.id.btn_detail_back);

        webView = findViewById(R.id.web_detail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideProgress();
                hideNotInternet();
            }
        });
        intent = getIntent();
        if (intent != null){
            String result = intent.getStringExtra(getString(R.string.NEWS_MODEL));
            if (result != null && !result.isEmpty()){
                dataModel = gson.fromJson(result, ImageNewsModel.class);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewTitle.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setColorTitle();
                    }
                }, 1000);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setColorTitle() {
        int cx = viewTitle.getRight();
        int cy = viewTitle.getTop();
        int finalRadius = Math.max(viewTitle.getWidth(), viewTitle.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewTitle, cx, cy, 0, finalRadius);
        viewTitle.setBackgroundColor(getResources().getColor(R.color.colorStudent));
        anim.setDuration(500);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(300);
        slide.setSlideEdge(Gravity.RIGHT);
        getWindow().setEnterTransition(slide);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void showNotInternet(){
        bgNotInternet.setVisibility(View.VISIBLE);
        aniNotInternet.setVisibility(View.VISIBLE);
    }

    public void hideNotInternet(){
        bgNotInternet.setVisibility(View.GONE);
        aniNotInternet.setVisibility(View.GONE);
    }

    public void showProgress(){
        bgNotInternet.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        bgNotInternet.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }
}
