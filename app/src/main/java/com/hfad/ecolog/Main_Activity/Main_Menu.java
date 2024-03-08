package com.hfad.ecolog.Main_Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.Drawer.Drawer_manager;
import com.hfad.ecolog.Drawer.ImageButtonClickListener;
import com.hfad.ecolog.Drawer.NavigationItemClickListener;
import com.hfad.ecolog.R;

public class Main_Menu extends AppCompatActivity {
    Drawer_manager drawerManager;
    DrawerLayout drawerLayout;
    ImageButton ButtonDrawerToggle;
    NavigationView navigationView;
    TextView textEmissions;
    MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        ButtonDrawerToggle = findViewById(R.id.ButtonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        textEmissions = findViewById(R.id.textEmissions);

        drawerManager = new Drawer_manager(drawerLayout);

        ButtonDrawerToggle.setOnClickListener(new ImageButtonClickListener(drawerManager));
        navigationView.setNavigationItemSelectedListener(new NavigationItemClickListener(this));

        //Вывод из БД данных
        myDbManager = new MyDbManager(this);
        myDbManager.openDb();
        float sum = 0.0F;
        if(myDbManager.ReadFromDb().isEmpty())
            textEmissions.append("0");

        else{
            for(String num :myDbManager.ReadFromDb()){
                float Value = Float.parseFloat(num);
                sum += Value;
            }
            textEmissions.append(String.valueOf(sum)) ;
            myDbManager.CloseDb();
        }

        //Если жмакаем на фотку то выведется надпись
        View headerView = navigationView.getHeaderView(0);
        ImageView imageUserPhoto = headerView.findViewById(R.id.imageUserPhoto);
        TextView textUserName = headerView.findViewById(R.id.textUserName);

        imageUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main_Menu.this, textUserName.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}