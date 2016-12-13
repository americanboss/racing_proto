package jp.co.equinestudio.racing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.FavoriteHorseListAdapter;
import jp.co.equinestudio.racing.dao.DbHelper;
import jp.co.equinestudio.racing.dao.green.DaoSession;
import jp.co.equinestudio.racing.dao.green.FavoriteHorse;
import jp.co.equinestudio.racing.dao.green.FavoriteHorseDao;

/**
 *
 */
public class FavoriteHorseFragment extends BaseFragment {


    private ListView mFavoriteHorseList;
    private FavoriteHorseListAdapter mAdapter;
    private FavoriteHorseDao mFavoriteHorseDao;


    public static FavoriteHorseFragment newInstance() {
        Bundle args = new Bundle();

        FavoriteHorseFragment fragment = new FavoriteHorseFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Viewの取得
        View view = inflater.inflate(R.layout.fragment_favorite_horse, null);

        mFavoriteHorseList = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new FavoriteHorseListAdapter(getContext());

        DaoSession session = DbHelper.getInstance(getActivity().getApplicationContext()).session();
        mFavoriteHorseDao = session.getFavoriteHorseDao();
        List<FavoriteHorse> favoriteHorseList = mFavoriteHorseDao.loadAll();
        mAdapter.setItems(favoriteHorseList);

        mFavoriteHorseList.setAdapter(mAdapter);

        return view;
    }

}
