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

        int[] drawableB = {R.drawable.sample_b0, R.drawable.sample_b1, R.drawable.sample_b2, R.drawable.sample_b3};
        int[] drawableC = {R.drawable.sample_c0, R.drawable.sample_c1, R.drawable.sample_c2, R.drawable.sample_c3, R.drawable.sample_c4,
                R.drawable.sample_c5, R.drawable.sample_c6, R.drawable.sample_c7, R.drawable.sample_c8};

        mScaleImageView = new ScalableImageView(this);
        mScaleImageView.setTiling(true);
        mScaleImageView.setMaxScale(6);
        mScaleImageView.setDrawableA(R.drawable.sample_a);
        mScaleImageView.setDrawableB(drawableB);
        mScaleImageView.setDrawableC(drawableC);
        frameLayout.addView(mScaleImageView);



}
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
