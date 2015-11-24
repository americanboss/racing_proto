package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.RaceViewerPagerAdapter;
import jp.co.equinestudio.racing.logic.AssetsLogic;
import jp.co.equinestudio.racing.model.RaceList;
import jp.co.equinestudio.racing.util.StringUtils;

/**
 *
 */
public class RaceViewerFragment extends Fragment {

    public static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";
    public static final String KEY_POSITION = "KEY_POSITION";

    private int mIndicatorOffset;
    private HorizontalScrollView mTabScroller;
    private ViewGroup mTab;
    private View mIndicator;
    private String mScheduleCode;
    private TextView mHeading;

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
        View view = inflater.inflate(R.layout.fragment_race_viewer, null);

        Bundle args = getArguments();
        mScheduleCode = args.getString(KEY_SCHEDULE_CODE);
        int startPosition = args.getInt(KEY_POSITION);

        // Viewの取得
        mTabScroller = (HorizontalScrollView) view.findViewById(R.id.tab_scroll);
        mTab = (ViewGroup) view.findViewById(R.id.tab);
        mIndicator = view.findViewById(R.id.indicator);
        mHeading = (TextView) view.findViewById(R.id.race_viewer_heading);

        //レース一覧をロードする
        String raceJson = AssetsLogic.getStringAsset(getActivity().getApplicationContext(), "race_list/race_list.static."+mScheduleCode+".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceList>() { }. getType();
        RaceList raceList = gson.fromJson(raceJson, listType);

        // TODO 開催をクリックするとリストを表示してジャンプできるようにする
        StringUtils.getRaceCourseText(raceList.getCourse());

        // TODO スケジュールコードから曜日は割り出せないので、レースリストに入れる
        StringUtils.getWeekdayText(raceList.getWeekday());

        PagerAdapter adapter = new RaceViewerPagerAdapter(getActivity().getSupportFragmentManager(), raceList);
        final ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new PageChangeListener());

        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            TextView textView = (TextView) inflater.inflate(R.layout.tab_race_item, mTab, false);
            textView.setText(adapter.getPageTitle(i));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    pager.setCurrentItem(position);
                }
            });
            mTab.addView(textView);
        }

        // 選択したレースを表示する
        pager.setCurrentItem(startPosition);


        return view;
    }

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
                updateIndicatorPosition(position, 0);
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
}
