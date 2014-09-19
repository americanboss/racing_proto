package net.youbuntan.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.youbuntan.racing.R;
import net.youbuntan.racing.model.Race;
import net.youbuntan.racing.model.RaceList;

/**
 *
 */
public class RaceListAdapter extends BaseAdapter{

    private RaceList mRaceList;
    private LayoutInflater mInflater;

    public RaceListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceList = new RaceList();
    }

    public RaceListAdapter(Context context, RaceList raceList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceList = raceList;
    }

    @Override
    public int getCount() {

        return mRaceList.getRaces().size();
    }

    @Override
    public Object getItem(final int position) {
        return mRaceList.getRaces().get(0);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_race, null);
        TextView tvRaceNum = (TextView) view.findViewById(R.id.race_num);
        TextView tvRaceName10 = (TextView) view.findViewById(R.id.race_name_10);

        Race race = mRaceList.getRaces().get(position);
        tvRaceNum.setText(race.getRaceNum());
        tvRaceName10.setText(race.getRaceName10());


        return view;
    }


}
