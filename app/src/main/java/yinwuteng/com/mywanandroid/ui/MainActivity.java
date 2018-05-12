package yinwuteng.com.mywanandroid.ui;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


import yinwuteng.com.mywanandroid.R;
import yinwuteng.com.mywanandroid.base.BaseActivity;
import yinwuteng.com.mywanandroid.base.BaseFragment;
import yinwuteng.com.mywanandroid.base.BasePresenter;
import yinwuteng.com.mywanandroid.base.BaseView;
import yinwuteng.com.mywanandroid.hotsearch.HotActivity;
import yinwuteng.com.mywanandroid.hotsearch.MySearchActivity;
import yinwuteng.com.mywanandroid.ui.home.HomeFragment;
import yinwuteng.com.mywanandroid.ui.knowledge.KnowledgeSystemFragment;
import yinwuteng.com.mywanandroid.ui.my.MyFragment;


/**
 * Created by yinwuteng on 2018/4/5.
 * 主界面
 */
@Route(path = "/ui/MainActivity")
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPage;
    private BottomNavigationView navigation;
    private Toolbar mToolbar;
    private List<BaseFragment> mFragments = new ArrayList<>();

    private long mExitTime; //退出时间


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {
        navigation = findViewById(R.id.navigation);
        //设置navigation监听
        navigation.setOnNavigationItemSelectedListener(this);
        viewPage = findViewById(R.id.viewPage);
        mToolbar = findViewById(R.id.toolbar);
        initFragment();
        switchFragment(0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                mToolbar.setTitle(R.string.app_name);
                viewPage.setCurrentItem(0);
                break;
            case R.id.navigation_knowledgesystem:
                mToolbar.setTitle(R.string.title_knowledgesystem);
                viewPage.setCurrentItem(1);
                break;
            case R.id.navigation_my:
                mToolbar.setTitle(R.string.title_my);
                viewPage.setCurrentItem(2);
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
           HotActivity.start();
        } else if (item.getItemId() == R.id.menuSearch) {
            MySearchActivity.start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter() {
            @Override
            public BaseView getView() {
                return super.getView();
            }
        };
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastUtils.showShort(R.string.exit_system);
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(KnowledgeSystemFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment下标
     */
    private void switchFragment(int position) {
        //设置fragment适配器，用于封装fragment
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
        viewPage.setAdapter(mPageAdapter);
        //加载剩余2页
        viewPage.setOffscreenPageLimit(2);
        //设置viewPage监听器
        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
