package com.example.mymfnapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import static com.example.mymfnapp.AliyunSmsUtils.querySendDetails;
import static com.example.mymfnapp.AliyunSmsUtils.sendSms;

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
                    setNewcode();
                    code = Integer.toString(getNewcode());
                    Toast.makeText(context,"正在发送验证码，请注意查收", Toast.LENGTH_SHORT).show();

                    //发短信
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
                                System.out.println("Code=" + response.getCode());
                                System.out.println("Message=" + response.getMessage());
                                System.out.println("RequestId=" + response.getRequestId());
                                System.out.println("BizId=" + response.getBizId());


                                //查明细
                                if(response.getCode() != null && response.getCode().equals("OK"))
                                {
                                    QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
                                    System.out.println("短信明细查询接口返回数据----------------");
                                    System.out.println("Code=" + querySendDetailsResponse.getCode());
                                    System.out.println("Message=" + querySendDetailsResponse.getMessage());
                                    int i = 0;
                                    for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                                    {
                                        System.out.println("SmsSendDetailDTO["+i+"]:");
                                        System.out.println("Content=" + smsSendDetailDTO.getContent());
                                        System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                                        System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                                        System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                                        System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                                        System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                                        System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                                        System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
                                    }
                                    System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
                                    System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
                                }
                            } catch (ClientException e)
                            {
                                e.printStackTrace();
                            }
                            //System.out.println(response.getData());
                                System.out.println("结束");


                        }
                    }).start();
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
                    //此处还应检查昵称是否重复
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

                    Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show();
					
					//跳转至登录界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("account",phoneEt.getText().toString());
                    startActivity(intent);
                    finish();
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
    public static void setNewcode()
    {
        newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数
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
