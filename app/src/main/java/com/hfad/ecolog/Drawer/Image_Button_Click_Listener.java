/*
Класс Image_Button_Click_Listener обрабатывает нажатия на кнопку выдвижения меню.
В этом классе реализован 1 метод:
onClick - Открывает меню.
*/
package com.hfad.ecolog.Drawer;

import android.view.View;

public class Image_Button_Click_Listener implements View.OnClickListener {
    private final Drawer_Manager drawerManager;

    public Image_Button_Click_Listener(Drawer_Manager drawerManager) {
        this.drawerManager = drawerManager;
    }

    public void onClick(View view) {
        drawerManager.OpenDrawer();
    }
}
