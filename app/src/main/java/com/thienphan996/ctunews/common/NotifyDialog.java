package com.thienphan996.ctunews.common;

import android.content.Context;
import android.graphics.Color;

import com.kinda.alert.KAlertDialog;
import com.thienphan996.ctunews.R;

public class NotifyDialog {

    KAlertDialog alertDialog;
    Context context;

    public NotifyDialog(Context context){
        this.context = context;
    }

    public void showProgressDialog(){
        alertDialog = new KAlertDialog(context, KAlertDialog.PROGRESS_TYPE);
        alertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        alertDialog.setTitleText(context.getString(R.string.LOADING));
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public boolean isShowing(){
        return alertDialog.isShowing();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }

    public void showMessage(String message){
        new KAlertDialog(context)
                .setTitleText("Here's a message!")
                .setContentText("It's pretty, isn't it?")
                .show();
    }

    public void showInfoDialog(String title, String message){
        new KAlertDialog(context)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    public void showErrorDialog(String errorMessage){
        new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                .setTitleText(context.getString(R.string.ERRORS))
                .setContentText(errorMessage)
                .showCancelButton(true)
                .show();
    }

    public void showWarningDialog(){
        new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(KAlertDialog.SUCCESS_TYPE);
                    }
                })
                .showCancelButton(true)
                .show();
    }

    public void showSuccessDialog(String message){
        new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
                .setTitleText(context.getString(R.string.SUCCESS))
                .setContentText(message)
                .show();
    }

    public void showConfirmDialog(String title, String message){
        new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setConfirmText(context.getString(R.string.YES))
                .setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
