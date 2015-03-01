package net.youbuntan.racing.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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


public class ImageViewerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.viewer);

        frameLayout.addView(new ScaleImageView(this));


    }

    class ScaleImageView extends View {

        private Bitmap mBitmap;

        public ScaleImageView(Context context) {
            super(context);

            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

}
