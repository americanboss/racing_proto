package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceList;

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
        TextView tvRaceNum = (TextView) view.findViewById(R.id.text_race_num);
        TextView tvStartTime = (TextView) view.findViewById(R.id.text_start_time);
        TextView tvRaceName = (TextView) view.findViewById(R.id.text_race_name);
        TextView tvTrack = (TextView) view.findViewById(R.id.text_track);
        TextView tvDistance = (TextView) view.findViewById(R.id.text_distance);
        TextView tvEntryNum = (TextView) view.findViewById(R.id.text_entry_num);

        Race race = mRaceList.getRaces().get(position);
        tvRaceNum.setText(race.getRaceNum());
        tvStartTime.setText(race.getStartTime());
        tvTrack.setText(race.getTrack());
        tvDistance.setText(race.getDistance());
        tvEntryNum.setText(race.getEntryNum());

        // レース名が空であれば条件を表示
        String raceName = ("".equals(race.getRaceName10())) ? race.getRaceDivision() :  race.getRaceName10();
        tvRaceName.setText(raceName);

        return view;
    }


}
