package com.hfad.ecolog.Emissions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.Main_Activity.Main_Menu;
import com.hfad.ecolog.R;

public class Emissions_Resolve extends AppCompatActivity  {
    TextView CarView;
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_resolve);
        CarView = findViewById(R.id.CarView);

        Intent intent = getIntent();
        float defaultValue = 0.0F;
        float E_Communal = intent.getFloatExtra("E_Communal", defaultValue);
        float E_Car = intent.getFloatExtra("E_Car", defaultValue);
        String email = intent.getStringExtra("Email");

        myDbManager = new MyDbManager(this);

        float E_Resolve = E_Communal + E_Car;
        CarView.append(Float.toString(E_Resolve));

        myDbManager.OpenDb();
        myDbManager.insertToDbEmissions(email, E_Communal, E_Car, E_Resolve);


        myDbManager.CloseDb();
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

    public void onClickButtonBack(View view){
        Intent intent =  new Intent(this, Emissions_Car.class);
        startActivity(intent);
        finish();
    }
}