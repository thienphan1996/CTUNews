package com.thienphan996.ctunews.views;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.thienphan996.ctunews.R;
import com.thienphan996.ctunews.common.NotifyDialog;
import com.thienphan996.ctunews.fragments.HomeFragment;
import com.thienphan996.ctunews.fragments.JobInfoFragment;
import com.thienphan996.ctunews.fragments.NewsFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {

    MeowBottomNavigation navHome;
    ViewPager pagerHome;
    ActionBar actionBar;
    LottieAnimationView internetError;
    NotifyDialog dialog;
    HomePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        onCreateViews();
        onBindViewModels();
        onCreateEvents();
    }

    private void onCreateViews() {
        navHome = findViewById(R.id.nav_home);
        pagerHome = findViewById(R.id.pager_home);
        actionBar = getSupportActionBar();
        dialog = new NotifyDialog(this);
        internetError = findViewById(R.id.ani_internet_error);
        internetError.setVisibility(View.GONE);
    }

    private void onBindViewModels() {
        navHome.add(new MeowBottomNavigation.Model(1, R.drawable.ic_notifications_black_24dp));
        navHome.add(new MeowBottomNavigation.Model(2, R.drawable.ic_student));
        navHome.add(new MeowBottomNavigation.Model(3, R.drawable.ic_customer));
        navHome.show(2,true);

        setUpPager();
    }

    private void onCreateEvents() {
        navHome.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        pagerHome.setCurrentItem(0);
                        break;
                    case 2:
                        pagerHome.setCurrentItem(1);
                        break;
                    case 3:
                        pagerHome.setCurrentItem(2);
                        break;
                    default: break;
                }
                return null;
            }
        });
        pagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navHome.show(1, true);
                        actionBar.setTitle(getString(R.string.TITLE_HOME));
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        navHome.show(2, true);
                        actionBar.setTitle(getString(R.string.TITLE_SUPPORT_STUDENT));
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        navHome.show(3, true);
                        actionBar.setTitle(getString(R.string.TITLE_JOB_INFO));
                        adapter.notifyDataSetChanged();
                        break;
                    default: break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpPager() {
        adapter = new HomePagerAdapter(getSupportFragmentManager());
        pagerHome.setAdapter(adapter);
        pagerHome.setOffscreenPageLimit(1);
        pagerHome.setCurrentItem(1);
    }

    private static class HomePagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public HomePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return NewsFragment.newInstance();
                case 2:
                    return JobInfoFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    public void showInternetError(){
        if (!dialog.isShowing()){
            dialog.showErrorNotInternet();
        }
        internetError.setVisibility(View.VISIBLE);
    }

    public void hideInternetError(){
        internetError.setVisibility(View.GONE);
    }
}
