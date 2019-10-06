package com.example.kaixuan.worryfreetutor.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
 * Created by huihui on 2019/5/30.
 */

public class StartActivity extends AppCompatActivity
{
    /**
     * 使用SharedPreferences 进行本地储存，实现用户跳过登录操作
     *SP保存的文件是应用的私有文件，其他应用（和用户）不能访问这些文件
     *不支持多进程调用？
     * */
    private Handler handler = new Handler();
    private Boolean HaveLogined = false;
    private Intent intent;//决定跳转到哪里
    private String access_token;
    private String refresh_token;
    private SharedPreferences sharedPreferences;
    private String account;
    private String password;
    //private CountDownLatch c1;// 保证线程顺序执行

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /*
        try
        {
            Thread.sleep(1000); //线程休眠1s，使出现白屏时的效果更加明显
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylg_start);

        sharedPreferences = getSharedPreferences("login_token",MODE_PRIVATE);
        access_token = sharedPreferences.getString("access_token","");
        refresh_token = sharedPreferences.getString("refresh_token","");
        account = sharedPreferences.getString("account","");
        password = sharedPreferences.getString("password","");
        HaveLogined = sharedPreferences.getBoolean("logined",false);

        System.out.println("StartActivity:"+"access_token   "+access_token+"refresh_token   "+refresh_token+"account    "+account+"password "+password+"HaveLogined"+HaveLogined);

        // 方案一，太慢，后台改接口
        /**1、得通过获取用户自己的信息接口，看下access_token是否过期，2、若过期则通过刷新令牌接口接口refresh_token
         * 更新access_token，3、继续请求获取用户自己的信息接口，看下access_token是否过期 3、若refresh_token过期则重新登录（网络请求）
         * ---------------三次有先后关系的无意义请求，太慢了！------------------
         **/
        // 方案二，本地保存，不安全
        /**本地保存用户的账号密码，若HaveLogined为true，则自动登录网络请求，进行登录。
         */

       //Log.w("StartActivityMain",Thread.currentThread().getId()+"");
        //c1 = new CountDownLatch(1);


        //展示2s后前往主页
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(HaveLogined)
                {
                    //如果之前已经登录过则直接跳转主页面
                    intent = new Intent(StartActivity.this, MainActivity.class);

                    /**在此处发起网络请求
                    */
                     Retrofit retrofit = new Retrofit.Builder()
                            .client(new OkHttpClient())
                            .baseUrl(BaseURL.getBaseUrl())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();

                    loginProtocol lp = retrofit.create(loginProtocol.class);
                    Call<String> call = lp.mLogin(account,password);
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

                                    /**
                                     * 存储用户登录后的token信息。
                                     */
                                    SharedPreferences  sharedPreferences =  getSharedPreferences("login_token",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("access_token",jsonObject.getString("access_token"));
                                    editor.putString("refresh_token",jsonObject.getString("refresh_token"));
                                    editor.putString("account",account);
                                    editor.putString("password",password);
                                    editor.putBoolean("logined",true);
                                    editor.apply();
                                    //Log.w("StartActivity1",Thread.currentThread().getId()+"");
                                    //intent = new Intent(StartActivity.this, MainActivity.class);
                                    //Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                    //startActivity(intent);
                                    //finish();

                                }else{
                                    str = jsonObject.getString("message");
                                }
                                //同步需要Looper.prepare()，异步不需要。
                                Toast.makeText(StartActivity.this, str, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }finally {
                             //   c1.countDown();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // 请求失败,do something
                           // c1.countDown();
                        }
                    });
                    /**-----------------------------------------------------------------------------*/
                    //Log.w("StartActivity3",Thread.currentThread().getId()+"");
                    //c1.countDown();

                }
                else {
                    intent = new Intent(StartActivity.this, ChooseActivity.class);
                    //c1.countDown();
                }


                /**
                 try {
                    c1.await();
                 } catch (InterruptedException e) {
                    e.printStackTrace();
                 }

                 Log.w("StartActivity2",Thread.currentThread().getId()+"");
                 这两个的线程ID都和主线程的ID一致，但外部先执行，还得进行同步
                 2019-09-30 17:49:42.531 32357-32357/com.example.kaixuan.worryfreetutor W/StartActivity2: 1
                 2019-09-30 17:49:43.947 32357-32357/com.example.kaixuan.worryfreetutor W/StartActivity1: 1
                 */
                startActivity(intent);
                finish();
                // StartActivity1: 1 是在最后执行的，而且如果进行同步操作，甚至会产生死锁，也就是如果StartActivity2不执行，StartActivity1那部分代码甚至无法执行，这是为什么呢？



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
