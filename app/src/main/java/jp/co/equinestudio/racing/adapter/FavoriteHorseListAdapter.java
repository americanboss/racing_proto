package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.dao.green.FavoriteHorse;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceList;

/**
 *
 */
public class FavoriteHorseListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<FavoriteHorse> mItems;

    public FavoriteHorseListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = new ArrayList<>();
    }

    public void setItems(final List<FavoriteHorse> items) {
        mItems = items;
    }

    @Override
    public int getCount() {

        return mItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(0);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_favorite_horse, null);

        ((TextView) view.findViewById(R.id.text_horse_name)).setText(mItems.get(position).getHorse_name());


        return view;
    }


}
