package net.youbuntan.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.jna.IntegerType;

import net.youbuntan.racing.Manager.BracketColorManager;
import net.youbuntan.racing.R;
import net.youbuntan.racing.model.RaceData;
import net.youbuntan.racing.model.RaceMember;
import net.youbuntan.racing.util.StringUtils;

/**
 * レース結果のListAdapter
 */
public class RaceResultListAdapter extends BaseAdapter{

    private RaceData mRaceData;
    private LayoutInflater mInflater;
    private Context mContext;

    public RaceResultListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceData = new RaceData();
        mContext = context;
    }

    public RaceResultListAdapter(Context context, RaceData raceData) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceData = raceData;
        mContext = context;
    }

    @Override
    public int getCount() {

        return mRaceData.getRaceMembers().size();
    }

    @Override
    public Object getItem(final int position) {
        return mRaceData.getRaceMembers().get(position);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_race_result, null);

            viewHolder = new ViewHolder();
            viewHolder.mResult = (TextView) view.findViewById(R.id.text_result);
            viewHolder.mGateNumber = (TextView) view.findViewById(R.id.text_gate_number);
            viewHolder.mHorseName = (TextView) view.findViewById(R.id.text_horse_name);
            viewHolder.mHorseDistance = (TextView) view.findViewById(R.id.text_horse_distance);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RaceMember member = mRaceData.getRaceMembers().get(position);

        viewHolder.mResult.setText(StringUtils.getResultByRaceMember(mContext, member));

        viewHolder.mGateNumber.setText(Integer.toString(member.getGateNumber()));
        // 馬番に枠番を着色する
        BracketColorManager bracketColorManager = new BracketColorManager();
        bracketColorManager.setBracketNumber(member.getBracketNumber());
        viewHolder.mGateNumber.setBackgroundColor(bracketColorManager.getBackgroundColorResourceId(mContext));
        viewHolder.mGateNumber.setTextColor(bracketColorManager.getTextColorResourceId(mContext));

        viewHolder.mHorseName.setText(member.getHorseName());
        viewHolder.mHorseDistance.setText(member.getHorseDistance());


        return view;
    }


    class ViewHolder {

        private TextView mResult;           // 着順
        private TextView mGateNumber;       // 馬番
        private TextView mHorseName;        // 馬名
        private TextView mHorseDistance;    // 着差

    }


}
