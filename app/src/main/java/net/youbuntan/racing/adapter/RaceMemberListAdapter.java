package net.youbuntan.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.youbuntan.racing.Manager.BracketColorManager;
import net.youbuntan.racing.R;
import net.youbuntan.racing.model.RaceData;
import net.youbuntan.racing.model.RaceMember;

/**
 * 出馬表のListAdapter
 */
public class RaceMemberListAdapter extends BaseAdapter{

    private RaceData mRaceData;
    private LayoutInflater mInflater;
    private Context mContext;

    public RaceMemberListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRaceData = new RaceData();
        mContext = context;
    }

    public RaceMemberListAdapter(Context context, RaceData raceData) {
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
            view = mInflater.inflate(R.layout.list_race_member, null);

            viewHolder = new ViewHolder();
            viewHolder.mContainerBracketNumber = (LinearLayout) view.findViewById(R.id.container_bracket_number);
            viewHolder.mBracketNumber = (TextView) view.findViewById(R.id.text_bracket_number);
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
            viewHolder.mHorseWeight = (TextView) view.findViewById(R.id.text_horse_weight);
            viewHolder.mHorseWeightDiff = (TextView) view.findViewById(R.id.text_horse_weight_diff);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RaceMember member = mRaceData.getRaceMembers().get(position);

        viewHolder.mBracketNumber.setText("(" + member.getBracketNumber() + ")");
        viewHolder.mGateNumber.setText(Integer.toString(member.getGateNumber()));
        viewHolder.mHorseName.setText(member.getHorseName());
        viewHolder.mFatherName.setText(member.getFatherName());
        viewHolder.mMotherName.setText(member.getMotherName());
        viewHolder.mMothersFatherName.setText("(" + member.getMothersFatherName() + ")");
        viewHolder.mGender.setText(member.getGender());
        viewHolder.mAge.setText(Integer.toString(member.getAge()));
        viewHolder.mJockeyName.setText(member.getJockeyName());
        viewHolder.mJockeyMark.setText(member.getJockeyMark());
        viewHolder.mTrainerName.setText(member.getTrainerName());
        viewHolder.mHorseColor.setText(member.getHorseColorCode());
        viewHolder.mHorseWeight.setText(member.getHorseWeight());

        // 枠番の色設定
        BracketColorManager budgetColorManager = new BracketColorManager();
        budgetColorManager.setBracketNumber(member.getBracketNumber());
        viewHolder.mContainerBracketNumber.setBackgroundColor(budgetColorManager.getBackgroundColorResourceId(mContext));
        viewHolder.mGateNumber.setTextColor(budgetColorManager.getTextColorResourceId(mContext));
        viewHolder.mBracketNumber.setTextColor(budgetColorManager.getTextColorResourceId(mContext));

        return view;
    }


    class ViewHolder {

        private LinearLayout mContainerBracketNumber; // 枠番のコンテナ

        private TextView mBracketNumber;        // 枠番
        private TextView mGateNumber;           // 馬番
        private TextView mHorseName;            // 馬名
        private TextView mFatherName;           // 父馬名
        private TextView mMotherName;           // 母馬名
        private TextView mMothersFatherName;    // 母父馬名
        private TextView mGender;               // 性別
        private TextView mAge;                  // 馬齢
        private TextView mJockeyName;           // 騎手名
        private TextView mJockeyMark;           // 見習い騎手記号
        private TextView mTrainerName;          // 調教師名
        private TextView mHorseColor;           // 毛色コード
        private TextView mHorseWeight;          // 馬体重
        private TextView mHorseWeightDiff;      // 馬体重増減


    }


}
