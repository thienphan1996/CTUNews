package com.thienphan996.ctunews.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.thienphan996.ctunews.R;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialog {
    Context context;
    AlertDialog alertDialog;
    public ProgressDialog(Context context){
        this.context = context;
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }
}
