package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.model.Odds;

/**
 *
 */
public class OddsListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<OddsItem> mItems;
    private Odds mOdds;


    public OddsListAdapter() {

    }

    public OddsListAdapter(final Context context, final List<OddsItem> oddsItemList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItems = oddsItemList;
    }



    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_odds_show, null);

        OddsItem oddsItem = mItems.get(position);

        // TODO: Holderパターンに変更する
        ((TextView) view.findViewById(R.id.target_number)).setText(oddsItem.Number);
        ((TextView) view.findViewById(R.id.heading_additional)).setText(oddsItem.HeadingAdditonal);
        ((TextView) view.findViewById(R.id.text_horse_name)).setText(oddsItem.HorseName);
        ((TextView) view.findViewById(R.id.odds)).setText(oddsItem.Odds);
        ((TextView) view.findViewById(R.id.odds_additional)).setText(oddsItem.OddsAdditional);


        //((TextView) view.findViewById(R.id.text_odds_title)).setText(mOddsTitles[position]);

        return view;
    }

    public void setOdds(final Odds odds) {
        mOdds = odds;
    }

    public OddsItem getOddsItem() {
        OddsItem oddsItem = new OddsItem();
        return oddsItem;
    }

    public class OddsItem {

        public String HeadingAdditonal;
        public String Number;
        public String HorseName;
        public String Odds;
        public String OddsAdditional;
        public int CheckboxCount;
    }

}
