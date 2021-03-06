package com.example.a846205123.ncu_learningchinese.Activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import com.example.a846205123.ncu_learningchinese.R;

import java.util.ArrayList;
import java.util.List;


public class ActivityProfileView extends AppCompatActivity {
    private ViewPager mViewPager;
    public TabLayout mTabLayout;
    private Toolbar mToolbar;
    private List<PageView> pageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        initData();
        initView();
    }
    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new Profile_information(ActivityProfileView.this));
        pageList.add(new Profile_learn(ActivityProfileView.this));

    }

    private void initView() {
        mToolbar =  findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tabs);

        setSupportActionBar(mToolbar);

        mTabLayout.addTab(mTabLayout.newTab().setText("Profile"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Learning"));

        mViewPager.setAdapter(new SamplePagerAdapter());
        initListener();
    }

    private void initListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
