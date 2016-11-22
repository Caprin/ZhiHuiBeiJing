package com.example.caprin.zhihuibeijing;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * Created by caprin on 16-11-22.
 */
public class WebViewActivity extends Activity implements DialogInterface.OnClickListener {

    WebView mWebView;
    ImageButton btnBack;
    ImageButton btnText;
    ImageButton btnShare;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnText = (ImageButton) findViewById(R.id.icon_text);
        btnShare = (ImageButton) findViewById(R.id.icon_share);
        mProgress = (ProgressBar) findViewById(R.id.webprogress);

        //去掉标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mWebView = (WebView) findViewById(R.id.mWebview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.loadUrl("http://www.liaoxuefeng.com/");
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
