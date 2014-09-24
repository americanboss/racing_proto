package net.youbuntan.racing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.youbuntan.racing.R;
import net.youbuntan.racing.adapter.RaceViewerPagerAdapter;
import net.youbuntan.racing.logic.AssetsLogic;
import net.youbuntan.racing.model.RaceList;

import java.lang.reflect.Type;


public class RaceViewerActivity extends FragmentActivity {

    public static final String KEY_SCHEDULE_CODE = "KEY_SCHEDULE_CODE";
    public static final String KEY_RACE_POSITION = "KEY_RACE_POSITION";

    private int mIndicatorOffset;
    private HorizontalScrollView mTabScroller;
    private ViewGroup mTab;
    private View mIndicator;
    private String mScheduleCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_viewer);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        mScheduleCode = args.getString(KEY_SCHEDULE_CODE);
        int startPosition = args.getInt(KEY_RACE_POSITION);

        // Viewの取得
        mTabScroller = (HorizontalScrollView) findViewById(R.id.tab_scroll);
        mTab = (ViewGroup) findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);

        //レース一覧をロードする
        String raceJson = AssetsLogic.getStringAsset(RaceViewerActivity.this, "race_list/race_list.static."+mScheduleCode+".json");
        Gson gson = new Gson();
        Type listType = new TypeToken<RaceList>() { }. getType();
        RaceList raceList = gson.fromJson(raceJson, listType);

        PagerAdapter adapter = new RaceViewerPagerAdapter(getSupportFragmentManager(), raceList);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new PageChangeListener());

        LayoutInflater inflater = LayoutInflater.from(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
