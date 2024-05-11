package com.hfad.ecolog.Emissions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.R;

public class Emissions_Car extends AppCompatActivity {
    private EditText Kilometers, Days;
    ImageView imageStick;
    private View darkOverlay;
    private static final String EMISSIONS_CAR_PREFS_NAME = "EmissionsCarPrefsFile";
    private static final String EMISSIONS_CAR_IMAGE_STICK_SHOWN_KEY = "EmissionsCarImageStickShown";
    private static final String EMISSIONS_CAT_DARK_OVERLAY_SHOWN_KEY = "EmissionsCarDarkOverlayShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_car);

        Kilometers = findViewById(R.id.Kilometrs);
        Days = findViewById(R.id.Days);
        imageStick = findViewById(R.id.imageStick);
        darkOverlay = findViewById(R.id.darkOverlay);

        // Получение состояния показа из SharedPreferences
        SharedPreferences settings = getSharedPreferences(EMISSIONS_CAR_PREFS_NAME, 0);
        boolean imageStickShown = settings.getBoolean(EMISSIONS_CAR_IMAGE_STICK_SHOWN_KEY, false);
        boolean darkOverlayShown = settings.getBoolean(EMISSIONS_CAT_DARK_OVERLAY_SHOWN_KEY, false);

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
                editor.putBoolean(EMISSIONS_CAR_IMAGE_STICK_SHOWN_KEY, true);
                editor.putBoolean(EMISSIONS_CAT_DARK_OVERLAY_SHOWN_KEY, true);
                editor.apply();
            }
        });
    }


    public void onClickButton(View view){
        float Car1 = 0.0F;
        float Car2 = 0.0F;
        float defaultValue = 0.0F;

        if (!Kilometers.getText().toString().isEmpty())
            Car1 = Float.parseFloat(Kilometers.getText().toString());
        if (!Days.getText().toString().isEmpty())
            Car2 = Float.parseFloat(Days.getText().toString());

        float Car3  = (Car1 * Car2 * 0.26709F) / 1000F;

        Intent intent1 = getIntent();
        float E_Communal = intent1.getFloatExtra("E_Communal", defaultValue);
        String UserId = intent1.getStringExtra("UserId");

        Intent intent2 = new Intent(this, Emissions_Resolve.class);
        intent2.putExtra("E_Car", Car3);
        intent2.putExtra("E_Communal", E_Communal);
        intent2.putExtra("UserId", UserId);
        startActivity(intent2);
        finish();
    }
}