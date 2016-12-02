package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.ScheduleRaceListener;
import jp.co.equinestudio.racing.adapter.item.HomeListItem;
import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.util.StringUtils;

/**
 *
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter {

    private List<HomeListItem> mItems;
    private Context mContext;
    private LayoutInflater mInflater;
    private ScheduleRaceListener mScheduleRaceListener;

    private List<HomeListItem> mMainRaceItems;
    private List<HomeListItem> mScheduleItems;
    private Map<String, List<HomeListItem>> mScheduleRaceItemMap;
    private Map<String, Boolean> mScheduleRaceVisibilityMap;

    public HomeRecyclerAdapter(final Context context, final ScheduleRaceListener listener) {
        mItems = new ArrayList<>();
        mMainRaceItems = new ArrayList<>();
        mScheduleItems = new ArrayList<>();
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mScheduleRaceItemMap = new HashMap<>();
        mScheduleRaceVisibilityMap = new HashMap<>();
        mScheduleRaceListener = listener;
    }

    public void buildItems() {
        mItems.clear();
        mItems.addAll(mMainRaceItems);
        for (HomeListItem item : mScheduleItems) {
            mItems.add(item);
            if (mScheduleRaceVisibilityMap.get(item.getSchedule().getCode())) {
                mItems.addAll(mScheduleRaceItemMap.get(item.getSchedule().getCode()));
            }
        }
    }

    public void setScheduleItems(final List<HomeListItem> items) {
        mScheduleItems = items;
    }

    public void setMainRaceItems(final List<HomeListItem> items) {
        mMainRaceItems = items;
    }

    public void putScheduleRaceItem(final String scheduleCode, final Race race) {
        HomeListItem item = new HomeListItem();
        item.setTypeId(HomeListItem.TYPE_ID_SCHEDULE_RACE);
        item.setRace(race);
        if (!mScheduleRaceItemMap.containsKey(scheduleCode)) {
            item.setId(0);
            mScheduleRaceItemMap.put(scheduleCode, new ArrayList<HomeListItem>());
        } else {
            item.setId(mScheduleRaceItemMap.get(scheduleCode).size());
        }
        mScheduleRaceItemMap.get(scheduleCode).add(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeId) {
        switch (typeId) {
            case HomeListItem.TYPE_ID_HEADING :
                break;
            case HomeListItem.TYPE_ID_MAIN_RACE :
                return new MainRaceViewHolder(mInflater.inflate(R.layout.recycler_home_main_race_item, parent, false));
            case HomeListItem.TYPE_ID_SCHEDULE :
                return new ScheduleViewHolder(mInflater.inflate(R.layout.recycler_home_schedule_item, parent, false));
            case HomeListItem.TYPE_ID_SCHEDULE_RACE :
                return new ScheduleRaceViewHolder(mInflater.inflate(R.layout.recycler_home_schedule_race_item, parent, false));
            default:
                return null;
        }
        return null;
    }


    @Override
    public int getItemViewType(final int position) {
        return mItems.get(position).getTypeId();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        switch (mItems.get(position).getTypeId()) {
            case HomeListItem.TYPE_ID_HEADING :
                break;
            case HomeListItem.TYPE_ID_MAIN_RACE :
                bindMainRaceView(viewHolder, position);
                break;
            case HomeListItem.TYPE_ID_SCHEDULE :
                bindScheduleView(viewHolder, position);
                break;
            case HomeListItem.TYPE_ID_SCHEDULE_RACE :
                bindScheduleRaceView(viewHolder, position);
                break;

            default:
                break;
        }
    }

    class HeadingViewHolder extends RecyclerView.ViewHolder {

        HeadingViewHolder(final View itemView) {
            super(itemView);
        }
    }

    /**
     * メインレース
     */
    class MainRaceViewHolder extends RecyclerView.ViewHolder {

        private CardView mListItemBlock;
        private TextView mDate;
        private TextView mWeekday;
        private TextView mRaceCourse;
        private TextView mRaceName;

        MainRaceViewHolder(final View itemView) {
            super(itemView);
            mListItemBlock = (CardView) itemView.findViewById(R.id.list_item_block);
            mDate = (TextView) itemView.findViewById(R.id.date);
            mWeekday = (TextView) itemView.findViewById(R.id.weekday);
            mRaceCourse = (TextView) itemView.findViewById(R.id.race_course);
            mRaceName = (TextView) itemView.findViewById(R.id.race_name);
        }
    }

    private void bindMainRaceView(final RecyclerView.ViewHolder viewHolder, final int position) {
        MainRaceViewHolder holder = (MainRaceViewHolder) viewHolder;
        holder.mDate.setText(StringUtils.getMonthDayText(mItems.get(position).getRace().getMonthDay()));
        holder.mWeekday.setText(StringUtils.getWeekdayText(mItems.get(position).getRace().getWeekDayCode()));
        holder.mRaceCourse.setText(StringUtils.getRaceCourseText(mItems.get(position).getRace().getCourse()));
        holder.mRaceName.setText(mItems.get(position).getRace().getRaceName10());
        holder.mListItemBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            }
        });
    }

    /**
     * スケジュール
     */
    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private TextView mCourseName;
        private TextView mMonth;
        private TextView mDay;
        private TextView mWeekday;

        ScheduleViewHolder(final View itemView) {
            super(itemView);
            mContainer = (LinearLayout) itemView.findViewById(R.id.view_container);
            mCourseName = (TextView) itemView.findViewById(R.id.text_course_name);
            mMonth = (TextView) itemView.findViewById(R.id.text_month);
            mDay = (TextView) itemView.findViewById(R.id.text_day);
            mWeekday = (TextView) itemView.findViewById(R.id.text_weekday);
        }
    }

    private void bindScheduleView(final RecyclerView.ViewHolder viewHolder, final int position) {
        String[] convertedMonthDay = StringUtils.convertMonthDayText(mItems.get(position).getSchedule().getMonthDay());

        ScheduleViewHolder holder = (ScheduleViewHolder) viewHolder;
        holder.mCourseName.setText(StringUtils.getRaceCourseText(mItems.get(position).getSchedule().getCourse()));
        holder.mMonth.setText(convertedMonthDay[StringUtils.MONTH_DAY_UTIL_MONTH]);
        holder.mDay.setText(convertedMonthDay[StringUtils.MONTH_DAY_UTIL_DAY]);
        holder.mWeekday.setText(StringUtils.getWeekdayText(mItems.get(position).getSchedule().getWeekday()));

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mScheduleRaceListener.onScheduleSelected(mItems.get(position).getSchedule().getCode());
            }
        });
    }

    /**
     * 個別レース
     */
    class ScheduleRaceViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private TextView mRaceNum;
        private TextView mStartTime;
        private TextView mRaceName;
        private TextView mTrack;
        private TextView mDistance;
        private TextView mEntryNum;

        ScheduleRaceViewHolder(final View itemView) {
            super(itemView);
            mContainer = (LinearLayout) itemView.findViewById(R.id.view_container);
            mRaceNum = (TextView) itemView.findViewById(R.id.text_race_num);
            mStartTime = (TextView) itemView.findViewById(R.id.text_start_time);
            mRaceName = (TextView) itemView.findViewById(R.id.text_race_name);
            mTrack = (TextView) itemView.findViewById(R.id.text_track);
            mDistance = (TextView) itemView.findViewById(R.id.text_distance);
            mEntryNum = (TextView) itemView.findViewById(R.id.text_entry_num);
        }
    }

    private void bindScheduleRaceView(final RecyclerView.ViewHolder viewHolder, final int position) {
        ScheduleRaceViewHolder holder = (ScheduleRaceViewHolder) viewHolder;
        final Race race = mItems.get(position).getRace();
        holder.mRaceNum.setText(race.getRaceNum());
        holder.mStartTime.setText(race.getStartTime());
        holder.mTrack.setText(race.getTrack());
        holder.mDistance.setText(race.getDistance());
        holder.mEntryNum.setText(race.getEntryNum());

        // レース名が空であれば条件を表示
        String raceName = ("".equals(race.getRaceName10())) ? race.getRaceDivision() :  race.getRaceName10();
        holder.mRaceName.setText(raceName);

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mScheduleRaceListener.onRaceSelected(mItems.get(position).getRace().getScheduleCode(), mItems.get(position).getId());
            }
        });
    }

    public void setScheduleVisibility(final String scheduleCode, final boolean visibility) {
        mScheduleRaceVisibilityMap.put(scheduleCode, visibility);
    }

    public boolean getScheduleVisibility(final String scheduleCode) {
        return mScheduleRaceVisibilityMap.get(scheduleCode);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
