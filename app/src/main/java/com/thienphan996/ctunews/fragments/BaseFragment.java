package com.thienphan996.ctunews.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.thienphan996.ctunews.views.HomeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    LottieAnimationView progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        progressBar = view.findViewById(getProgressBarId());
        if (view != null){
            initViews(view);
            setActionViews();
            onBindViewModels();
        }
        return view;
    }

    protected abstract int getProgressBarId();

    protected abstract void onBindViewModels();

    protected abstract int getLayoutId();

    protected abstract void setActionViews();

    protected abstract void initViews(View view);

    protected void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    protected void showNotInternetDialog(){
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).showInternetError();
        }
    }

    protected void hideInternetError(){
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).hideInternetError();
        }
    }
}
