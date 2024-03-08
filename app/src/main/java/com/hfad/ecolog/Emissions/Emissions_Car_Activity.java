package com.hfad.ecolog.Emissions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.Activity.Main_Menu_Activity;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.R;

public class Emissions_Car_Activity extends AppCompatActivity {

    EditText KM, Days;

    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_car_activity);

        KM = findViewById(R.id.KM);
        Days = findViewById(R.id.Days);

        myDbManager = new MyDbManager(this);
    }
    public void onClickButton(View view){

        float Car1 = 0.0F;
        float Car2 = 0.0F;

        if (!KM.getText().toString().isEmpty())
            Car1 = Float.parseFloat(KM.getText().toString());
        if (!Days.getText().toString().isEmpty())
            Car2 = Float.parseFloat(Days.getText().toString());

        float Car3  = (Car1 * Car2 * 0.26709F) / 1000F;

        myDbManager.openDb();
        myDbManager.updateOrInsertDb(2, Car3);
        myDbManager.CloseDb();

        Intent intent = new Intent(this, Emissions_Resolve_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myDbManager.CloseDb();
    }

    public void onClickButtonMainMenu(View view){
        Intent intent =  new Intent(this, Main_Menu_Activity.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonBack(View view){
        Intent intent =  new Intent(this, Emissions_Communal_Activity.class);
        startActivity(intent);
    }

}