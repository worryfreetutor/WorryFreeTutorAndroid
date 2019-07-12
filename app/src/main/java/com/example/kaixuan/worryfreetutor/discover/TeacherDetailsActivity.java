package com.example.kaixuan.worryfreetutor.discover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;

public class TeacherDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("动态设置标题");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.inflateMenu(R.menu.menu_teacher_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.teacher_details_support:
                        Toast.makeText(TeacherDetailsActivity.this,"点赞",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.teacher_details_share:
                        Toast.makeText(TeacherDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.teacher_details_follow:
                        Toast.makeText(TeacherDetailsActivity.this,"关注",Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            break;
                }
                return true;
            }
        });


    }
}
