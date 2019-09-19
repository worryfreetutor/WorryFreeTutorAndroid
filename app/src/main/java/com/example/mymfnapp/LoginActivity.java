package com.example.mymfnapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 2019/5/31.
 */

public class LoginActivity extends AppCompatActivity
{
    private ImageView logo;
    private TextView accTv,pwTv;
    private EditText accEt,pwEt;
    private Button loginBt;
    private String acc,pw;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        init();

        loginBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                acc = accEt.getText().toString();
                pw = pwEt.getText().toString();
                if(!acc.isEmpty() && !pw.isEmpty())
                {
					//在此处发起网络请求
					
                    Toast.makeText(LoginActivity.this, "账号为：" + accEt.getText() +
                            " 密码为：" + pwEt.getText(), Toast.LENGTH_SHORT).show();
							
					//跳转到主页面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "账户或密码不能为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void init()
    {
        logo = (ImageView) findViewById(R.id.Login_ImgLogo);
        accTv = (TextView) findViewById(R.id.Login_acc_tv);
        pwTv = (TextView) findViewById(R.id.Login_pw_tv);
        accEt = (EditText) findViewById(R.id.Login_acc_et);
        pwEt = (EditText) findViewById(R.id.Login_pw_et);
        loginBt = (Button) findViewById(R.id.Login_bt);

        intent = getIntent();
        if (intent.getStringExtra("account")!=null)
        {
            accEt.setText(intent.getStringExtra("account"));
        }
    }

    /**
     * 重写返回键，实现返回效果
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginActivity.this, ChooseActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 重写输入框，实现点击空白处隐藏效果
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev))
            {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 窗口的父类事件分发处理，如果不写所有组件的点击事件都没有了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 重写软键盘，实现点击确认键的隐藏效果
     */

    @Override
    public boolean  dispatchKeyEvent(KeyEvent event)
    {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
        {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive())
            {
                inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
