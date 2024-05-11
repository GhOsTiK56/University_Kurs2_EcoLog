package com.hfad.ecolog.Emissions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.DataBaseUsers.MyDbManagerUsers;
import com.hfad.ecolog.Main_Activity.Main_Menu;
import com.hfad.ecolog.R;

public class Emissions_Resolve extends AppCompatActivity  {
    TextView ResolveView;
    private MyDbManagerUsers myDbManagerUsers;
    ImageView imageStick;
    private View darkOverlay;
    private static final String EMISSIONS_RESOLVE_PREFS_NAME = "EmissionsResolvePrefsFile";
    private static final String EMISSIONS_RESOLVE_IMAGE_STICK_SHOWN_KEY = "EmissionsResolveImageStickShown";
    private static final String EMISSIONS_RESOLVE_DARK_OVERLAY_SHOWN_KEY = "EmissionsResolveDarkOverlayShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_resolve);
        ResolveView = findViewById(R.id.ResolveView);
        imageStick = findViewById(R.id.imageStick);
        darkOverlay = findViewById(R.id.darkOverlay);

        // Получение состояния показа из SharedPreferences
        SharedPreferences settings = getSharedPreferences(EMISSIONS_RESOLVE_PREFS_NAME, 0);
        boolean imageStickShown = settings.getBoolean(EMISSIONS_RESOLVE_IMAGE_STICK_SHOWN_KEY, false);
        boolean darkOverlayShown = settings.getBoolean(EMISSIONS_RESOLVE_DARK_OVERLAY_SHOWN_KEY, false);

        // Если картинка была показана ранее, делаем ее невидимой
        if (imageStickShown) {
            imageStick.setVisibility(View.INVISIBLE);
        }

        // Если darkOverlay был показан ранее, делаем его невидимым
        if (darkOverlayShown) {
            darkOverlay.setVisibility(View.INVISIBLE);
        }

        imageStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeOutImage = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_image);
                imageStick.startAnimation(fadeOutImage);

                Animation fadeOutOverlay = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_overlay);
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
                editor.putBoolean(EMISSIONS_RESOLVE_IMAGE_STICK_SHOWN_KEY, true);
                editor.putBoolean(EMISSIONS_RESOLVE_DARK_OVERLAY_SHOWN_KEY, true);
                editor.apply();
            }
        });

        Intent intent = getIntent();
        float defaultValue = 0.0F;
        float E_Communal = intent.getFloatExtra("E_Communal", defaultValue);
        float E_Car = intent.getFloatExtra("E_Car", defaultValue);
        String UserId = intent.getStringExtra("UserId");

        myDbManagerUsers = new MyDbManagerUsers(this);

        float E_Resolve = E_Communal + E_Car;
        @SuppressLint("DefaultLocale") String Resolve = String.format("%.2f", E_Resolve);
        ResolveView.append(String.valueOf(Resolve));

        myDbManagerUsers.OpenDb();
        myDbManagerUsers.insertToDbEmissions(UserId, E_Communal, E_Car, E_Resolve);


        myDbManagerUsers.CloseDb();
    }

    public void onClickButtonRecalculate(View view){
        Intent intent = new Intent(this, Emissions_Communal.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonMainMenu(View view){
        Intent intent =  new Intent(this, Main_Menu.class);
        startActivity(intent);
        finish();
    }
}