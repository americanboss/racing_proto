package net.youbuntan.racing.model;

import android.graphics.Bitmap;

/**
 *
 */
public class DrawerMenuItem {

    public static final int ID_MAP = 1;
    public static final int ID_SEARCH = 2;
    public static final int ID_FAVORITE = 3;
    public static final int ID_EVENT = 4;
    public static final int ID_INFORMATION = 5;
    public static final int ID_ABOUT = 6;


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
