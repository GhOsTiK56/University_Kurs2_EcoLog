package com.hfad.ecolog.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.R;

public class Splash_Screen_Activity extends AppCompatActivity {

    private long ms = 0;
    private long splashTime = 2000;
    private boolean splashActive = true;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        Thread thread = new Thread() { // Создается поток выполнения, для выболнения ассинхронной работы, чтобы не блочить основной поток, здесь ожидание времени "ожидания" заставки
            public void run(){
                try { // Если здесь возникает исключение, то оно просто игнорируестя
                    while (splashActive && ms < splashTime){
                        if (!paused)
                            ms = ms+100;
                        sleep(100); // Вот тут может быть исключение
                    }
                } catch (Exception e){} // Здесь происходит обработка исключений, в данном случае она пуста, чтобы просто ингорить исключение
                finally {
                    Intent intent = new Intent(Splash_Screen_Activity.this, Main_Menu_Activity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}