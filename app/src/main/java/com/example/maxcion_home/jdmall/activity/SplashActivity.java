package com.example.maxcion_home.jdmall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.maxcion_home.jdmall.R;

import butterknife.BindView;

public class SplashActivity extends AppCompatActivity {
   
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = (ImageView) findViewById(R.id.iv_splan);
        setAlphaAnimation();

    }



    private void setAlphaAnimation() {
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashImage.setAnimation(alpha);
    }
}
