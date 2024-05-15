package com.hfad.ecolog.Main_Activity;

import android.content.Intent;
import android.health.connect.datatypes.ExerciseRoute;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.DataBaseUsers.MyDbManagerUsers;
import com.hfad.ecolog.R;

public class StatisticsActivity extends AppCompatActivity {

    TextView textViewCar, textViewCommunal, textViewObshTran, textViewFly, textViewResolve;
    private MyDbManagerUsers myDbManagerUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        textViewCar = findViewById(R.id.textViewCar);
        textViewCommunal = findViewById(R.id.textViewCommunal);
        textViewObshTran = findViewById(R.id.textViewObshTran);
        textViewFly = findViewById(R.id.textViewFly);
        textViewResolve = findViewById(R.id.textViewResolve);

        Intent intent = getIntent();
        String UserId = intent.getStringExtra("UserId");

        myDbManagerUsers = new MyDbManagerUsers(this);

        myDbManagerUsers.OpenDb();
        float ECommunal = myDbManagerUsers.getECommunalForUser(UserId);
        float ECar = myDbManagerUsers.getECarForUser(UserId);
        float EResolve = myDbManagerUsers.getEResolveForUser(UserId);

        myDbManagerUsers.CloseDb();

        textViewResolve.append(Float.toString(EResolve));
        textViewCar.append(Float.toString(ECar));
        textViewCommunal.append(Float.toString(ECommunal));
        textViewObshTran.append("sdfsdf");
        textViewFly.append("SFSDFSDFSDF");
    }
    public void onClickButton(View view){
        Intent intent = new Intent(this, Main_Menu.class);
        startActivity(intent);
        finish();
    }
}