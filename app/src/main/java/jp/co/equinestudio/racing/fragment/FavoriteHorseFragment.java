package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.FavoriteHorseListAdapter;

/**
 *
 */
public class FavoriteHorseFragment extends BaseFragment {


    private ListView mFavoriteHorseList;


    public static FavoriteHorseFragment newInstance() {
        Bundle args = new Bundle();

        FavoriteHorseFragment fragment = new FavoriteHorseFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_horse, null);

        mFavoriteHorseList = (ListView) view.findViewById(R.id.list_view);
        FavoriteHorseListAdapter mAdapter = new FavoriteHorseListAdapter(getContext());
        mFavoriteHorseList.setAdapter(mAdapter);



        Bundle args = getArguments();

        // Viewの取得

        return view;
    }

}
