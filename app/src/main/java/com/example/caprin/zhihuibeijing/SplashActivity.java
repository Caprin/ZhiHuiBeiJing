package com.example.caprin.zhihuibeijing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.caprin.zhihuibeijing.utils.PrefUtils;

public class SplashActivity extends Activity {

    RelativeLayout rlRoot;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        rlRoot = (RelativeLayout) findViewById(R.id.splash);
        startAnimation();
    }

    private void startAnimation() {
        AnimationSet set = new AnimationSet(false);

        RotateAnimation rotate = new RotateAnimation(0, 360, AnimationSet.RELATIVE_TO_SELF, 0.5f,
                AnimationSet.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rlRoot.startAnimation(set);
    }

    private void jumpNextPage() {
        //判断之前有没有显示过新手引导
        boolean userGuide = PrefUtils.getBoolean(this, "is_user_guide_showed", false);

        if (!userGuide) {
            startActivity(new Intent(SplashActivity.this, GuidePagerActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }

}
