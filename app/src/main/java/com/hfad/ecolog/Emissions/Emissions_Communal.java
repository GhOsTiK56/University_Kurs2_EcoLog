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

public class Emissions_Communal extends AppCompatActivity {
    EditText Electricity, Household, NaturalGas, Water;
    ImageView imageStick;
    private View darkOverlay;
    private static final String EMISSIONS_COMMUNAL_PREFS_NAME = "EmissionsCommunalPrefsFile";
    private static final String EMISSIONS_COMMUNAL_IMAGE_STICK_SHOWN_KEY = "EmissionsCommunalImageStickShown";
    private static final String EMISSIONS_COMMUNAL_DARK_OVERLAY_SHOWN_KEY = "EmissionsCommunalDarkOverlayShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_communal);

        Electricity = findViewById(R.id.Electricity);
        Household = findViewById(R.id.Household);
        NaturalGas = findViewById(R.id.NaturalGas);
        Water = findViewById(R.id.Water);
        imageStick = findViewById(R.id.imageStick);
        darkOverlay = findViewById(R.id.darkOverlay);

        // Получение состояния показа из SharedPreferences
        SharedPreferences settings = getSharedPreferences(EMISSIONS_COMMUNAL_PREFS_NAME, 0);
        boolean imageStickShown = settings.getBoolean(EMISSIONS_COMMUNAL_IMAGE_STICK_SHOWN_KEY, false);
        boolean darkOverlayShown = settings.getBoolean(EMISSIONS_COMMUNAL_DARK_OVERLAY_SHOWN_KEY, false);

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
                editor.putBoolean(EMISSIONS_COMMUNAL_IMAGE_STICK_SHOWN_KEY, true);
                editor.putBoolean(EMISSIONS_COMMUNAL_DARK_OVERLAY_SHOWN_KEY, true);
                editor.apply();
            }
        });
    }
    public void onClickButton(View view){

        float _Electricity = 0.0F;
        float _Household  = 0.0F;
        float _NaturalGas = 0.0F;
        float _Water = 0.0F;

        if (!Electricity.getText().toString().isEmpty())
            _Electricity = Float.parseFloat(Electricity.getText().toString());
        if (!Household.getText().toString().isEmpty())
            _Household  = Float.parseFloat(Household.getText().toString());
        if (!NaturalGas.getText().toString().isEmpty())
            _NaturalGas = Float.parseFloat(NaturalGas.getText().toString());
        if (!Water.getText().toString().isEmpty())
            _Water = Float.parseFloat(Water.getText().toString());

        //Расчет выбросов за электричество, данные вводить в кВт
        float _Electricity1 = 0.23317F - 0.21461F;
        float _Electricity2 = 0.21461F + _Electricity1;
        float _Electricity3 = _Electricity * _Electricity2 / 1000;

        //Расчет выбросов за Бытовой газ (плита газовая), данные вводить в кубометрах
        float _Household1 = _Household * 10.55F;
        float _Household2 = (_Household1 * 0.18F) / 1000;

        //Расчет выбросов за отопление, данные вводить в кубометрах
        float _NaturalGas1 = _NaturalGas * 10.55F; // Переводим из кубометров в кВт
        float _NaturalGas2 = (_NaturalGas1 * 0.18F) / 1000;

        //Расчет выбросов за воду, данные вводить в кубометрах
        float _Water1 = _Water * 10.55F;
        float _Water2 = (_Water1 * 0.17F) / 1000;

        //Сумма коммуналки
        float Communal = _Electricity3 + _Household2 + _NaturalGas2 + _Water2;

        Intent intent1 = getIntent();
        String UserId = intent1.getStringExtra("UserId");

        Intent intent = new Intent(this, Emissions_Car.class);
        intent.putExtra("E_Communal", Communal);
        intent.putExtra("UserId", UserId);
        startActivity(intent);
        finish();
    }
}