/*
Класс Navigation_Item_Click_Listener обрабатывает нажатием на пункты выдвижного меню.
В этом классе реализован 1 метод:
onNavigationItemSelected - Получает ссылку на нажимаемый объект, затем с помощью intent переходит на нужное activity.
*/
package com.hfad.ecolog.Drawer;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.hfad.ecolog.Emissions.Emissions_Communal;
import com.hfad.ecolog.R;

public class Navigation_Item_Click_Listener implements NavigationView.OnNavigationItemSelectedListener {
    private final Context context;
    private final String UserId;
    private final Drawer_Manager drawerManager;
    public Navigation_Item_Click_Listener(Context context, String UserId, DrawerLayout drawerLayout){
        this.context = context;
        this.UserId = UserId;
        this.drawerManager = new Drawer_Manager(drawerLayout);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();


        if (itemId == R.id.navCalculateEmission){
            Toast.makeText(context, "Calculation Emission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Emissions_Communal.class);
            intent.putExtra("UserId", UserId);
            drawerManager.CloseDrawer();
            startActivity(context, intent, new Bundle());
        }

        if (itemId == R.id.navStatistics){
            Toast.makeText(context, "Statistics", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Emissions_Communal.class);
            intent.putExtra("UserId", UserId);
            drawerManager.CloseDrawer();
        }

        if (itemId == R.id.navShared){
            Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Emissions_Communal.class);
            intent.putExtra("UserId", UserId);
            drawerManager.CloseDrawer();
        }

        if (itemId == R.id.navRecommendations){
            Toast.makeText(context, "Recommendations", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Emissions_Communal.class);
            intent.putExtra("UserId", UserId);
            drawerManager.CloseDrawer();
        }

        if (itemId == R.id.navSettings){
            Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Emissions_Communal.class);
            intent.putExtra("UserId", UserId);
            drawerManager.CloseDrawer();
        }

        return false;
    }
}
