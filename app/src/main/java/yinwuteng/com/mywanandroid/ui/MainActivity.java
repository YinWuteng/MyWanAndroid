package yinwuteng.com.mywanandroid.ui;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.ui.home.HomeFragment;
import yinwuteng.com.mywanandroid.ui.home.KnowledgeFragment;
import yinwuteng.com.mywanandroid.ui.home.MyFragment;

/**
 * Created by yinwuteng on 2018/3/19.
 * 主页
 */

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private List<BaseFragment> mFragments = new ArrayList<>();

    private long mExitTime; //退出时间
    @BindView(R.id.viewPage)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //设置navigation监听
        navigation.setOnNavigationItemSelectedListener(this);
        initFragment();
        switchFragment(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mToolbar.setTitle(R.string.app_name);
                viewPager.setCurrentItem(0);
                break;
            case R.id.navigation_knowledgesystem:
                mToolbar.setTitle(R.string.title_knowledgesystem);
                viewPager.setCurrentItem(1);
                break;
            case R.id.navigation_my:
                mToolbar.setTitle(R.string.title_my);
                viewPager.setCurrentItem(2);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuHot) {

        } else if (item.getItemId() == R.id.menuSearch) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showShort(R.string.exit_system);
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mFragments.add(new HomeFragment());
        mFragments.add(new KnowledgeFragment());
        mFragments.add(new MyFragment());
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        //设置适配器，用于封装fragment
        FragmentPagerAdapter mPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        //设置适配器
        viewPager.setAdapter(mPageAdapter);
        //设置加载剩余2页
        viewPager.setOffscreenPageLimit(2);
        //viewPage监听器
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
