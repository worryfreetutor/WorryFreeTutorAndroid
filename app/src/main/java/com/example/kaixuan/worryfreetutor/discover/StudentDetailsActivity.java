package com.example.kaixuan.worryfreetutor.discover;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;


public class StudentDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//Activity中设置状态栏透明：

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("可动态设置标题");

        // 设置navigation button 点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AppBarLayout mAppBarLayout= (AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.blue),Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange()));
            }
        });


        //Menu
        toolbar.inflateMenu(R.menu.menu_student_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.student_details_complaint:
                        Toast.makeText(StudentDetailsActivity.this,"投诉",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.student_details_favorite:
                        Toast.makeText(StudentDetailsActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.student_details_share:
                        Toast.makeText(StudentDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        break;

                }
                return false;
            }
        });

        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_details,menu);
        return true;
    }

    /** 根据百分比改变颜色透明度 */
    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

   private void initView() {

   }


}
