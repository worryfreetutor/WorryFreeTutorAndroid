package com.example.mymfnapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;

/**
 * Created by huihui on 2019/6/2.
 */

public class TimingButton extends android.support.v7.widget.AppCompatButton
{

    private int total, interval;
    private String psText;

    public TimingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton);
        //total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000);
        total = 60000;
        //interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000);
        interval = 1000;
        //psText = typedArray.getString(R.styleable.TimingButton_tb_psText);
        psText = "重新获取";
        setBackgroundResource(R.drawable.timing_button); //设置默认样式
        typedArray.recycle();
    }

    //执行
    public void start() {
        TimeCount time = new TimeCount(total, interval);
        time.start();
    }

    public class TimeCount extends CountDownTimer
    {
        private long countDownInterval;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            setText(psText);
            setEnabled(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            setEnabled(false);
            setText(millisUntilFinished / countDownInterval + "秒");
        }
    }

}
