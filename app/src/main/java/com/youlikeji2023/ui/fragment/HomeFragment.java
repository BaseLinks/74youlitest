package com.youlikeji2023.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.FragmentPagerAdapter;
import com.tencent.bugly.proguard.v;
import com.youlikeji2023.R;
import com.youlikeji2023.app.AppFragment;
import com.youlikeji2023.app.TitleBarFragment;
import com.youlikeji2023.other.CaptureAct;
import com.youlikeji2023.other.QRCodeScanner;
import com.youlikeji2023.ui.activity.HomeActivity;
import com.youlikeji2023.ui.adapter.TabAdapter;
import com.youlikeji2023.widget.XCollapsingToolbarLayout;

import java.util.Scanner;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 首页 Fragment
 */
public final class HomeFragment extends TitleBarFragment<HomeActivity>
        implements TabAdapter.OnTabListener, ViewPager.OnPageChangeListener,
        XCollapsingToolbarLayout.OnScrimsListener, View.OnClickListener {

    private XCollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    private TextView mAddressView;
    private TextView mHintView;
    private AppCompatImageView mSearchView;

    private RecyclerView mTabView;
    private ViewPager mViewPager;

    private TabAdapter mTabAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;

    //创建Scan对象 实现扫码功能
    Context context = getActivity();
    QRCodeScanner scanner = new QRCodeScanner(context);



    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        mCollapsingToolbarLayout = findViewById(R.id.ctl_home_bar);
        mToolbar = findViewById(R.id.tb_home_title);

        mAddressView = findViewById(R.id.tv_home_address);
        mHintView = findViewById(R.id.tv_home_hint);
        mSearchView = findViewById(R.id.iv_home_search);
        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);

        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(StatusFragment.newInstance(), "项目列表");
        mPagerAdapter.addFragment(BrowserFragment.newInstance("https://blog.csdn.net/ckfamily2023"), "关于我们");
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        mTabAdapter = new TabAdapter(getAttachActivity());
        mTabView.setAdapter(mTabAdapter);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);

        // 扫码按键监听
        mSearchView.setOnClickListener(this);

        scanner.setRequestDataListener(data -> Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show());
    }

//    Scanner scanner = this.scanner ;
    @Override
    public void onClick(View v) {
        scanner.startScan(getActivity());
 //       scan();
    }

    /**
     * 扫码方法
     */
//    private void scan() {
//        toast("scan");
//        IntentIntegrator integrator = new IntentIntegrator(getActivity());
//        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        // 设置竖屏方向
//        //integrator.setCaptureActivity(CaptureAct.class);
//        integrator.setPrompt("扫描条码");
//        integrator.setOrientationLocked(false);
//        integrator.setCameraId(0);  // 使用默认的相机
//        integrator.setBeepEnabled(false); // 扫到码后播放提示音
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();
//
//
//    }


    @Override
    protected void initData() {
        mTabAdapter.addItem("项目列表");
        mTabAdapter.addItem("关于我们");
        mTabAdapter.setOnTabListener(this);
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * {@link TabAdapter.OnTabListener}
     */

    @Override
    public boolean onTabSelected(RecyclerView recyclerView, int position) {
        mViewPager.setCurrentItem(position);
        return true;
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if (mTabAdapter == null) {
            return;
        }
        mTabAdapter.setSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * CollapsingToolbarLayout 渐变回调
     *
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        getStatusBarConfig().statusBarDarkFont(shown).init();
        mAddressView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black : R.color.white));
        mHintView.setBackgroundResource(shown ? R.drawable.home_search_bar_gray_bg : R.drawable.home_search_bar_transparent_bg);
        mHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black60 : R.color.white60));
        mSearchView.setSupportImageTintList(ColorStateList.valueOf(getColor(shown ? R.color.common_icon_color : R.color.white)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }


    public void requestData(String scannedData) {
        Log.d("scannedData", "requestData: " + scannedData);
    }

}