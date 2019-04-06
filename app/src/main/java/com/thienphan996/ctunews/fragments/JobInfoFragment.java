package com.thienphan996.ctunews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thienphan996.ctunews.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JobInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_job_info, container, false);
        return rootView;
    }

    public static JobInfoFragment newInstance(){
        JobInfoFragment jobInfoFragment = new JobInfoFragment();
        return jobInfoFragment;
    }
}
