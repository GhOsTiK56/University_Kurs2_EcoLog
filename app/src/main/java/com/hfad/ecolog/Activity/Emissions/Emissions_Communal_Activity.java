package com.hfad.ecolog.Activity.Emissions;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hfad.ecolog.Activity.Emissions.Emissions_Car_Activity;
import com.hfad.ecolog.Activity.Main_Menu_Activity;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.R;

public class Emissions_Communal_Activity extends AppCompatActivity {
    EditText Electricity, Household, NaturalGas, Water;
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_communal_activity);

        Electricity = findViewById(R.id.Electricity);
        Household = findViewById(R.id.Household);
        NaturalGas = findViewById(R.id.NaturalGas);
        Water = findViewById(R.id.Water);

        myDbManager = new MyDbManager(this);
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

        //Расчет выбпросов за отопление, данные вводить в кубометрах
        float _NaturalGas1 = _NaturalGas * 10.55F; // Переводим из кубометров в кВт
        float _NaturalGas2 = (_NaturalGas1 * 0.18F) / 1000;

        //Расчет выбросов за воду, данные вводить в кубометрах
        float _Water1 = _Water * 10.55F;
        float _Water2 = (_Water1 * 0.17F) / 1000;

        //Сумма коммуналки
        float Communal = _Electricity3 + _Household2 + _NaturalGas2 + _Water2;

        myDbManager.openDb();//Открыли таблицу
        myDbManager.updateOrInsertDb(1,Communal);
        myDbManager.CloseDb();

        Intent intent = new Intent(this, Emissions_Car_Activity.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonMainMenu(View view){
        Intent intent =  new Intent(this, Main_Menu_Activity.class);
        startActivity(intent);
        finish();
    }
    public void onClickButtonBack(View view){
        Intent intent =  new Intent(this, Main_Menu_Activity.class);
        startActivity(intent);
    }
}