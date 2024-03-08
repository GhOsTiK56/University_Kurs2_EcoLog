package com.hfad.ecolog.Drawer;

import android.view.View;

public class ImageButtonClickListener implements View.OnClickListener {
    private final Drawer_manager drawerManager;

    public ImageButtonClickListener(Drawer_manager drawerManager) {
        this.drawerManager = drawerManager;
    }

    public void onClick(View view) {
        drawerManager.OpenDrawer();
    }


}
