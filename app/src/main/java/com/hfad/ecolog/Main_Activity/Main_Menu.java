/*
Класс Main_Menu главное окно приложения, из которого можно попасть в другие activity и обратно, на него выводятся данные из БД.
В этом классе реализован 1 метод:
onCreate - Находятся элементы в UI, затем из БД получаем данные, суммируем их и выводим на экран. Дополнительно если нажать на иконку фото, то выведется надпись.
*/
package com.hfad.ecolog.Main_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.hfad.ecolog.DataBaseUsers.MyDbManagerUsers;
import com.hfad.ecolog.Drawer.Drawer_Manager;
import com.hfad.ecolog.Drawer.Image_Button_Click_Listener;
import com.hfad.ecolog.Drawer.Navigation_Item_Click_Listener;
import com.hfad.ecolog.R;

public class Main_Menu extends AppCompatActivity {
    Drawer_Manager drawerManager;
    DrawerLayout drawerLayout;
    ImageButton ButtonDrawerToggle;
    NavigationView navigationView;
    ImageView imageStick;
    private View darkOverlay;
    TextView textEmissions, textUserName;
    MyDbManagerUsers myDbManagerUsers;
    private static final String MAIN_MENU_PREFS_NAME = "MainMenuPrefsFile";
    private static final String MAIN_MENU_IMAGE_STICK_SHOWN_KEY = "MainMenuImageStickShown";
    private static final String MAIN_MENU_DARK_OVERLAY_SHOWN_KEY = "MainMenuDarkOverlayShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        ButtonDrawerToggle = findViewById(R.id.ButtonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);
        textEmissions = findViewById(R.id.textEmissions);
        textUserName = findViewById(R.id.textViewName);
        imageStick = findViewById(R.id.imageStick);
        darkOverlay = findViewById(R.id.darkOverlay);

        // Получение состояния показа из SharedPreferences
        SharedPreferences settings = getSharedPreferences(MAIN_MENU_PREFS_NAME, 0);
        boolean imageStickShown = settings.getBoolean(MAIN_MENU_IMAGE_STICK_SHOWN_KEY, false);
        boolean darkOverlayShown = settings.getBoolean(MAIN_MENU_DARK_OVERLAY_SHOWN_KEY, false);

        // Если imageStick был показан ранее, делаем его невидимым
        if (imageStickShown) {
            imageStick.setVisibility(View.INVISIBLE);
        }

        // Если darkOverlay был показан ранее, делаем его невидимым
        if (darkOverlayShown) {
            darkOverlay.setVisibility(View.INVISIBLE);
        }

        drawerManager = new Drawer_Manager(drawerLayout);
        Intent intent = getIntent();
        String UserId = intent.getStringExtra("UserId");

        ButtonDrawerToggle.setOnClickListener(new Image_Button_Click_Listener(drawerManager));
        navigationView.setNavigationItemSelectedListener(new Navigation_Item_Click_Listener(this, UserId, drawerLayout));

        //Вывод из БД данных
        myDbManagerUsers = new MyDbManagerUsers(this);
        myDbManagerUsers.OpenDb();

        float E_Resolve = myDbManagerUsers.getEResolveForUser(UserId);
        @SuppressLint("DefaultLocale") String Resolve = String.format("%.2f", E_Resolve);
        textEmissions.setText(String.valueOf(Resolve));

        String userName = myDbManagerUsers.getUserName(UserId);
        textUserName.setText(userName);

        //Если жмакаем на фотку то выведется надпись
        View headerView = navigationView.getHeaderView(0);
        ImageView imageUserPhoto = headerView.findViewById(R.id.imageUserPhoto);
        TextView textUserName = headerView.findViewById(R.id.textUserName);

        myDbManagerUsers.CloseDb();
        imageUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main_Menu.this, textUserName.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        imageStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeOutImage = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_image);
                imageStick.startAnimation(fadeOutImage);

                Animation fadeOutOverlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_overlay);
                View darkOverlay = findViewById(R.id.darkOverlay);
                darkOverlay.startAnimation(fadeOutOverlay);

                fadeOutImage.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imageStick.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                fadeOutOverlay.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        darkOverlay.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(MAIN_MENU_IMAGE_STICK_SHOWN_KEY, true);
                editor.putBoolean(MAIN_MENU_DARK_OVERLAY_SHOWN_KEY, true);
                editor.apply();
            }
        });
    }
    public void onClickButtonRec(View view){
        Intent intent = new Intent(this, Recomendation_Activity.class);
        startActivity(intent);
        finish();
    }
}