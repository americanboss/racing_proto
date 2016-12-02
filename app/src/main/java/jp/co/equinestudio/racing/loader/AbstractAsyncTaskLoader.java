package jp.co.equinestudio.racing.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * AsyncTaskLoaderの改造クラス
 */
public abstract class AbstractAsyncTaskLoader<T> extends AsyncTaskLoader<T> {

    protected T result;

    public AbstractAsyncTaskLoader(final Context context) {
        super(context);
    }

    @Override
    public abstract T loadInBackground();

    @Override
    public void deliverResult(final T data) {
        if (isReset()) {
            return;
        }

        result = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (result != null) {
            deliverResult(result);
        }

        if (takeContentChanged() || result == null) {
            forceLoad(); // 非同期処理を開始
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad(); // 非同期処理のキャンセル
    }

    @Override
    public void onCanceled(final T data) {
        // 特にやることなし
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        result = null;
    }
}