package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class AdminHomePageActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        // TODO 设置背景色样式
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setBarBackgroundColor("#EEEEEE");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_camera, "Home").setActiveColorResource(R.color.Lavender))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_gallery, "Books").setActiveColorResource(R.color.Tomato))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_send, "Music").setActiveColorResource(R.color.LemonChiffon))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_share, "Movies & TV").setActiveColorResource(R.color.MediumPurple))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_slideshow, "Games").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
