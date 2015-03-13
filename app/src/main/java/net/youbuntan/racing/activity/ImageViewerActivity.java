package net.youbuntan.racing.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import net.youbuntan.racing.R;

import jp.co.equine.scalableimage.ScalableImageView;
import jp.co.equine.scalableimage.ExpandableImageViewHolder;

import java.util.ArrayList;

public class ImageViewerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_image_view);

        int[] drawableB = {R.drawable.sample_b0, R.drawable.sample_b1, R.drawable.sample_b2, R.drawable.sample_b3};
        int[] drawableC = {R.drawable.sample_c0, R.drawable.sample_c1, R.drawable.sample_c2, R.drawable.sample_c3, R.drawable.sample_c4,
                R.drawable.sample_c5, R.drawable.sample_c6, R.drawable.sample_c7, R.drawable.sample_c8};

        ExpandableImageViewHolder viewer = (ExpandableImageViewHolder) findViewById(R.id.viewer);

        ExpandableImageViewHolder.ImageResource resource0 = ExpandableImageViewHolder.makeImageResource(R.drawable.sample_c4, null, null, false);
        ExpandableImageViewHolder.ImageResource resource1 = ExpandableImageViewHolder.makeImageResource(R.drawable.sample_c2, null, null, false);
        ExpandableImageViewHolder.ImageResource resource2 = ExpandableImageViewHolder.makeImageResource(R.drawable.sample_c5, null, null, false);

        ExpandableImageViewHolder.ImageResource resource3 = ExpandableImageViewHolder.makeImageResource(R.drawable.sample_a, drawableB, drawableC, true);
        ExpandableImageViewHolder.Adapter adapter = new ExpandableImageViewHolder.Adapter();
        ArrayList<ExpandableImageViewHolder.ImageResource> items = new ArrayList<ExpandableImageViewHolder.ImageResource>();
        items.add(resource0);
        items.add(resource1);
        items.add(resource2);
        items.add(resource3);
        adapter.setItems(items);
        viewer.setAdapter(adapter);

        viewer.setNextButtonDrawable(android.R.drawable.ic_media_next);
        viewer.setPrevButtonDrawable(android.R.drawable.ic_media_pause);
        viewer.setImageDefaultHeight(700);
        viewer.setMaxScale(6);

}
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
