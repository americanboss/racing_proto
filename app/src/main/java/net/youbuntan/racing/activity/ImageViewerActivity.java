package net.youbuntan.racing.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.FrameLayout;

import net.youbuntan.racing.R;
import net.youbuntan.racing.util.ScalableImageView;

public class ImageViewerActivity extends FragmentActivity {

    private ScalableImageView mScaleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_image_view);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.viewer);

        mScaleImageView = new ScalableImageView(this);
        mScaleImageView.setMaxScale(4);
        mScaleImageView.setDrawable(R.drawable.sample_1);
        frameLayout.addView(mScaleImageView);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
