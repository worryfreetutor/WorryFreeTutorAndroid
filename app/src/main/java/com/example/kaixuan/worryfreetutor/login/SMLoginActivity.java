package com.example.kaixuan.worryfreetutor.login;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.SimplifyLogin;

import static com.example.kaixuan.worryfreetutor.base.SimplifyLogin.getNewcode;
import static com.example.kaixuan.worryfreetutor.base.SimplifyLogin.setNewcode;

public class SMLoginActivity extends Activity implements View.OnClickListener,TextWatcher {

    private EditText phoneEt,authEt;
    private ImageButton ibtn_clean1,ibtn_clean2;
    private Button SMLogin_btn;
    private TimingButton checkBt;
    private Context context;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylg_smlogin);
        initView();
        init();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(!phoneEt.getText().toString().isEmpty()){
            checkBt.setEnabled(true);
        }else{
            checkBt.setEnabled(false);
        }
        if(!phoneEt.getText().toString().isEmpty() && !authEt.getText().toString().isEmpty()) {
            SimplifyLogin.btnEnabled(this, SMLogin_btn, true);
        }else {
            SimplifyLogin.btnEnabled(this, SMLogin_btn, false);
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
        SimplifyLogin.etCleanVisibility(phoneEt,ibtn_clean1);
        SimplifyLogin.etCleanVisibility(authEt,ibtn_clean2);
        if(!phoneEt.getText().toString().isEmpty()){
            checkBt.setEnabled(true);
        }else{
            checkBt.setEnabled(false);
        }
        if(!phoneEt.getText().toString().isEmpty() && !authEt.getText().toString().isEmpty()) {
            SimplifyLogin.btnEnabled(this, SMLogin_btn, true);
        }else {
            SimplifyLogin.btnEnabled(this, SMLogin_btn, false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SMLogin_clean1:
                phoneEt.setText("");
                break;
            case R.id.SMLogin_clean2:
                authEt.setText("");
                break;
            case R.id.SMLogin_bt:

                break;
            case R.id.SMLogin_auth_btn:
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
        }
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    private void initView() {

        phoneEt = $(R.id.SMLogin_phone_et);
        authEt = $(R.id.SMLogin_auth_et);
        phoneEt.setOnClickListener(this);
        authEt.setOnClickListener(this);
        phoneEt.addTextChangedListener(this);
        authEt.addTextChangedListener(this);

        ibtn_clean1 = $(R.id.SMLogin_clean1);
        ibtn_clean2 = $(R.id.SMLogin_clean2);
        ibtn_clean1.setOnClickListener(this);
        ibtn_clean2.setOnClickListener(this);

        SMLogin_btn = $(R.id.SMLogin_bt);
        SMLogin_btn.setOnClickListener(this);

        checkBt = $(R.id.SMLogin_auth_btn);
        checkBt.setOnClickListener(this);
    }
    private void init() {
        context = this.getApplicationContext();
    }


}
