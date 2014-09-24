package net.youbuntan.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.youbuntan.racing.R;
import net.youbuntan.racing.model.RaceData;
import net.youbuntan.racing.model.RaceMember;

/**
 *
 */
public class RaceMemberListAdapter extends BaseAdapter{

    private RaceData mRaceData;
    private LayoutInflater mInflater;

    public RaceMemberListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceData = new RaceData();
    }

    public RaceMemberListAdapter(Context context, RaceData raceData) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceData = raceData;
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
            view = mInflater.inflate(R.layout.list_race_member, null);

            viewHolder = new ViewHolder();
            viewHolder.mGateNumber = (TextView) view.findViewById(R.id.text_gate_number);
            viewHolder.mHorseName = (TextView) view.findViewById(R.id.text_horse_name);
            viewHolder.mFatherName = (TextView) view.findViewById(R.id.text_father_name);
            viewHolder.mMotherName = (TextView) view.findViewById(R.id.text_mother_name);
            viewHolder.mMothersFatherName = (TextView) view.findViewById(R.id.text_mothers_father_name);
            viewHolder.mGender = (TextView) view.findViewById(R.id.text_gender);
            viewHolder.mAge = (TextView) view.findViewById(R.id.text_age);
            viewHolder.mJockeyName = (TextView) view.findViewById(R.id.text_jockey_name);
            viewHolder.mJockeyMark = (TextView) view.findViewById(R.id.text_jockey_mark);
            viewHolder.mTrainerName = (TextView) view.findViewById(R.id.text_trainer_name);
            viewHolder.mHorseColor = (TextView) view.findViewById(R.id.text_horse_color);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RaceMember member = mRaceData.getRaceMembers().get(position);

        viewHolder.mGateNumber.setText(Integer.toString(member.getGateNumber()));
        viewHolder.mHorseName.setText(member.getHorseName());
        viewHolder.mFatherName.setText(member.getFatherName());
        viewHolder.mMotherName.setText(member.getMotherName());
        viewHolder.mMothersFatherName.setText(member.getMothersFatherName());
        viewHolder.mGender.setText(member.getGender());
        viewHolder.mAge.setText(Integer.toString(member.getAge()));
        viewHolder.mJockeyName.setText(member.getJockeyName());
        viewHolder.mJockeyMark.setText(member.getJockeyMark());
        viewHolder.mTrainerName.setText(member.getTrainerName());
        viewHolder.mHorseColor.setText(member.getHorseColorCode());


        return view;
    }


    class ViewHolder {

        private TextView mGateNumber;
        private TextView mHorseName;
        private TextView mFatherName;
        private TextView mMotherName;
        private TextView mMothersFatherName;
        private TextView mGender;
        private TextView mAge;
        private TextView mJockeyName;
        private TextView mJockeyMark;
        private TextView mTrainerName;
        private TextView mHorseColor;


    }


}
