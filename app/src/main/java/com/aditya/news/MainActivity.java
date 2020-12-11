package com.aditya.news;

import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aditya.news.fragment.Dashboard;
import com.aditya.news.fragment.Home;
import com.aditya.news.fragment.Notification;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChipNavigationBar navView = findViewById(R.id.bottonnav);
        frameLayout = findViewById(R.id.framelayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Home()).commit();
        getSupportActionBar().setTitle("For you");
        navView.setItemSelected(R.id.navigation_home, true);
        navView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                String title = "";
                switch (i) {
                    case R.id.navigation_home:
//                        Toast toast=Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER,0,0);
//                        toast.show();
                        fragment = new Home();
                        title = "For you";
                        break;
                    case R.id.navigation_dashboard:
//                        Toast toast1=Toast.makeText(MainActivity.this, "dashboard Selected", Toast.LENGTH_SHORT);
//                        toast1.setGravity(Gravity.CENTER,0,0);
//                        toast1.show();
                        fragment = new Dashboard();
                        title = "Headlines";
                        break;
                    case R.id.navigation_notifications:
//                        Toast toast2=Toast.makeText(MainActivity.this, "notifications Selected", Toast.LENGTH_SHORT);
//                        toast2.setGravity(Gravity.CENTER,0,0);
//                        toast2.show();
                        fragment = new Notification();
                        title = "Newsstand";
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
                    getSupportActionBar().setTitle(title);
                }
            }
        });
//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                switch (item.getItemId()){
//                    case R.id.navigation_home:
//                        Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
//                        fragment=new Home();
//                        break;
//                    case R.id.navigation_dashboard:
//                        Toast.makeText(MainActivity.this, "dashboard Selected", Toast.LENGTH_SHORT).show();
//                        fragment=new Dashboard();
//                        break;
//                    case R.id.navigation_notifications:
//                        Toast.makeText(MainActivity.this, "notifications Selected", Toast.LENGTH_SHORT).show();
//                        fragment=new Notification();
//                        break;
//                }
//                if(fragment!=null){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
//                }
//                return true;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}