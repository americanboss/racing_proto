package net.youbuntan.racing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * すけーらぶるなImageView
 */
public class ScalableImageView  extends View implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private Bitmap mBitmap;

    private Controller mController;

    private int mCount;
    private static final int COUNT_DOWN = 60;

    private static final String TAG = "ScaleImageView";

    private float mX;
    private float mY;
    private float mScale;

    private int mMaxScale;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mImageWidth;
    private int mImageHeight;
    private float mBaseScale;

    private GestureDetector mGestureDetector;

    private Timer mTimer;

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mScreenWidth = this.getWidth();
        mScreenHeight = this.getHeight();

        float scaleW = (float) mScreenWidth / (float) mImageWidth;
        float scaleH = (float) mScreenHeight / (float) mImageHeight;
        mScale = (scaleH < scaleW) ? scaleH : scaleW;
        mBaseScale = mScale;

        mTimer = new Timer(false);

        setValidParam();
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDetachedFromWindow();
    }

    public ScalableImageView(Context context) {
        super(context);

        setOnTouchListener(this);

        mController = new Controller();

        mX = 0;
        mY = 0;
        mScale = mBaseScale;
        mMaxScale = 3;

        mGestureDetector = new GestureDetector(context, this);

        mTimer = new Timer(false);
    }

    public void setMaxScale(final int maxScale) {
        mMaxScale = maxScale;
    }

    public void setDrawable(final int drawable) {
        mBitmap = BitmapFactory.decodeResource(getResources(), drawable);
        mImageWidth = mBitmap.getWidth();
        mImageHeight = mBitmap.getHeight();
    }

    private float getCenterPointX() {
        return mX - (mScreenWidth / (2 * mScale ) );
    }
    private float getCenterPointY() {
        return mY - (mScreenHeight / (2 * mScale ) );
    }

    private float getXByCenter(final float centerX) {
        return centerX + (mScreenWidth / (2 * mScale));
    }
    private float getYByCenter(final float centerY) {
        return centerY + (mScreenHeight / (2 * mScale));
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        // シングルタッチの場合
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mController.lastX0 = event.getX();
            mController.lastY0 = event.getY();
            mController.touched = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mController.reset();
        } else {
            if (mController.touched) {
                if (event.getPointerCount() == 1) {
                    if (mController.lastDistance != -1) {
                        mController.lastDistance = -1;
                        mController.lastX0 = event.getX();
                        mController.lastY0 = event.getY();
                    } else {
                        moved(event.getX(), event.getY());
                    }
                } else if (event.getPointerCount() == 2) {
                    scaled(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                }
            }
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private void moved(final float distX, final float distY) {
        float moveX = (distX - mController.lastX0) / mScale;
        float moveY = (distY - mController.lastY0) / mScale;
        mController.lastX0 = distX;
        mController.lastY0 = distY;

        mX += moveX;
        mY += moveY;

        setValidParam();

        invalidate();
    }

    private void scaled(final float x0, final float y0, final float x1, final float y1) {
        // 直線距離を算出する
        double distance = Math.sqrt(Math.pow(Math.abs(x0 - x1), 2) + Math.pow(Math.abs(y0 - y1), 2));
        if (mController.lastDistance != -1) {
            float centerX = getCenterPointX();
            float centerY = getCenterPointY();
            mScale = mScale * (float) (distance / mController.lastDistance);
            mX = getXByCenter(centerX);
            mY = getYByCenter(centerY);
        } else {
            mController.lastScale = mScale;
        }
        mController.lastDistance = distance;


        setValidParam();

        invalidate();


    }

    private float getNextScale() {
        float scale = mScale;
        if (scale == mBaseScale * mMaxScale) {
            scale = mBaseScale;
        } else if (scale + mBaseScale > mBaseScale * mMaxScale) {
            scale = mBaseScale * mMaxScale;
        } else {
            scale = scale + mBaseScale;
        }
        return scale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale(mScale, mScale);
        canvas.drawBitmap(mBitmap, mX, mY, null);
    }

    @Override
    public boolean onDown(final MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(final MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(final MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(final MotionEvent e) {
    }

    @Override
    public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(final MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(final MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(final MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            // 移動量を計算
            if (mScale > getNextScale()) {
                startResetAnimation();
            } else {
                float moveX = ((mScreenWidth / 2) - e.getX()) / mScale;
                float moveY = ((mScreenHeight / 2) - e.getY()) / mScale;
                startZoomAnimation(moveX, moveY, getNextScale());
            }
        }
        return false;
    }

    private void startZoomAnimation(final float moveX, final float moveY, final float nextScale) {
        if (mCount == 0) {
            mCount = COUNT_DOWN;
            mTimer.schedule(new AnimationTimer(moveX, moveY, nextScale), 0, 200 / COUNT_DOWN);
        }
    }

    private void startResetAnimation() {
        if (mCount == 0) {
            mCount = COUNT_DOWN;
            mTimer.schedule(new AnimationTimer(), 0, 200 / COUNT_DOWN);
        }
    }

    private void setValidParam() {
        // スケール
        if (mScale > mMaxScale) {
            mScale = mMaxScale;
        } else if (mScale < mBaseScale) {
            mScale = mBaseScale;
        }

        // 横位置
        if (mImageWidth * mScale < mScreenWidth) {
            mX = getXByCenter(-(mImageWidth / 2));
        } else if (mX > 0) {
            mX = 0;
        } else if (mX - (mScreenWidth / mScale) < 0 - mImageWidth) {
            mX = ( - mImageWidth + (mScreenWidth / mScale));
        }

        // 縦位置
        if (mImageHeight * mScale < mScreenHeight) {
            mY = getYByCenter(-(mImageHeight / 2));
        } else if (mY > 0) {
            mY = 0;
        } else if (mY - (mScreenHeight / mScale) < 0 - mImageHeight) {
            mY = ( - mImageHeight + (mScreenHeight / mScale));
        }
    }



    class AnimationTimer extends TimerTask {
        private float startX;
        private float startY;
        private float startCenterX;
        private float startCenterY;
        private float startScale;
        private float endX;
        private float endY;
        private float endCenterX;
        private float endCenterY;
        private float endScale;

        AnimationTimer(final float moveX, final float moveY, final float endScale) {
            startX = mX;
            startY = mY;
            startCenterX = startX - (mScreenWidth / (2 * mScale ) );
            startCenterY = startY - (mScreenHeight / (2 * mScale ) );
            startScale = mScale;
            endX = mX + moveX / endScale;
            endY = mY + moveY / endScale;
            endCenterX = endX - (mScreenWidth / (2 * endScale ) );
            endCenterY = endY - (mScreenHeight / (2 * endScale ) );
            this.endScale = endScale;

        }

        AnimationTimer() {
            startX = mX;
            startY = mY;
            startCenterX = startX - (mScreenWidth / (2 * mScale ) );
            startCenterY = startY - (mScreenHeight / (2 * mScale ) );
            startScale = mScale;
            endX = 0;
            endY = 0;
            endScale = mBaseScale;
            endCenterX = endX - (mScreenWidth / (2 * endScale ) );
            endCenterY = endY - (mScreenHeight / (2 * endScale ) );
        }

        private float getStep() {
            return (float) 1 / COUNT_DOWN * (COUNT_DOWN - mCount);
        }

        @Override
        public void run() {
            mCount--;
            float centerX = startCenterX + (endCenterX - startCenterX) * getStep();
            float centerY = startCenterY + (endCenterY - startCenterY) * getStep();
            float scale = startScale + (endScale - startScale) * getStep();

            mX = getXByCenter(centerX);
            mY = getYByCenter(centerY);
            mScale = scale;

            setValidParam();

            mHandler.post(mRunnable);

            if (mCount == 0) {
                this.cancel();
            }
        }
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    class Controller {
        private float lastX0;
        private float lastY0;
        private double lastDistance;
        private float lastScale;
        private boolean touched;
        private boolean multi;

        Controller() {
            reset();
        }

        public void reset() {
            lastX0 = 0;
            lastY0 = 0;
            lastDistance = -1;
            touched = false;
            multi = false;
        }
    }
}