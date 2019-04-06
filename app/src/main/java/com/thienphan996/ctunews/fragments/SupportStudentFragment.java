package com.thienphan996.ctunews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thienphan996.ctunews.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SupportStudentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_support_student, container, false);
        return rootView;
    }

    public static SupportStudentFragment newInstance(){
        SupportStudentFragment supportStudentFragment = new SupportStudentFragment();
        return supportStudentFragment;
    }
}
