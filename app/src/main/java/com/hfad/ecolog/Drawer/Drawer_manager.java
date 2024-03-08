package com.hfad.ecolog.Drawer;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Drawer_manager {

    private final DrawerLayout drawerLayout;

    public Drawer_manager(DrawerLayout drawerLayout){
        this.drawerLayout = drawerLayout;
    }

    public void OpenDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void CloseDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }



}
