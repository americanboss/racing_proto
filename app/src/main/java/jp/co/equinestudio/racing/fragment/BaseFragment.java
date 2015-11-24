package jp.co.equinestudio.racing.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 *
 */
public class BaseFragment extends Fragment {

    protected Context getContext() {
        return getActivity().getApplicationContext();
    }
}
