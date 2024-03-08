package com.hfad.ecolog.Activity.Emissions;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hfad.ecolog.Activity.Emissions.Emissions_Car_Activity;
import com.hfad.ecolog.Activity.Emissions.Emissions_Communal_Activity;
import com.hfad.ecolog.Activity.Main_Menu_Activity;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.R;

public class Emissions_Resolve_Activity extends AppCompatActivity  {

    TextView CarView;
    private MyDbManager myDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_resolve_activity);
        CarView = findViewById(R.id.CarView);

        myDbManager = new MyDbManager(this);

        myDbManager.openDb();
        float sum = 0.0F;
        if(myDbManager.ReadFromDb().isEmpty())
            CarView.append("0");

        else{
            for(String num :myDbManager.ReadFromDb()){
                float Value = Float.parseFloat(num);
                sum += Value;
                if (!num.equals("0")){
                    CarView.append(num);
                    CarView.append("\n");
                }
                else {
                    CarView.append("0");
                    CarView.append("\n");
                }
            }
            CarView.append("Сумма ваших выбросов CO2 в тоннах: " + "\n" + String.valueOf(sum)) ;
            myDbManager.CloseDb();
        }
    }

    public void onClickButtonRecalculate(View view){
        myDbManager.openDb();
        myDbManager.DestroyDb();
        myDbManager.CloseDb();
        Intent intent = new Intent(this, Emissions_Communal_Activity.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonMainMenu(View view){
        Intent intent =  new Intent(this, Main_Menu_Activity.class);
        startActivity(intent);
        finish();
    }

    public void onClickButtonBack(View view){
        Intent intent =  new Intent(this, Emissions_Car_Activity.class);
        startActivity(intent);
    }

}