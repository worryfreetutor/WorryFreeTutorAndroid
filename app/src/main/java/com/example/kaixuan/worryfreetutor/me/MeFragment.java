package com.example.kaixuan.worryfreetutor.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseFragment;
import com.example.kaixuan.worryfreetutor.base.BaseURL;
import com.example.kaixuan.worryfreetutor.base.GetHeader;
import com.example.kaixuan.worryfreetutor.base.GetRetrofit;
import com.example.kaixuan.worryfreetutor.login.LoginActivity;
import com.example.kaixuan.worryfreetutor.net.MeProtocol;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private View mView = null;
    /**
     * 标志，记录View初始化是否完成。
     */
    private boolean isPrepared;
    /**
     * 避免多次请求数据
     */
    private boolean mHasLoadedOnce;

    private Button logOutBtn,getInfoBtn,updateInfoBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_me,container,false);
            initView();
            isPrepared = true;
            lazyLoad();
        }
        /**
         * 判断mView是否拥有parent，如果有，得删除，否则会有mView已有parent的错误

         ViewGroup parent = (ViewGroup)mView.getParent();
         if(parent != null){
         parent.removeView(mView);
         }
         */

        return mView;
    }

    /**
     * 初始化控件
     */
    private void initView() {

        logOutBtn = (Button) mView.findViewById(R.id.logout);
        getInfoBtn = (Button) mView.findViewById(R.id.get_info);
        updateInfoBtn = (Button) mView.findViewById(R.id.update_info);
        logOutBtn.setOnClickListener(this);
        getInfoBtn.setOnClickListener(this);
        updateInfoBtn.setOnClickListener(this);
    }

    @Override
    public void lazyLoad() {
        if(!isPrepared || !isVisible() || mHasLoadedOnce){
            return ;
        }
        /**
         * 在这里填充数据请求的操作
         */
        mHasLoadedOnce = true;
    }

    // 多按钮监听
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.logout:
                SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("login_token",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.putString("access_token","");
                editor.putString("refresh_token","");
                editor.putString("account","");
                editor.putString("password","");
                editor.putBoolean("logined",false);
                editor.apply();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.get_info:

                Retrofit retrofit1 =  GetRetrofit.getR();
                MeProtocol meProtocol1 = retrofit1.create(MeProtocol.class);
                Call<String> call1 = meProtocol1.get_info(GetHeader.getAccessRefresh(mView.getContext()));
                call1.enqueue(new Callback<String>() {
                                 @Override
                                 public void onResponse(Call<String> call, Response<String> response) {
                                     try {
                                         JSONObject jsonObject = new JSONObject(response.body());
                                         if(jsonObject.getString("code").equals("0")) {
                                             JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                             System.out.println("MeFragment"+jsonObject1.getString("account"));
                                             System.out.println("MeFragment"+jsonObject1.getString("nickname"));
                                             System.out.println("MeFragment"+jsonObject1.getString("name"));
                                             System.out.println("MeFragment"+jsonObject1.getString("avatar"));
                                             System.out.println("MeFragment"+jsonObject1.getString("sex"));
                                             System.out.println("MeFragment"+jsonObject1.getString("per_signature"));
                                             System.out.println("MeFragment"+jsonObject1.getString("is_teacher"));
                                             System.out.println("MeFragment"+jsonObject1.getString("is_student"));
                                             System.out.println("MeFragment"+jsonObject1.getString("tutor_num"));
                                             System.out.println("MeFragment"+jsonObject1.getString("student_num"));
                                             System.out.println("MeFragment"+jsonObject1.getString("average_score"));
                                         }
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }

                                 @Override
                                 public void onFailure(Call<String> call, Throwable t) {

                                 }
                             }
                );
                break;
            case R.id.update_info:
                String nickname = "Felix";
                String per_signature = "felix 666";

                Retrofit retrofit2 =  GetRetrofit.getR();
                MeProtocol meProtocol2 = retrofit2.create(MeProtocol.class);
                Call<String> call2 = meProtocol2.update_info(GetHeader.getAccessRefresh(mView.getContext()),nickname,"",per_signature);
                call2.enqueue(new Callback<String>() {
                                 @Override
                                 public void onResponse(Call<String> call, Response<String> response) {

                                 }

                                 @Override
                                 public void onFailure(Call<String> call, Throwable t) {

                                 }
                             }
                );
                break;
                default:break;
        }
    }
}

//
//public class MeFragment extends Fragment {
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_me, container, false);
//        return rootView;
//    }
//}