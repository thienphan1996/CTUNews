package com.thienphan996.ctunews.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.thienphan996.ctunews.R;

public class SplashActivity extends AppCompatActivity {
    LinearLayout lnlSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        setActionViews();
    }

    private void setActionViews() {
        CountDownTimer countDownTimer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        };
        countDownTimer.start();
    }

    private void initViews() {
        lnlSplash = findViewById(R.id.lnl_splash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);
        lnlSplash.setAnimation(animation);
    }
}
