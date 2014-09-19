package net.youbuntan.racing.activity;

import android.app.Activity;
import android.os.Bundle;
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

import net.youbuntan.racing.R;


public class RaceViewerActivity extends Activity {

    private int mIndicatorOffset;
    private HorizontalScrollView mTabScroller;
    private ViewGroup mTab;
    private View mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_viewer);

        // Viewの取得
        mTabScroller = (HorizontalScrollView) findViewById(R.id.tab_scroll);
        mTab = (ViewGroup) findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);

        PagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
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

    }

    // ページスクロールのリスナ
    class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(final int i, final float v, final int i2) {

        }

        @Override
        public void onPageSelected(final int i) {

        }

        @Override
        public void onPageScrollStateChanged(final int i) {

        }

        private void updateIndicatorPosition(int position, float positionOffset) {
            final View view = mTab.getChildAt(position);
            final View viewRight = position == (mTab.getChildCount() - 1) ? null : mTab.getChildAt(position + 1);

            int left = view.getLeft();

            int width = view.getWidth();
            int width2 = viewRight == null ? width : viewRight.getWidth();

            int indicationWidth = (int) (width2 * positionOffset + width * (1 - positionOffset));

            int indicatorLeft = (int) (left + positionOffset * width);

            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();


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
