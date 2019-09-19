package com.example.mymfnapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseActivity extends AppCompatActivity
{

    private long exitTime;
    private Button buRegister;
    private Button buLogin;
    private ImageView ivLogo;

    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.choose_activity);

        init();
        init_animation();

        buLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ChooseActivity.this, "登录！", Toast.LENGTH_SHORT).show();
                end_animation();
                Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(ChooseActivity.this, "注册！", Toast.LENGTH_SHORT).show();
                end_animation();
                Intent intent = new Intent(ChooseActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init()
    {
        buLogin = (Button) findViewById(R.id.login_button);
        buRegister = (Button) findViewById(R.id.register_button);
        ivLogo = (ImageView) findViewById(R.id.ImgLogo);
    }

    private void init_animation()
    {
        //初始化底部注册、登录的按钮动画
        //以控件自身所在的位置为原点，从下方距离原点200像素的位置移动到原点
//        ObjectAnimator tranLogin = ObjectAnimator.ofFloat(buLogin, "translationY", 200, 0);
//        ObjectAnimator tranRegister = ObjectAnimator.ofFloat(buRegister, "translationY", 200, 0);
        //将注册、登录的控件alpha属性从0变到1
        ObjectAnimator alphaLogin = ObjectAnimator.ofFloat(buLogin, "alpha", 0, 1);
        ObjectAnimator alphaRegister = ObjectAnimator.ofFloat(buRegister, "alpha", 0, 1);
        AnimatorSet bottomAnim = new AnimatorSet();
        bottomAnim.setDuration(1000);
        //同时执行控件平移和alpha渐变动画
        bottomAnim.play(alphaLogin).with(alphaRegister);

        //获取屏幕高度
//        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics metrics = new DisplayMetrics();
//        manager.getDefaultDisplay().getMetrics(metrics);
//        int screenHeight = metrics.heightPixels;
//
//        //通过测量，获取ivLogo的高度
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        ivLogo.measure(w, h);
//        int logoHeight = ivLogo.getMeasuredHeight();
//
//        //初始化ivLogo的移动和缩放动画
////        float transY = (screenHeight - logoHeight) * 0.28f;
//        float transY = 100;
//        //ivLogo向上移动 transY 的距离
//        ObjectAnimator tranLogo = ObjectAnimator.ofFloat(ivLogo, "translationY", 0, -transY);
////        //ivLogo在X轴和Y轴上都缩放0.75倍
//        ObjectAnimator scaleXLogo = ObjectAnimator.ofFloat(ivLogo, "scaleX", 1f, 0.75f);
//        ObjectAnimator scaleYLogo = ObjectAnimator.ofFloat(ivLogo, "scaleY", 1f, 0.75f);
        ObjectAnimator alphaLogo = ObjectAnimator.ofFloat(ivLogo, "alpha", 0, 1);
        AnimatorSet logoAnim = new AnimatorSet();
        logoAnim.setDuration(1000);
//        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo);
        logoAnim.play(alphaLogo);
        logoAnim.start();
        bottomAnim.start();
//        logoAnim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                buLogin.setVisibility(View.VISIBLE);
//                buRegister.setVisibility(View.VISIBLE);
//                //待ivLogo的动画结束后,开始播放底部注册、登录按钮的动画
//                bottomAnim.start();
//            }
//        });
    }

    private void end_animation()
    {
        ObjectAnimator hidebuLogin  = ObjectAnimator.ofFloat(buLogin, "alpha", 1, 0);
        ObjectAnimator hidebuRegister  = ObjectAnimator.ofFloat(buLogin, "alpha", 1, 0);
        ObjectAnimator hideivLogo  = ObjectAnimator.ofFloat(buLogin, "alpha", 1, 0);

        final AnimatorSet hideAnim = new AnimatorSet();
        hideAnim.setDuration(1000);
        hideAnim.play(hidebuLogin).with(hidebuRegister).with(hideivLogo);
        hideAnim.start();
    }


}
