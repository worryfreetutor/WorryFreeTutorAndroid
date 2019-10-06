package com.example.kaixuan.worryfreetutor.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.kaixuan.worryfreetutor.net.loginProtocol;
import retrofit2.Retrofit;

import static com.example.kaixuan.worryfreetutor.base.SimplifyLogin.getNewcode;
import static com.example.kaixuan.worryfreetutor.base.SimplifyLogin.setNewcode;

public class FogotPWActivity extends AppCompatActivity  implements View.OnClickListener,TextWatcher {


    private EditText phoneEt, authEt,pwEt,surePwEt;
    private ImageButton ibtn_clean1,ibtn_clean2,ibtn_clean3,ibtn_clean4,ibtn_pw_eye,ibtn_surepw_eye;
    private Button FogotPWBt;
    private TimingButton checkBt;
    private static int newcode; // 注意，这是个静态变量
    private Context context;
    private String code;
    private Boolean isPwOpenEye = false,isSurePwOpenEye = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylg_fogotpw);
        initView();
        init();
    }
    @Override
    protected void onResume(){
        super.onResume();

        /**
         * 防止activity变换时，造成FogotPWBt状态错误
         * */
        rbtnEnabled();
        if(!phoneEt.getText().toString().isEmpty()){
            checkBt.setEnabled(true);
        }else {
            checkBt.setEnabled(false);
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
        if ((v instanceof EditText) || (v instanceof  ImageButton)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域， 点击的是ImageButton区域，
                //则保留点击事件
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
                inputMethodManager.hideSoftInputFromWindow(FogotPWActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 对话框
     * * @setIcon 设置对话框图标
     * @setTitle 设置对话框标题
     * @setMessage 设置对话框消息提示
     * setXXX方法返回Dialog对象，因此可以链式设置属性
     */

    private void showNormalDialog()
    {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(FogotPWActivity.this);
        normalDialog.setTitle("xxx免责条例");
        normalDialog.setMessage("各种内容……");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //...To-do
            }
        });
        normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //...To-do
            }
        });
        // 显示
        normalDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FogotPW_auth_btn:
                if(phoneEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkBt.start();

                    /*验证码是随机生产的，不安全*/
                    setNewcode();
                    code = Integer.toString(getNewcode());

                    Toast.makeText(context,"正在发送验证码，请注意查收", Toast.LENGTH_SHORT).show();

                    //发短信
                    //MessageShort.ms(phoneEt.getText().toString(),code);

                    Toast.makeText(context, "手机号" + phoneEt.getText().toString()
                            + "发送的验证码为：" + code, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.FogotPW_bt:
                if(phoneEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(authEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(!authEt.getText().toString().equals(code))
                {
                    Toast.makeText(context, "验证码错误！", Toast.LENGTH_SHORT).show();
                }
                else if(pwEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if (pwEt.getText().toString().length()<6)
                {
                    Toast.makeText(context, "密码长度不能小于六位！", Toast.LENGTH_SHORT).show();
                }
                else if(surePwEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "确认密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(!pwEt.getText().toString().equals(surePwEt.getText().toString()))
                {
                    Toast.makeText(context, "密码与确认密码不符！", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    //发起网络请求

                    // 创建Retrofit实例
                    Retrofit retrofit = GetRetrofit.getR();
                    //生成代理接口
                    loginProtocol lp = retrofit.create(loginProtocol.class);
                    //调用具体接口方法
                    /*
                    final Call<String> call = lp.mFogotPW(phoneEt.getText().toString(),surePwEt.getText().toString());

                    //同步请求(数据量不大),不用使用Handler，如何做到？但响应时间若大于5S,ANR
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Response<String> response = call.execute();
                                // 防止空指针错误 ，秒退
                                if(response.body() != null) {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    //2. 内部类和外部类并不在同一个class文件，所有无法直接改变其参数，故底层设计人员需其定义为final
                                    //3. r_code = jsonObject.getString("code");
                                    //4.
                                    String str ;
                                    if(jsonObject.getString("code") .equals("0")){
                                        // 若在这里放finish(),有效果？
                                        // 罢了，还是handler最合适,注意内存泄漏问题 但是handler里面也是内部类
                                        //-----------
                                        //Android是不能直接在子线程中弹出Toast的
                                        str = "修改密码成功！";

                                        //跳转至登录界面
                                        Intent intent = new Intent(FogotPWActivity.this, LoginActivity.class);
                                        intent.putExtra("account",phoneEt.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    }else if(jsonObject.getString("code") .equals("010101")){
                                        // 账号类型哪不对
                                        str = jsonObject.getString("message");
                                    }else if(jsonObject.getString("code") .equals("010102")){
                                        //手机号已经存在
                                        str = jsonObject.getString("message");
                                    }else {
                                        str = jsonObject.getString("请检查网络");
                                    }
                                    Looper.prepare();
                                    Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                    //Message message = Message.obtain(); // 少用 new Message(),性能差
                                    //message.obj = response.body();

                                }
                            }catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();*/
                }
                break;

            case R.id.FogotPW_clean1:
                phoneEt.setText("");
                break;
            case R.id.FogotPW_clean2:
                authEt.setText("");
                break;
            case R.id.FogotPW_clean3:
                pwEt.setText("");
                break;
            case R.id.FogotPW_clean4:
                surePwEt.setText("");
                break;
            case R.id.FogotPW_pw_eye:
                isPwOpenEye = SimplifyLogin.onClickeEye(isPwOpenEye,ibtn_pw_eye,pwEt);
                break;
            case R.id.FogotPW_surepw_eye:
                isSurePwOpenEye = SimplifyLogin.onClickeEye(isSurePwOpenEye,ibtn_surepw_eye,surePwEt);
                break;
            default:

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        SimplifyLogin.etCleanVisibility(phoneEt, ibtn_clean1);
        SimplifyLogin.etCleanVisibility(authEt, ibtn_clean2);
        SimplifyLogin.etCleanVisibility(pwEt, ibtn_clean3);
        SimplifyLogin.etCleanVisibility(surePwEt, ibtn_clean4);
        rbtnEnabled();
        if(!phoneEt.getText().toString().isEmpty()){
            checkBt.setEnabled(true);
        }else {
            checkBt.setEnabled(false);
        }
    }

    private void init(){
        context = this.getApplicationContext();
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    private void initView()
    {
        phoneEt =  $(R.id.FogotPW_phone_et);
        authEt = $(R.id.FogotPW_auth_et);
        pwEt =  $(R.id.FogotPW_pw_et);
        surePwEt =  $(R.id.FogotPW_surepw_et);
        phoneEt.addTextChangedListener(this);
        authEt.addTextChangedListener(this);
        pwEt.addTextChangedListener(this);
        surePwEt.addTextChangedListener(this);

        checkBt =  $(R.id.FogotPW_auth_btn);
        checkBt.setOnClickListener(this);

        FogotPWBt =  $(R.id.FogotPW_bt);
        FogotPWBt.setEnabled(false);
        FogotPWBt.setOnClickListener(this);

        ibtn_clean1 =  $(R.id.FogotPW_clean1);
        ibtn_clean2 =  $(R.id.FogotPW_clean2);
        ibtn_clean3 =  $(R.id.FogotPW_clean3);
        ibtn_clean4 =  $(R.id.FogotPW_clean4);
        ibtn_pw_eye =  $(R.id.FogotPW_pw_eye);
        ibtn_surepw_eye =  $(R.id.FogotPW_surepw_eye);

        ibtn_clean1.setOnClickListener(this);
        ibtn_clean2.setOnClickListener(this);
        ibtn_clean3.setOnClickListener(this);
        ibtn_clean4.setOnClickListener(this);
        ibtn_pw_eye.setOnClickListener(this);
        ibtn_surepw_eye.setOnClickListener(this);


    }

    private void rbtnEnabled(){
        if(!phoneEt.getText().toString().isEmpty() && !authEt.getText().toString().isEmpty()
                && !pwEt.getText().toString().isEmpty() && !surePwEt.getText().toString().isEmpty()){
            SimplifyLogin.btnEnabled(this, FogotPWBt, true);
        }else {
            SimplifyLogin.btnEnabled(this, FogotPWBt, false);
        }

    }
}
