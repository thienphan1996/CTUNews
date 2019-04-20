package com.thienphan996.ctunews.common;

import android.app.Activity;
import com.thienphan996.ctunews.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NotifyDialog {

    Activity activity;
    SweetAlertDialog dialog;

    public NotifyDialog(Activity activity){
        this.activity = activity;
        dialog = new SweetAlertDialog(activity);
    }

    public void showErrorNotInternet(){
        dialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(activity.getString(R.string.ERRORS))
                .setContentText(activity.getString(R.string.INTERNET_ERROR));
        dialog.show();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }
}
