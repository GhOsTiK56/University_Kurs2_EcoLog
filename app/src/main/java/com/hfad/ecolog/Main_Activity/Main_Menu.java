package com.hfad.ecolog.Main_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.hfad.ecolog.Emissions.Emissions_Communal;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.R;

public class Main_Menu extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton ButtonDrawerToggle;
    NavigationView navigationView;
    TextView textEmissions;
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        ButtonDrawerToggle = findViewById(R.id.ButtonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        textEmissions = findViewById(R.id.textEmissions);

        //Открытие боковой шторки по нажатию кнопки
        ButtonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

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

        //Тут прописывается куда отправить при нажатии на кнопку из боковой шторки
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navCalculateEmission){
                    Toast.makeText(Main_Menu.this, "Calculation Emission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main_Menu.this, Emissions_Communal.class);
                    startActivity(intent);
                }

                if (itemId == R.id.navStatistics){
                    Toast.makeText(Main_Menu.this, "Statistics", Toast.LENGTH_SHORT).show();
                }

                if (itemId == R.id.navShared){
                    Toast.makeText(Main_Menu.this, "Shared", Toast.LENGTH_SHORT).show();

                }

                if (itemId == R.id.navRecommendations){
                    Toast.makeText(Main_Menu.this, "Recommendations", Toast.LENGTH_SHORT).show();

                }

                if (itemId == R.id.navSettings){
                    Toast.makeText(Main_Menu.this, "Settings", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
    }
}