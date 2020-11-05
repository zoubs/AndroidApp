package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.myapplication.ui.adminmodule.FoodManagerActivity;
import com.example.myapplication.ui.find.FindFragment;
import com.example.myapplication.ui.info.InfoFragment;
import com.example.myapplication.ui.logup.LogUpFragment;
import com.example.myapplication.ui.user.UserManageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminHomePageActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private FindFragment findFragment;
    private InfoFragment infoFragment;
    private UserManageFragment userManageFragment;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        fab = findViewById(R.id.admin_fab);
        fab.hide();

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        // TODO 设置背景色样式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setBarBackgroundColor("#EEEEEE");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "首页").setActiveColorResource(R.color.DodgerBlue1))
                .addItem(new BottomNavigationItem(R.drawable.ic_restaurant_black_24dp, "食物管理").setActiveColorResource(R.color.Tomato))
                .addItem(new BottomNavigationItem(R.drawable.ic_people_black_24dp, "用户管理").setActiveColorResource(R.color.Orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_feedback_black_24dp, "问题反馈").setActiveColorResource(R.color.MediumPurple))
                .setFirstSelectedPosition(0)
                .initialise();
        //FIXME 页面上点击+号后，再次点击应用会闪退
        findFragment = new FindFragment();
        infoFragment = new InfoFragment();
        userManageFragment = new UserManageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_admin_container,findFragment).commitAllowingStateLoss();
        //设置底部选择菜单点击状态
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_admin_container,findFragment).addToBackStack(null).commitAllowingStateLoss();
                        fab.hide();
                        break;
                    case 1:
                        Intent intent = new Intent(AdminHomePageActivity.this, FoodManagerActivity.class);
                        startActivity(intent);
                        fab.show();
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AdminHomePageActivity.this, "食物", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_admin_container,userManageFragment, "userManageFragment").commitAllowingStateLoss();
                        fab.show();
                        final LogUpFragment logUpFragment = new LogUpFragment(true);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AdminHomePageActivity.this, "用户", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().add(R.id.fl_admin_container,logUpFragment,"logUpFragment").addToBackStack(null).commitAllowingStateLoss();
                                fab.hide();
                            }
                        });
                        //fab.show();

                        break;
                    case 3:
                        Intent intent2 = new Intent(AdminHomePageActivity.this, FoodManagerActivity.class);
                        startActivity(intent2);
                        fab.hide();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_admin_container,findFragment).commitAllowingStateLoss();
                        fab.hide();
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
                if(position == 1 || position == 2) {
                    fab.show();
                }
            }
        });
    }

}
