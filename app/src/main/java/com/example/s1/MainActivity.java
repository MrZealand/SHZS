package com.example.s1;

import android.content.ContentValues;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.s1.adapter.MyPagerStateAdapter;
import com.example.s1.entity.DiaryText;
import com.example.s1.fragments.ControlFragment;
import com.example.s1.fragments.DiaryFragment;
import com.example.s1.fragments.NewsFragment;
import com.example.s1.fragments.ScheduleFragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private TabLayout.Tab diary;
    private TabLayout.Tab schedule;
    private TabLayout.Tab control;
    private TabLayout.Tab news;
    private DrawerLayout mDrawerLayout;
    private DiaryFragment diaryFragment;
    private ScheduleFragment scheduleFragment;
    private NewsFragment newsFragment;
    private ControlFragment controlFragment;
    private MyPagerStateAdapter myPagerStateAdapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu_64px);
        }

        fragmentList=new ArrayList<>();
        fragmentList.add(scheduleFragment=new ScheduleFragment());
        fragmentList.add(newsFragment=new NewsFragment());
        fragmentList.add(diaryFragment=new DiaryFragment());
        fragmentList.add(controlFragment=new ControlFragment());
        initViews();
        initEvents();

    }

    private void initEvents(){
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab==mTablayout.getTabAt(0)){
                    schedule.setIcon(R.mipmap.ic_menu_anniversary);
                }else if(tab==mTablayout.getTabAt(1)){
                    news.setIcon(R.mipmap.ic_menu_welfare_black);
                }else if(tab==mTablayout.getTabAt(2)){
                    diary.setIcon(R.mipmap.ic_menu_diary);
                }else if(tab==mTablayout.getTabAt(3)){
                    control.setIcon(R.mipmap.ic_menu_welfare_white);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if(tab==mTablayout.getTabAt(0)){
                    schedule.setIcon(R.mipmap.ic_menu_anniversary_dark);
                    mViewPager.setCurrentItem(0);
                }else if(tab==mTablayout.getTabAt(1)){
                    news.setIcon(R.mipmap.ic_menu_welfare_dark);
                    mViewPager.setCurrentItem(1);
                }else if(tab==mTablayout.getTabAt(2)){
                    diary.setIcon(R.mipmap.ic_menu_diary_dark);
                    mViewPager.setCurrentItem(2);
                }else if(tab==mTablayout.getTabAt(3)){
                    control.setIcon(R.mipmap.ic_menu_welfare_dark);
                    mViewPager.setCurrentItem(3);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews(){
        mTablayout=(TabLayout)findViewById(R.id.tabLayout);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);
        myPagerStateAdapter=new MyPagerStateAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(myPagerStateAdapter);
//        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            private String[]mTitles=new String[]{"任务","新闻","日记","休息"};
//            @Override
//            public Fragment getItem(int position) {
//                if(position==0)
//                    return new ScheduleFragment();
//                else if(position==1)
//                    return new NewsFragment();
//                else if(position==2)
//                {
//                    if(diaryFragment==null)
//                        diaryFragment=new DiaryFragment();
//                    diaryFragment.show();
//                    return diaryFragment;
//                }
//                else if(position==3)
//                    return new ControlFragment();
//                return new ScheduleFragment();
//            }
//
//            @Override
//            public int getCount() {
//                return mTitles.length;
//            }
//
//
//
//            @Override
//            public CharSequence getPageTitle(int position){
//                return mTitles[position];
//            }
//
//            @Override
//            public void setPrimaryItem(ViewGroup container, int position, Object object) {
//                mCurrentFragment = (XXXFragment) object;
//                super.setPrimaryItem(container, position, object);
//            }
//
//
//            public XXXFragment getCurrentFragment() {
//                return mCurrentFragment;
//            }
//        });

        mTablayout.setupWithViewPager(mViewPager);

        schedule=mTablayout.getTabAt(0);
        news=mTablayout.getTabAt(1);
        diary=mTablayout.getTabAt(2);
        control=mTablayout.getTabAt(3);

        schedule.setIcon(R.mipmap.ic_launcher);
        news.setIcon(R.mipmap.ic_launcher);
        diary.setIcon(R.mipmap.ic_launcher);
        control.setIcon(R.mipmap.ic_launcher);

    }

    //顶部菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){//有待处理
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,WriteDiaryActivity.class);
                intent.putExtra("current_title","新建");
                startActivity(intent);
                break;

            case android.R.id.home:
                Log.d("open","打开");
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

}
