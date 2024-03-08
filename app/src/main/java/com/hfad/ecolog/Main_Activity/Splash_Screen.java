package com.hfad.ecolog.Main_Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.ecolog.R;

public class Splash_Screen extends AppCompatActivity {

    private long ms = 0;
    private final long splashTime = 2000;
    private final boolean splashActive = true;
    private final boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread thread = new Thread() { // Создается поток выполнения, для выболнения ассинхронной работы, чтобы не блочить основной поток, здесь ожидание времени "ожидания" заставки
            public void run(){
                try { // Если здесь возникает исключение, то оно просто игнорируестя
                    while (splashActive && ms < splashTime){
                        if (!paused)
                            ms = ms+100;
                        sleep(100); // Вот тут может быть исключение
                    }
                } catch (Exception ignored){} // Здесь происходит обработка исключений, в данном случае она пуста, чтобы просто ингорить исключение
                finally {
                    Intent intent = new Intent(Splash_Screen.this, Main_Menu.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}