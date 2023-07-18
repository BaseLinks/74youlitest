package com.youli.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.View;

import com.youli.R;
import com.youli.aop.SingleClick;
import com.youli.app.TitleBarFragment;
import com.youli.ui.activity.AboutActivity;
import com.youli.ui.activity.BrowserActivity;
import com.youli.ui.activity.GuideActivity;
import com.youli.ui.activity.HomeActivity;
import com.youli.ui.activity.ImagePreviewActivity;
import com.youli.ui.activity.ImageSelectActivity;
import com.youli.ui.activity.LoginActivity;
import com.youli.ui.activity.PasswordForgetActivity;
import com.youli.ui.activity.PasswordResetActivity;
import com.youli.ui.activity.PersonalDataActivity;
import com.youli.ui.activity.PhoneResetActivity;
import com.youli.ui.activity.RegisterActivity;
import com.youli.ui.activity.SettingActivity;
import com.youli.ui.activity.StatusActivity;
import com.youli.ui.activity.VideoPlayActivity;
import com.youli.ui.activity.VideoSelectActivity;
import com.youli.ui.dialog.InputDialog;
import com.youli.ui.dialog.MessageDialog;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 我的 Fragment
 */
public final class MineFragment extends TitleBarFragment<HomeActivity> {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView() {
            setOnClickListener(R.id.btn_mine_about, R.id.btn_mine_pay);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_mine_about) {

            startActivity(AboutActivity.class);

        }
        else if (viewId == R.id.btn_mine_pay) {

            new MessageDialog.Builder(getAttachActivity())
                    .setTitle("捐赠")
                    .setMessage("如果你觉得这个开源项目很棒，希望它能更好地坚持开发下去，可否愿意花一点点钱（推荐 10.24 元）作为对于开发者的激励")
                    .setConfirm("支付宝")
                    .setCancel(null)
                    //.setAutoDismiss(false)
                    .setListener(dialog -> {
                        BrowserActivity.start(getAttachActivity(), "https://blog.csdn.net/ckfamily2023");
                        toast("AndroidProject 因为有你的支持而能够不断更新、完善，非常感谢支持！");
                        postDelayed(() -> {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX04202G4K6AVCF5GIY66%3F_s%3Dweb-other"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                toast("打开支付宝失败，你可能还没有安装支付宝客户端");
                            }
                        }, 2000);
                    })
                    .show();
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}