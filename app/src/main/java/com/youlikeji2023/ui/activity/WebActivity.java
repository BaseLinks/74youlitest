package com.youlikeji2023.ui.activity;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.youlikeji2023.R;
import com.youlikeji2023.app.AppActivity;

public class WebActivity extends AppActivity {
    private String url1;


    private WebView mWebview;
    WebSettings mWebSettings;

    @Override
    protected int getLayoutId() {
        return R.layout.web_activity;
    }

    @Override
    protected void initView() {
        final Intent intent = getIntent();
        url1 = intent.getStringExtra("url");
        toast(url1);
        mWebview = findViewById(R.id.webview);

        mWebSettings = mWebview.getSettings();
        mWebview.loadUrl(url1);

        mWebSettings.setJavaScriptEnabled(true); //这里如果本地html引入了javascript那么需要把这个设置为true
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setDefaultTextEncodingName("utf-8"); //设置文本编码
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadsImagesAutomatically(true);

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    protected void initData() {


    }
}
