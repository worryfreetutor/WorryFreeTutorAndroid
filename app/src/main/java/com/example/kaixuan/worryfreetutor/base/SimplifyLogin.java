package com.example.kaixuan.worryfreetutor.base;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.kaixuan.worryfreetutor.R;

public class SimplifyLogin {

    public static void btnEnabled(Context context, Button btn, boolean flag){
        btn.setEnabled(flag);
        if(flag){
            GradientDrawable gradientDrawable2 = (GradientDrawable) btn.getBackground();
            gradientDrawable2.setColor(context.getResources().getColor(R.color.deepskyblue));
        }else {
            GradientDrawable gradientDrawable2 = (GradientDrawable) btn.getBackground();
            gradientDrawable2.setColor(context.getResources().getColor(R.color.skyblue));
        }
    }

    public static void etCleanVisibility(EditText editText,ImageButton ibtn){
        if(editText.getText().toString().length()>0){
            ibtn.setVisibility(View.VISIBLE);
        }else{
            ibtn.setVisibility(View.INVISIBLE);
        }
    }

    public static void  changeLineView(Context context,View view,boolean flag){
        if(flag){
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            gradientDrawable.setColor(context.getResources().getColor(R.color.deepskyblue));
            gradientDrawable.setSize(1000,200);
        }else {
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            gradientDrawable.setColor(context.getResources().getColor(R.color.skyblue));
            gradientDrawable.setSize(1000,1);
        }
    }

    public static boolean onClickeEye(boolean flag,ImageButton eye,EditText relatedEt){
        if (!flag) {
            eye.setSelected(true);
            flag = true;
            relatedEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            eye.setSelected(false);
            flag = false;
            relatedEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        //点击"eye"，将焦点移到LoginPwEt并将光标移至文字末尾
        relatedEt.requestFocus();
        relatedEt.setSelection(relatedEt.getText().toString().length());
        return flag;
    }

    private static int newcode;
    public static int getNewcode()
    {
        return newcode;
    }

    public static void setNewcode( )
    {
        newcode = (int)(Math.random()*9999)+100;  //每次调用生成一位四位数的随机数，这里根本没保证4位
        //newcode = x;
    }
}
