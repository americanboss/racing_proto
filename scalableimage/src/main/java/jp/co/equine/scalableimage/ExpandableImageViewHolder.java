package jp.co.equine.scalableimage;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 伸縮可能な画像のホルダー

 */
public class ExpandableImageViewHolder extends ScrollView implements View.OnTouchListener, ScalableImageView.OnImageTouchListener, ScalableImageView.OnDrawListener {

    private LinearLayout mContainer;

    private FrameLayout mImageContainer;
    private RelativeLayout mImageController;

    private LinearLayout mContentsContainer;


    private Adapter mAdapter;

    /** 画像の表示アニメーションを定義 */
    private Timer mTimer;
    private int mCount;
    private static final int COUNT_DOWN = 60;
    private int mImageDefaultHeight = 500;

    // 最大化表示時基本スケール表示モードの前回タッチY座標
    private float mMaxViewBaseScaleModeTouchLastY = -1;

    private boolean mImageMinMode = false;
    private boolean mImageTouchMode = false;
    private ScalableImageView mScalableImageView;

    private int mPosition = 0;

    private ImageButton mNextButton;
    private ImageButton mPrevButton;


    /**
     * コンストラクタ
     * @param context Context.
     */
    public ExpandableImageViewHolder(final Context context) {
        super(context);
        initialize();
    }
    public ExpandableImageViewHolder(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }
    public ExpandableImageViewHolder(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * 初期化処理を行う
     */
    private void initialize() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mContainer = (LinearLayout) inflater.inflate(R.layout.image_view_holder, null);

        mImageContainer = (FrameLayout) mContainer.findViewById(R.id.image_container);
        mImageContainer.setBackgroundColor(Color.BLACK);
        resetImageContainerHeight();

        mScalableImageView = (ScalableImageView) mContainer.findViewById(R.id.scalable_image);
        mScalableImageView.setOnClickListener(new ScalableImageView.OnClickListener() {
            @Override
            public void onClick() {
                if (getImageContainerHeight() == mImageDefaultHeight) {
                    startImageMax();
                } else if (getImageContainerHeight() == getHeight()) {
                    startImageMin();
                }
            }
        });
        mScalableImageView.setOnImageTouchListener(this);
        mScalableImageView.setOnDrawListener(this);

        mNextButton = (ImageButton) mContainer.findViewById(R.id.scalable_image_next);
        mNextButton.setOnClickListener(mPagerButtonClickListener);

        mPrevButton = (ImageButton) mContainer.findViewById(R.id.scalable_image_prev);
        mPrevButton.setOnClickListener(mPagerButtonClickListener);

        mContentsContainer = (LinearLayout) mContainer.findViewById(R.id.content_container);
        mContentsContainer.setBackgroundColor(Color.BLUE);

        mImageController = (RelativeLayout) mContainer.findViewById(R.id.scalable_image_controller);

        this.addView(mContainer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        this.setOnTouchListener(this);

        mTimer = new Timer(false);
    }

    /**
     * ページャーボタンのクリック動作を定義
     */
    private OnClickListener mPagerButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            final int fadeInX;
            final int fadeOutX;
            if (v == mNextButton) {
                // 次へボタンをクリックした際の挙動
                if (!mAdapter.hasNext(mPosition)) {
                    return;
                }
                mPosition++;
                fadeInX = getWidth();
                fadeOutX = (-getWidth());

            } else {
                // 前へボタンをクリックした際の挙動
                if (!mAdapter.hasPrevious(mPosition)) {
                    return;
                }
                mPosition--;
                fadeInX = (-getWidth());
                fadeOutX = getWidth();
            }

            /** 画像に適用するアニメーションを定義 */
            final TranslateAnimation fadeInAnimation = new TranslateAnimation(fadeInX, 0, 0, 0);
            TranslateAnimation fadeOutAnimation = new TranslateAnimation(0, fadeOutX, 0, 0);
            fadeInAnimation.setDuration(200);
            fadeOutAnimation.setDuration(200);
            fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(final Animation animation) {
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    mScalableImageView.setDrawableA(mAdapter.getItem(mPosition).mResourceA);
                    mScalableImageView.setTiling(mAdapter.getItem(mPosition).mTiling);
                    if (mScalableImageView.getTiling()) {
                        mScalableImageView.setDrawableB(mAdapter.getItem(mPosition).mResourceB);
                        mScalableImageView.setDrawableC(mAdapter.getItem(mPosition).mResourceC);
                    }
                    mScalableImageView.requestLayout();
                    mScalableImageView.startAnimation(fadeInAnimation);
                }

            });
            setImageControlButtonVisibility();
            mScalableImageView.startAnimation(fadeOutAnimation);
        }
    };

    /**
     * 画像のコントロールボタンの表示を切り替える
     */
    private void setImageControlButtonVisibility() {
        mNextButton.setVisibility(mAdapter.hasNext(mPosition) ? VISIBLE : GONE);
        mPrevButton.setVisibility(mAdapter.hasPrevious(mPosition) ? VISIBLE : GONE);
    }


    public void setAdapter(final Adapter adapter) {
        mAdapter = adapter;
        setImageView();
        setImageControlButtonVisibility();
    }

    public void setImageDefaultHeight(final int defaultHeight) {
        mImageDefaultHeight = defaultHeight;
        resetImageContainerHeight();
    }

    public void setMaxScale(final int maxScale) {
        mScalableImageView.setMaxScale(maxScale);
    }

    private void resetImageContainerHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageContainer.getLayoutParams();
        params.height = mImageDefaultHeight;
        mImageContainer.setLayoutParams(params);
        mContainer.requestLayout();
    }

    private void addImageContainerHeight(final int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageContainer.getLayoutParams();
        int distHeight = params.height - height;
        if (distHeight < mImageDefaultHeight) {
            distHeight = mImageDefaultHeight;
        } else if (distHeight > getHeight()) {
            distHeight = getHeight();
        }
        params.height = distHeight;
        mImageContainer.setLayoutParams(params);
        mContainer.requestLayout();
    }

    private void setImageContainerHeight(final int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageContainer.getLayoutParams();
        params.height = height;
        mImageContainer.setLayoutParams(params);
        mContainer.requestLayout();
    }

    private int getImageContainerHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageContainer.getLayoutParams();
        return params.height;
    }

    public void setNextButtonDrawable(final int drawable) {
        mNextButton.setImageDrawable(getResources().getDrawable(drawable));
    }

    public void setPrevButtonDrawable(final int drawable) {
        mPrevButton.setImageDrawable(getResources().getDrawable(drawable));
    }

    public void setContentsContainerView (final View view) {
        mContentsContainer.addView(view);
    }

    private void setImageView() {
        mScalableImageView.setDrawableA(mAdapter.getItem(mPosition).mResourceA);
        mScalableImageView.setTiling(mAdapter.getItem(mPosition).mTiling);
        if (mScalableImageView.getTiling()) {
            mScalableImageView.setDrawableB(mAdapter.getItem(mPosition).mResourceB);
            mScalableImageView.setDrawableC(mAdapter.getItem(mPosition).mResourceC);
        }
    }

    private void startImageMax() {
        mCount = COUNT_DOWN;
        mTimer.schedule(new AnimationTimer(getImageContainerHeight(), getHeight()), 0, 200 / COUNT_DOWN);
    }

    private void startImageMin() {
        mCount = COUNT_DOWN;
        mTimer.schedule(new AnimationTimer(getImageContainerHeight(), mImageDefaultHeight), 0, 200 / COUNT_DOWN);
    }

    @Override
    public void onImageTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mImageTouchMode = false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            mImageTouchMode = true;
        } else {
            mImageTouchMode = false;
        }
    }

    @Override
    public void onDraw(final boolean isBaseScale) {
        mImageController.setVisibility(isBaseScale ? VISIBLE : GONE);
        if (getImageContainerHeight() == getHeight()) {
            mScalableImageView.setFixScaleMode(false);
        } else {
            mScalableImageView.setFixScaleMode(true);
        }
    }

    class AnimationTimer extends TimerTask {
        private float startHeight;
        private float distHeight;
        private int stepHeight;

        AnimationTimer(final float start, final float dist) {
            distHeight = dist;
            startHeight = start;
        }

        private float getStep() {
            return (float) 1 / COUNT_DOWN * (COUNT_DOWN - mCount);
        }

        @Override
        public void run() {
            mCount--;
            stepHeight = (int) (startHeight + (distHeight - startHeight) * getStep());
            mHandler.post(mRunnable);

            if (mCount <= 0) {
                this.cancel();
            }
        }
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                setImageContainerHeight(stepHeight);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean overScrollBy(
            final int deltaX, final int deltaY,
            final int scrollX, final int scrollY,
            final int scrollRangeX, final int scrollRangeY,
            final int maxOverScrollX, final int maxOverScrollY,
            final boolean isTouchEvent) {

        if (scrollY == 0 && getImageContainerHeight() != getHeight()) {
            addImageContainerHeight(deltaY);
        }

        return super.overScrollBy(
                deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, 0, isTouchEvent);
    }

    public static ImageResource makeImageResource(final int rA, final int[] rB, final int[] rC, final boolean tiling) {
        ImageResource imageResource = new ImageResource();
        imageResource.mResourceA = rA;
        imageResource.mResourceB = rB;
        imageResource.mResourceC = rC;
        imageResource.mTiling = tiling;
        return imageResource;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDetachedFromWindow();
    }



    @Override
    public boolean onTouch(final View v, final MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 画像の大きさの半分のところで処理分岐
            int halfHeight = (mImageDefaultHeight + getHeight()) / 2;
            if (getImageContainerHeight() != mImageDefaultHeight && getImageContainerHeight() != getHeight()) {
                if (getImageContainerHeight() > halfHeight) {
                    startImageMax();
                } else {
                    startImageMin();
                }
            }
            mMaxViewBaseScaleModeTouchLastY = -1;
        }
        // 画像の全画面表示で拡大中の画像の位置を変える動きを定義
        if (getImageContainerHeight() == getHeight() && mImageTouchMode) {
            if (mScalableImageView.isBaseScale()) {
                // 初期スケールならタッチで閉じる動きに遷移する
                mImageMinMode = true;
            } else {
                mScalableImageView.onTouch(v, event);
                return true;
            }
        }
        // 画像の全画面表示をタッチで閉じる際の動きを定義
        if (mImageMinMode) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (mMaxViewBaseScaleModeTouchLastY != -1) {
                    addImageContainerHeight((int) ((mMaxViewBaseScaleModeTouchLastY - event.getY()) / 2));
                } else {
                    mMaxViewBaseScaleModeTouchLastY = event.getY();
                }
            } else {
                mImageMinMode = false;
                mMaxViewBaseScaleModeTouchLastY = -1;
            }
            return true;
        }
        return getImageContainerHeight() == getHeight();
    }

    public static class ImageResource {
        private int mResourceA;
        private int[] mResourceB;
        private int[] mResourceC;

        private boolean mTiling;

        public ImageResource() {

        }
    }


    public static class Adapter {
        private ArrayList<ImageResource> mItems;

        public Adapter() {
        }

        public void setItems(final ArrayList<ImageResource> items) {
            mItems = items;
        }

        public ImageResource getItem(final int position) {
            return mItems.get(position);
        }

        public boolean hasNext(final int position) {
            if (mItems == null) {
                return false;
            }
            return !(position + 1 == mItems.size());
        }

        public boolean hasPrevious(final int position) {
            if (mItems == null) {
                return false;
            }
            return position > 0;
        }

    }
}
