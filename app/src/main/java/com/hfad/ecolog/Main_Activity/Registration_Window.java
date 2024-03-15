/*
Класс Registration_Window отвечает за регистрацию пользователя и добавление его в базу данных.
В классе реализованно 3 методов:

*/
package com.hfad.ecolog.Main_Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.hfad.ecolog.DataBase.MyDbManagerUsers;
import com.hfad.ecolog.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registration_Window extends AppCompatActivity {
    Button ButtonEnter, ButtonRegistration;
    MyDbManagerUsers myDbManagerUsers;
    ConstraintLayout root;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_window);

        ButtonEnter = findViewById(R.id.ButtonEnter);
        ButtonRegistration = findViewById(R.id.ButtonRegistration);
        root = findViewById(R.id.root_element);

        myDbManagerUsers = new MyDbManagerUsers(this);

        ButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
        ButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });

        InitAuthentication();
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти"); //Заголовок окна
        dialog.setMessage("Введите все данные для входа"); //Небольшая подпись под заголовком

        LayoutInflater inflater = LayoutInflater.from(this); //
        View show_sign_in_window = inflater.inflate(R.layout.show_sign_in_window, null);
        dialog.setView(show_sign_in_window);

        final TextInputEditText email = show_sign_in_window.findViewById(R.id.EmailField);
        final TextInputEditText password = show_sign_in_window.findViewById(R.id.PasswordField);

        //Проверка наличия сохраненных данных в SharedPrefereces
        String savedEmail = preferences.getString("Email", "");
        String savedPassword = preferences.getString("Password", "");

        if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)){
            email.setText(savedEmail);
            password.setText(savedPassword);
        }

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

                myDbManagerUsers.OpenDb();
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();
                String userId = myDbManagerUsers.getUserIdForEmail(inputEmail);

                if (TextUtils.isEmpty(userId)){
                    // Пользователь с указанным email не найден или пароль отсутствует
                    Toast.makeText(root.getContext(), "Пользователь с таким Email не найден", Toast.LENGTH_SHORT).show();
                }
                else {
                    String hashedPasswordFromDatabase = myDbManagerUsers.getPasswordForUserId(userId);
                    try {
                        if (CheckPasswordHashing(inputPassword, hashedPasswordFromDatabase)) {
                            // Пароли совпадают
                            Toast.makeText(root.getContext(), "Вы успешно авторизовались", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration_Window.this, Main_Menu.class);
                            intent.putExtra("UserId", userId); // Передаем _id пользователя
                            startActivity(intent);
                            finish();

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Email", email.getText().toString());
                            editor.putString("Password", password.getText().toString());
                            editor.apply();

                        } else {
                            // Пароли не совпадают
                            Toast.makeText(root.getContext(), "Неправильный пароль", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
                myDbManagerUsers.CloseDb();
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

                myDbManagerUsers.OpenDb();
                if(myDbManagerUsers.checkUserRegistrationExists(email.getText().toString())){
                    Toast.makeText(root.getContext(), "Пользователь с таким Email уже зарегистрирован", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Если все подходит, то регистрируем пользователя в базу
                    try{
                        String hashedPassword =  PasswordHashing(password.getText().toString());
                        myDbManagerUsers.insertToDbUsers(email.getText().toString(), hashedPassword, name.getText().toString(), phone.getText().toString());
                        Toast.makeText(root.getContext(), "Пользователь успешно зарегистрирован", Toast.LENGTH_SHORT).show();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        Toast.makeText(root.getContext(), "Ошибка при хешировании", Toast.LENGTH_SHORT).show();
                    }

                }
                myDbManagerUsers.CloseDb();
            }

        });
    dialog.show();
    }

    private String PasswordHashing(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return android.util.Base64.encodeToString(hash, android.util.Base64.DEFAULT);
    }

    private boolean CheckPasswordHashing(String userInputPassword, String hashedPasswordFromDatabase) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] userInputHash = md.digest(userInputPassword.getBytes());
        String userInputHashBase64 = android.util.Base64.encodeToString(userInputHash, android.util.Base64.DEFAULT);
        return userInputHashBase64.equals(hashedPasswordFromDatabase);
    }

    private void InitAuthentication(){
        preferences = getSharedPreferences("LogAndPass", MODE_PRIVATE);
    }
}