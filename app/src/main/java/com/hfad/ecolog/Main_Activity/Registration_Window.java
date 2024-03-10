/*
Класс Registration_Window отвечает за регистрацию пользователя и добавление его в базу данных.
В классе реализованно 3 методов:

*/
package com.hfad.ecolog.Main_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.hfad.ecolog.DataBase.MyDbManager;
import com.hfad.ecolog.R;

public class Registration_Window extends AppCompatActivity {
    Button ButtonEnter, ButtonRegistration;
    MyDbManager myDbManager;
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_window);
        ButtonEnter = findViewById(R.id.ButtonEnter);
        ButtonRegistration = findViewById(R.id.ButtonRegistration);
        root = findViewById(R.id.root_element);

        myDbManager = new MyDbManager(this);

        ButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        ButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSighInWindow();
            }
        });
    }

    private void showSighInWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти"); //Заголовок окна
        dialog.setMessage("Введите все данные для входа"); //Небольшая подпись под заголовком

        LayoutInflater inflater = LayoutInflater.from(this); //
        View show_sign_in_window = inflater.inflate(R.layout.show_sign_in_window, null);
        dialog.setView(show_sign_in_window);

        final TextInputEditText email = show_sign_in_window.findViewById(R.id.EmailField);
        final TextInputEditText password = show_sign_in_window.findViewById(R.id.PasswordField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваш Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваш пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                myDbManager.OpenDb();

                if(myDbManager.checkUserSignInExists(email.getText().toString(), password.getText().toString())){
                    Toast.makeText(root.getContext(), "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration_Window.this, Main_Menu.class);
                    intent.putExtra("Email", email.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(root.getContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                }

                myDbManager.CloseDb();
            }
        });
        dialog.show();

    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться"); //Заголовок окна
        dialog.setMessage("Введите все данные для регистрации"); //Небольшая подпись под заголовком

        LayoutInflater inflater = LayoutInflater.from(this); //
        View show_registration_window = inflater.inflate(R.layout.show_registration_window, null);
        dialog.setView(show_registration_window);

        final TextInputEditText email = show_registration_window.findViewById(R.id.EmailField);
        final TextInputEditText password = show_registration_window.findViewById(R.id.PasswordField);
        final TextInputEditText name = show_registration_window.findViewById(R.id.NameField);
        final TextInputEditText phone = show_registration_window.findViewById(R.id.PhoneField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваш Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваш пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 5){
                    Toast.makeText(root.getContext(), "Пароль должен быть больше 5 символов", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваше имя", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(root.getContext(), "Введите ваш телефон", Toast.LENGTH_SHORT).show();
                    return;
                }

                myDbManager.OpenDb();
                if(myDbManager.checkUserRegistrationExists(email.getText().toString())){
                    Toast.makeText(root.getContext(), "Пользователь с таким Email уже зарегистрирован", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Если все подходит, то регистрируем пользователя в базу
                    myDbManager.insertToDbUsers(email.getText().toString(), password.getText().toString(), name.getText().toString(), phone.getText().toString());
                    Toast.makeText(root.getContext(), "Пользователь успешно зарегистрирован", Toast.LENGTH_SHORT).show();
                }
                myDbManager.CloseDb();
            }
        });
    dialog.show();
    }


}