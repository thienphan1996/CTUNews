package com.thienphan996.ctunews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        if (view != null){
            initViews(view);
            setActionViews();
            onBindViewModels();
        }
        return view;
    }

    protected abstract void onBindViewModels();

    protected abstract int getLayoutId();

    protected abstract void setActionViews();

    protected abstract void initViews(View view);
}
