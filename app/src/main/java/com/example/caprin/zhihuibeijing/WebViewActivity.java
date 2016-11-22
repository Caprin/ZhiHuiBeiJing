package com.example.caprin.zhihuibeijing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

/**
 * Created by caprin on 16-11-22.
 */
public class WebViewActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private ImageButton btnBack;
    private ImageButton btnText;
    private ImageButton btnShare;
    private ProgressBar mProgress;
    private static final String TAG = "webviewactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
//                .LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnText = (ImageButton) findViewById(R.id.icon_text);
        btnShare = (ImageButton) findViewById(R.id.icon_share);
        mProgress = (ProgressBar) findViewById(R.id.webprogress);
        mWebView = (WebView) findViewById(R.id.mWebview);

        btnBack.setOnClickListener(this);
        btnText.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mProgress != null) {
                    mProgress.setVisibility(View.VISIBLE);
                }
                Log.d(TAG, "页面开始刷新");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgress != null) {
                    mProgress.setVisibility(View.GONE);
                }
                Log.d(TAG, "页面刷新完成");
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
                Log.d(TAG, "加载进度" + newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG, "获得标题：" + title);
                super.onReceivedTitle(view, title);
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.loadUrl("http://www.liaoxuefeng.com/");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.icon_text:
                showChooseDialog();
                break;
            case R.id.icon_share:

                break;
        }
    }

    private int mCurrentChooseItem;
    private int mCurrentItem = 2;

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String items[] = new String[]{"超大字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        builder.setTitle("字体设置");

        builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + which);
                mCurrentChooseItem = which;
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = mWebView.getSettings();
                switch (mCurrentChooseItem) {
                    case 0:
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                    case 1:
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                    case 2:
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                    case 3:
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                    case 4:
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                    default:
                        break;
                }
                mCurrentItem = mCurrentChooseItem;
            }
        });

        builder.setNegativeButton("取消", null);

        builder.show();
    }
}
