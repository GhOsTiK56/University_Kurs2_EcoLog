package com.hfad.ecolog.Emissions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.R;

public class Emissions_Car extends AppCompatActivity {
    private EditText Kilometers, Days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emissions_car);

        Kilometers = findViewById(R.id.Kilometrs);
        Days = findViewById(R.id.Days);
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