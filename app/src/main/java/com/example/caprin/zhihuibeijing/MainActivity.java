package com.example.caprin.zhihuibeijing;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.caprin.zhihuibeijing.Fragment.ContentFragment;
import com.example.caprin.zhihuibeijing.Fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

/**
 * Created by WD on 2016/10/11.
 */
public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SlidingMenu slidingMenu = getSlidingMenu();
        setBehindContentView(R.layout.left_menu);
        slidingMenu.setBehindOffset(700);
        slidingMenu.setMode(SlidingMenu.TOUCHMODE_MARGIN);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), "fragment_left_menu");
        transaction.replace(R.id.main_content, new ContentFragment(), "fragment_content");

        transaction.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fm = getFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag("fragment_left_menu");
        return fragment;
    }

    public ContentFragment getContentFragment() {
        FragmentManager fm = getFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.findFragmentByTag("fragment_content");
        return fragment;
    }
}
