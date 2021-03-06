package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.FragmentTransferListener;
import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.RaceViewerPagerAdapter;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.RaceList;
import jp.co.equinestudio.racing.model.Schedules;
import jp.co.equinestudio.racing.util.StringUtils;

/**
 *
 */
public class RaceViewerFragment extends BaseFragment {

    public static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";
    public static final String KEY_POSITION = "KEY_POSITION";

    private int mIndicatorOffset;
    private HorizontalScrollView mTabScroller;
    private ViewGroup mTab;
    private View mIndicator;
    private ViewPager mRacePager;
    private String mScheduleCode;
    private Spinner mScheduleSelector;
    private List<Schedules.Schedule> mScheduleList;

    private FragmentTransferListener mFragmentTransferListener;

    public static RaceViewerFragment newInstance(final String scheduleCode, final int position ) {
        Bundle args = new Bundle();
        args.putString(KEY_SCHEDULE_CODE, scheduleCode);
        args.putInt(KEY_POSITION, position);

        RaceViewerFragment fragment = new RaceViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getActivity() instanceof FragmentTransferListener) {
            mFragmentTransferListener = (FragmentTransferListener) getActivity();
        } else if (getParentFragment() instanceof FragmentTransferListener) {
            mFragmentTransferListener = (FragmentTransferListener) getParentFragment();
        } else if (getTargetFragment() instanceof FragmentTransferListener) {
            mFragmentTransferListener = (FragmentTransferListener) getTargetFragment();
        }

        View view = inflater.inflate(R.layout.fragment_race_viewer, null);

        Bundle args = getArguments();
        mScheduleCode = args.getString(KEY_SCHEDULE_CODE);
        int startPosition = args.getInt(KEY_POSITION);

        // Viewの取得
        mTabScroller = (HorizontalScrollView) view.findViewById(R.id.tab_scroll);
        mTab = (ViewGroup) view.findViewById(R.id.tab);
        mIndicator = view.findViewById(R.id.indicator);
        mScheduleSelector = (Spinner) view.findViewById(R.id.race_viewer_spinner);

        // 開催一覧をロードする
        mScheduleList = new ArrayList<>();
        String scheduleJson = AssetsLogic.getStringAsset(getActivity(), "schedule/schedule.static.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Schedules>() { }. getType();
        Schedules schedules = gson.fromJson(scheduleJson, listType);
        List<String> scheduleList = new ArrayList<>();
        int schedulePosition = 0;
        int count = 0;
        for (Schedules.ScheduleDate scheduleDate : schedules.getScheduleDates()) {
            for (Schedules.Schedule schedule : scheduleDate.getSchedules()) {
                StringBuilder builder = new StringBuilder();
                builder.append(StringUtils.getRaceCourseText(schedule.getCourse()));
                builder.append(StringUtils.getWeekdayText(schedule.getWeekday()));
                scheduleList.add(builder.toString());
                mScheduleList.add(schedule);
                if (mScheduleCode.equals(schedule.getCode())) {
                    schedulePosition = count;
                }
                count++;

            }
        }
        ArrayAdapter<String> scheduleListAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, scheduleList);
        scheduleListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mScheduleSelector.setAdapter(scheduleListAdapter);
        mScheduleSelector.setOnItemSelectedListener(mScheduleSelectedListener);
        mScheduleSelector.setSelection(schedulePosition);

        //レース一覧をロードする
        String raceJson = AssetsLogic.getStringAsset(getActivity().getApplicationContext(), "race_list/race_list.static."+mScheduleCode+".json");
        listType = new TypeToken<RaceList>() { }. getType();
        RaceList raceList = gson.fromJson(raceJson, listType);

        PagerAdapter adapter = new RaceViewerPagerAdapter(getChildFragmentManager(), raceList);
        mRacePager = (ViewPager) view.findViewById(R.id.pager);
        mRacePager.setAdapter(adapter);
        mRacePager.setOnPageChangeListener(new PageChangeListener());

        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            TextView textView = (TextView) inflater.inflate(R.layout.tab_race_item, mTab, false);
            textView.setText(adapter.getPageTitle(i));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mRacePager.setCurrentItem(position);
                }
            });
            mTab.addView(textView);
        }

        // 選択したレースを表示する
        mRacePager.setCurrentItem(startPosition);


        return view;
    }

    private AdapterView.OnItemSelectedListener mScheduleSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
            if (mScheduleCode.equals(mScheduleList.get(i).getCode())) {
                Log.d("same", "same");
            } else {
                if (mFragmentTransferListener != null) {
                    mFragmentTransferListener.replaceRaceViewerFragment(mScheduleList.get(i).getCode(), 0);
                }
                Log.d("same", "not same");

            }
        }

        @Override
        public void onNothingSelected(final AdapterView<?> adapterView) {

        }
    };

    // ページスクロールのリスナ
    class PageChangeListener implements ViewPager.OnPageChangeListener {
        private int mScrollingState = ViewPager.SCROLL_STATE_IDLE;

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            updateIndicatorPosition(position, positionOffset);
        }

        @Override
        public void onPageSelected(final int position) {
            // スクロール中はonPageScrolled()で描画するのでここではしない
            if (mScrollingState == ViewPager.SCROLL_STATE_IDLE) {
                updateIndicatorPosition(position, mRacePager.getCurrentItem());
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mScrollingState = state;
        }

        private void updateIndicatorPosition(int position, float positionOffset) {
            // 現在のタブ
            final View view = mTab.getChildAt(position);
            // 次のタブ（現在が最終の場合はnull）
            final View viewRight = position == (mTab.getChildCount() - 1) ? null : mTab.getChildAt(position + 1);
            // 現在のタブの左端の座標
            int left = view.getLeft();
            // タブの横幅
            int width = view.getWidth();
            int widthRight = viewRight == null ? width : viewRight.getWidth();
            // インジケータの幅
            int indicatorWidth = (int) (widthRight * positionOffset + width * (1 - positionOffset));

            int indicatorLeft = (int) (left + positionOffset * width);

            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.setMargins(indicatorLeft, 0, 0, 0);
            mIndicator.setLayoutParams(layoutParams);

            mTabScroller.scrollTo(indicatorLeft - mIndicatorOffset, 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRacePager = null;
        mScheduleSelectedListener = null;
        mFragmentTransferListener = null;
        mTab = null;
        mTabScroller = null;
        mScheduleSelector = null;
    }
}
