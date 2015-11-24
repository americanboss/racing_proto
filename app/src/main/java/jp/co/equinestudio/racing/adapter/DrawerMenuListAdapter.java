package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.model.DrawerMenuItem;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class DrawerMenuListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<DrawerMenuItem> mItems;


    public DrawerMenuListAdapter(final Context context, final List<DrawerMenuItem> items) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mItems = items;
    }

    public void setItems(List<DrawerMenuItem> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return mItems.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_drawer_menu, null);
        }

        final TextView menuText = (TextView) convertView.findViewById(R.id.eqs_drawer_menu_text);
        menuText.setText(mItems.get(position).getMenuText());

        ImageView menuIcon = (ImageView) convertView.findViewById(R.id.eqs_drawer_menu_icon);
        menuIcon.setImageBitmap(mItems.get(position).getMenuIcon());

        return convertView;
    }
}
