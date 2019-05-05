package com.thienphan996.ctunews.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import com.thienphan996.ctunews.R;
public class NotifyDialog {

    Activity activity;
    AlertDialog dialog;

    public NotifyDialog(Activity activity){
        this.activity = activity;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(activity.getString(R.string.ERRORS));
        builder1.setCancelable(true);
        dialog = builder1.create();
    }

    public void showErrorNotInternet(){
        if (dialog != null){
            dialog.setMessage(activity.getString(R.string.INTERNET_ERROR));
            dialog.setButton(Dialog.BUTTON_POSITIVE, activity.getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }
}
