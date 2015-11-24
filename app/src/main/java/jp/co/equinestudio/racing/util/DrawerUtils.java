package jp.co.equinestudio.racing.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.model.DrawerMenuItem;

/**
 * ナビゲーションドロワーに関するUtisクラス
 */
public class DrawerUtils {

    public static List<DrawerMenuItem> getMenuItemList(final Context context) {

        List<DrawerMenuItem> menuItems = new ArrayList<>();

        DrawerMenuItem drawerItemHome = new DrawerMenuItem();
        drawerItemHome.setId(DrawerMenuItem.ID_HOME);
        drawerItemHome.setMenuText(context.getResources().getString(R.string.drawer_menu_home));
        menuItems.add(drawerItemHome);

        DrawerMenuItem drawerItemFavoriteHorse = new DrawerMenuItem();
        drawerItemFavoriteHorse.setId(DrawerMenuItem.ID_FAVORITE_HORSE);
        drawerItemFavoriteHorse.setMenuText(context.getResources().getString(R.string.drawer_menu_favorite_horse));
        menuItems.add(drawerItemFavoriteHorse);

        return menuItems;

    }


}
