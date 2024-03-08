/*
Класс Drawer_Manager управляет открытием и закрытием бокового выдвижного меню drawer.
В этом классе реализованы 2 метода:
OpenDrawer - Отвечает за открытие бокового меню.
CloseDrawer - Отвечает за закрытие бокового меню.
*/
package com.hfad.ecolog.Drawer;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Drawer_Manager {

    private final DrawerLayout drawerLayout; //Хранит ссылку на экземпляр DrawerLayout с которым будет работать Drawer_Manager

    public Drawer_Manager(DrawerLayout drawerLayout){ //Конструктор, который принимает экземпляр DrawerLayout и сохраняет его в приватном поле drawerLayout
        this.drawerLayout = drawerLayout;
    }

    public void OpenDrawer(){ //Отвечает за открытие бокового меню
        drawerLayout.openDrawer(GravityCompat.START); // Константа, представляет начальное положение для размещения элементов UI
    }

    public void CloseDrawer(){ //Отвечает за закрытие бокового меню
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
