package com.example.kaixuan.worryfreetutor.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseURL;
import com.example.kaixuan.worryfreetutor.main.MainActivity;
import com.example.kaixuan.worryfreetutor.net.loginProtocol;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(new OkHttpClient())
                            .baseUrl(BaseURL.getBaseUrl())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();

                    loginProtocol lp = retrofit.create(loginProtocol.class);
                    Call<String> call = lp.mLogin(accEt.getText().toString(),pwEt.getText().toString());
                    // 这里使用异步还是同步好？ 异步感觉不能通注册界面一样这样在run内部进行activity跳转操作了，试试吧

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                String str="欢迎使用";
                                if(jsonObject.getString("code").equals("0")){
                                    //跳转到主页面
                                    //jsonObject.getString("access_token");
                                    //jsonObject.getString("refresh_token")

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else{
                                    str = jsonObject.getString("message");
                                }
                                //同步需要Looper.prepare()，异步不需要？
                                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // 请求失败,do something
                        }
                    });




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
