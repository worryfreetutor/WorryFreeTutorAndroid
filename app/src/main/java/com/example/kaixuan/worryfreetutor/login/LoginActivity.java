package com.example.kaixuan.worryfreetutor.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.GetRetrofit;
import com.example.kaixuan.worryfreetutor.base.SimplifyLogin;
import com.example.kaixuan.worryfreetutor.main.MainActivity;
import com.example.kaixuan.worryfreetutor.net.loginProtocol;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lenovo on 2019/5/31.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    private EditText LoginAccEt,LoginPwEt;
    private ImageButton ibtn_clean1,ibtn_eye,ibtn_clean2;
    private View acc_view,pw_view;
    private Button loginBt;
    private TextView tv_fogot,tv_sm_login,tv_rg;
    private Boolean isOpenEye = false;
    private Intent intent;
    private Intent temp_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylg_login);
        initView();
        init();
    }



    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 防止activity变换时，造成状态错误
         * */
        if (!LoginAccEt.getText().toString().isEmpty() && !LoginPwEt.getText().toString().isEmpty()) {
            SimplifyLogin.btnEnabled(this, loginBt, true);
        } else {
            SimplifyLogin.btnEnabled(this, loginBt, false);
        }
    }



    /**
     * 重写输入框，实现点击空白处隐藏效果
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
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

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText) || (v instanceof ImageButton)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            if ( event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                // 点击的是输入框,点击的是ImageButton 所在行的区域
                // 则保留点击事件
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


    /**
     * 重写软键盘，实现点击确认键的隐藏效果
     */

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        SimplifyLogin.etCleanVisibility(LoginAccEt, ibtn_clean1);
        SimplifyLogin.etCleanVisibility(LoginPwEt, ibtn_clean2);

        if (!LoginAccEt.getText().toString().isEmpty() && !LoginPwEt.getText().toString().isEmpty()) {
            SimplifyLogin.btnEnabled(this, loginBt, true);
        } else {
            SimplifyLogin.btnEnabled(this, loginBt, false);
        }
    }

    @Override
    public void onFocusChange(View v,boolean hasFocus){
        switch (v.getId()) {
            case R.id.Login_acc_et:
                SimplifyLogin.changeLineView(this, acc_view, hasFocus);
                break;
            case R.id.Login_pw_et:
                SimplifyLogin.changeLineView(this, pw_view, hasFocus);
                break;
            default:
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login_clean1:
                LoginAccEt.setText("");

                break;
            case R.id.Login_eye:
                isOpenEye = SimplifyLogin.onClickeEye(isOpenEye,ibtn_eye,LoginPwEt);

                break;
            case R.id.Login_clean2:
                LoginPwEt.setText("");

                break;
            case R.id.Login_forgot_pw:
                temp_intent = new Intent(LoginActivity.this, FogotPWActivity.class);
                startActivity(temp_intent);
                break;
            case R.id.Login_bt:

                //在此处发起网络请求
                Retrofit retrofit = GetRetrofit.getR();
                loginProtocol lp = retrofit.create(loginProtocol.class);
                Call<String> call = lp.mLogin(LoginAccEt.getText().toString(), LoginPwEt.getText().toString());
                // 这里使用异步还是同步好？ 异步感觉不能通注册界面一样这样在run内部进行activity跳转操作了，试试吧

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String str = "欢迎使用";
                            if (jsonObject.getString("code").equals("0")) {
                                //跳转到主页面
                                //jsonObject.getString("access_token");
                                //jsonObject.getString("refresh_token")

                                /**
                                 * 存储用户登录后的token信息。
                                 */
                                SharedPreferences sharedPreferences = getSharedPreferences("login_token", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("access_token", jsonObject.getString("access_token"));
                                editor.putString("refresh_token", jsonObject.getString("refresh_token"));
                                editor.putString("account", LoginAccEt.getText().toString());
                                editor.putString("password", LoginPwEt.getText().toString());
                                editor.putBoolean("logined", true);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                str = jsonObject.getString("message");
                            }
                            //同步需要Looper.prepare()，异步不需要。
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

                break;
            case R.id.Login_sm_tv:
                temp_intent = new Intent(LoginActivity.this,SMLoginActivity.class   );
                startActivity(temp_intent);
                break;
            case R.id.Login_rg_tv:
                temp_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(temp_intent);
                break;
        }
    }


    private void init() {
        intent = getIntent();
        if (intent.getStringExtra("account") != null) {
            LoginAccEt.setText(intent.getStringExtra("account"));
        }
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    private void initView() {
        LoginAccEt = $(R.id.Login_acc_et);
        LoginPwEt = $(R.id.Login_pw_et);
        LoginAccEt.addTextChangedListener(this);
        LoginPwEt.addTextChangedListener(this);
        LoginAccEt.setOnFocusChangeListener(this);
        LoginPwEt.setOnFocusChangeListener(this);

        ibtn_clean1 = $(R.id.Login_clean1);
        ibtn_eye = $(R.id.Login_eye);
        ibtn_clean2 = $(R.id.Login_clean2);
        ibtn_clean1.setOnClickListener(this);
        ibtn_eye.setOnClickListener(this);
        ibtn_clean2.setOnClickListener(this);

        acc_view = $(R.id.Login_view1);
        pw_view = $(R.id.Login_view2);

        loginBt = $(R.id.Login_bt);
        loginBt.setEnabled(false);
        loginBt.setOnClickListener(this);

        tv_fogot = $(R.id.Login_forgot_pw);
        tv_sm_login = $(R.id.Login_sm_tv);
        tv_rg = $(R.id.Login_rg_tv);
        tv_fogot.setOnClickListener(this);
        tv_sm_login.setOnClickListener(this);
        tv_rg.setOnClickListener(this);

    }
}
