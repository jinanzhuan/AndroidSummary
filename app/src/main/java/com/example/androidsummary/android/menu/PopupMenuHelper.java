package com.example.androidsummary.android.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import com.example.androidsummary.R;
import com.example.androidsummary.common.CommonUtils;

import java.lang.reflect.Field;

public class PopupMenuHelper {

    @SuppressLint("RestrictedApi")
    public void showMenu(Context context, View view, int x, int y) {
        PopupMenu pMenu = new PopupMenu(context, view);
        Menu menu = pMenu.getMenu();
//        pMenu.getMenuInflater().inflate(R.menu.action_menu, pMenu.getMenu());
//        pMenu.getMenuInflater().inflate(R.menu.action_menu2, pMenu.getMenu());
        menu.add(Menu.NONE, 0, 0, "增加好友");
        menu.add(Menu.NONE, 1, 1, "删除");
        menu.add(Menu.NONE, 2, 2, "递增");

        menu.findItem(1).setVisible(false);

        try {
            Field field = pMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(pMenu);
            helper.show(x, 0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

