package com.hfad.ecolog.Emissions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.Main_Activity.Main_Menu;
import com.hfad.ecolog.R;

public class Emissions_Communal extends AppCompatActivity {
    EditText Electricity, Household, NaturalGas, Water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_communal);

        Electricity = findViewById(R.id.Electricity);
        Household = findViewById(R.id.Household);
        NaturalGas = findViewById(R.id.NaturalGas);
        Water = findViewById(R.id.Water);
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

        Intent intent = new Intent(this, Emissions_Car.class);
        intent.putExtra("E_Communal", Communal);
        startActivity(intent);
        finish();
    }

    public void onClickButtonMainMenu(View view){
        Intent intent =  new Intent(this, Main_Menu.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonBack(View view){
        Intent intent =  new Intent(this, Main_Menu.class); //Поправить кнопку назад
        startActivity(intent);
        finish();
    }
}