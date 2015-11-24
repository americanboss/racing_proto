package jp.co.equinestudio.racing.model;

import android.graphics.Bitmap;

/**
 *
 */
public class DrawerMenuItem {

    public static final int ID_HOME = 1;
    public static final int ID_RACE_LIST = 2;
    public static final int ID_FAVORITE_HORSE = 3;


    private int mId;
    private String mMenuText;
    private Bitmap mMenuIcon;

    public int getId() {
        return mId;
    }

    public void setId(final int id) {
        mId = id;
    }

    public String getMenuText() {
        return mMenuText;
    }

    public void setMenuText(final String menuText) {
        mMenuText = menuText;
    }

    public Bitmap getMenuIcon() {
        return mMenuIcon;
    }

    public void setMenuIcon(final Bitmap menuIcon) {
        mMenuIcon = menuIcon;
    }
}
