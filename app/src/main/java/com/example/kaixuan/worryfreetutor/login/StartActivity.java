package com.example.kaixuan.worryfreetutor.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.example.kaixuan.worryfreetutor.R;

/**
 * Created by huihui on 2019/5/30.
 */

public class StartActivity extends AppCompatActivity
{
    private Handler handler = new Handler();
    private Boolean Islogin = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            Thread.sleep(1000); //线程休眠1s，使出现白屏时的效果更加明显
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        //展示2s后前往主页
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Islogin)
                {
                    //如果之前已经登录过则直接跳转主页面
                    //intent = new Intent(StartActivity.this, LoginMainActivity.class);
                }
                else
                {
                    intent = new Intent(StartActivity.this, ChooseActivity.class);
                }
                startActivity(intent);
                finish();
                //取消界面跳转时的动画，使启动页的logo图片与注册、登录主页的logo图片完美衔接
                overridePendingTransition(0, 0);
            }
        }, 2000);
    }

    /**
     * 屏蔽物理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            //If token is null, all callbacks and messages will be removed.
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
