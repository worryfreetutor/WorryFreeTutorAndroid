package com.example.kaixuan.worryfreetutor.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseURL;
import com.example.kaixuan.worryfreetutor.net.loginProtocol;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


import java.io.IOException;


/**
 * Created by huihui on 2019/6/1.
 */

public class RegisterActivity extends AppCompatActivity
{
    private TextView nickTv,genderTv,phoneTv,checkTv,pwTv,surePwTv;
    private EditText nickEt,phoneEt,checkEt,pwEt,surePwEt;
    private Button registerBt,registerDutyBt;
    private TimingButton checkBt;
    private RadioButton manRb,womanRb;
    private CheckBox cbox;
    private static int newcode;
    private Context context;
    private String code;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Init();

        checkBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

                    /** 此功能已经测试好，待整体完成再删除注释
                    //发短信
                     private String mtest= "Register+";
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                //发短信
                                SendSmsResponse response = sendSms(phoneEt.getText().toString(),code);
                                System.out.println("短信接口返回的数据----------------");
                                System.out.println(mtest+"Code=" + response.getCode());

                                System.out.println(mtest+"Message=" + response.getMessage());
                                System.out.println(mtest+"RequestId=" + response.getRequestId());
                                System.out.println(mtest+"BizId=" + response.getBizId());


                                //查明细
                                if(response.getCode() != null && response.getCode().equals("OK"))
                                {
                                    QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
                                    System.out.println("短信明细查询接口返回数据----------------");
                                    System.out.println(mtest+"Code=" + querySendDetailsResponse.getCode());
                                    System.out.println(mtest+"Message=" + querySendDetailsResponse.getMessage());
                                    int i = 0;
                                    for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                                    {
                                        System.out.println(mtest+"SmsSendDetailDTO["+i+"]:");
                                        System.out.println(mtest+"Content=" + smsSendDetailDTO.getContent());
                                        System.out.println(mtest+"ErrCode=" + smsSendDetailDTO.getErrCode());
                                        System.out.println(mtest+"OutId=" + smsSendDetailDTO.getOutId());
                                        System.out.println(mtest+"PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                                        System.out.println(mtest+"ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                                        System.out.println(mtest+"SendDate=" + smsSendDetailDTO.getSendDate());
                                        System.out.println(mtest+"SendStatus=" + smsSendDetailDTO.getSendStatus());
                                        System.out.println(mtest+"Template=" + smsSendDetailDTO.getTemplateCode());
                                    }
                                    System.out.println(mtest+"TotalCount=" + querySendDetailsResponse.getTotalCount());
                                    System.out.println(mtest+"RequestId=" + querySendDetailsResponse.getRequestId());
                                }
                            } catch (ClientException e)
                            {
                                e.printStackTrace();
                            }
                            //System.out.println(response.getData());
                                System.out.println("结束");


                        }
                    }).start();
                    */
                    Toast.makeText(context, "手机号" + phoneEt.getText().toString()
                            + "发送的验证码为：" + code, Toast.LENGTH_SHORT).show();

                }

            }
        });

        registerBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(nickEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if (false)
                {
                    /**此处还应检查昵称是否重复
                    */
                }
                else if(!manRb.isChecked()&&!womanRb.isChecked())
                {
                    Toast.makeText(context, "请选择您的性别！", Toast.LENGTH_SHORT).show();
                }
                else if(phoneEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(checkEt.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(!checkEt.getText().toString().equals(code))
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

                    /**
                     * 修改部分
                     */
                    // 创建Retrofit实例
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(new OkHttpClient())
                            .baseUrl(BaseURL.getBaseUrl())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();

                    //生成代理接口
                    loginProtocol lp = retrofit.create(loginProtocol.class);

                    //调用具体接口方法

                    final Call<String> call = lp.mRegister(phoneEt.getText().toString(),surePwEt.getText().toString());

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
                                         str = "注册成功！";

                                         //跳转至登录界面
                                         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
                    }).start();
                }
            }
        });

        registerDutyBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showNormalDialog();
            }
        });

        cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    registerBt.setEnabled(true);
                }
                else
                {
                    registerBt.setEnabled(false);
                }
            }
        });
    }

    private void Init()
    {
        nickEt = (EditText) findViewById(R.id.Register_nickname_et);
        phoneEt = (EditText) findViewById(R.id.Register_phone_et);
        checkEt = (EditText) findViewById(R.id.Register_check_et);
        pwEt = (EditText) findViewById(R.id.Register_pw_et);
        surePwEt = (EditText) findViewById(R.id.Register_Sure_pw_et);
        checkBt = (TimingButton) findViewById(R.id.Register_check_bt);
        registerBt = (Button) findViewById(R.id.Register_bt);
        registerBt.setEnabled(false);
        registerDutyBt = (Button) findViewById(R.id.Register_duty_button);
        manRb = (RadioButton) findViewById(R.id.radioButton_man);
        womanRb = (RadioButton) findViewById(R.id.radioButton_woman);
        cbox = (CheckBox) findViewById(R.id.Register_checkBox);
        context = this.getApplicationContext();
    }

    public static int getNewcode() 
    {
        return newcode;
    }

    public static void setNewcode( )
    {
        newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数，这里根本没保证4位
        //newcode = x;
    }

    /**
     * 重写返回键，实现返回效果
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(RegisterActivity.this, ChooseActivity.class);
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
                inputMethodManager.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(), 0);
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
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(RegisterActivity.this);
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

}
