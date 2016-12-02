package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.model.Odds;

/**
 *
 */
public class OddsTitleListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Odds mOdds;
    private boolean[] mItemEnable;
    private final static int ITEM_COUNT = 7;
    private String[] mOddsTitles;

    private OnOddsListOpen mOnOddsListOpen;

    public OddsTitleListAdapter(final Context context, final OnOddsListOpen listener) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mOddsTitles = context.getResources().getStringArray(R.array.odds_title);
        mOnOddsListOpen = listener;
    }



    public void setOdds(final Odds odds) {
        mOdds = odds;
        mItemEnable = new boolean[ITEM_COUNT];
        mItemEnable[0] = mOdds.getWinShowOddsMap() != null;
        mItemEnable[1] = mOdds.getBracketMap() != null;
        mItemEnable[2] = mOdds.getQuinellaMap() != null;
        mItemEnable[3] = mOdds.getWideMap() != null;
        mItemEnable[4] = mOdds.getExactaMap() != null;
        mItemEnable[5] = mOdds.getTrioMap() != null;
        mItemEnable[6] = mOdds.getTrifectaMap() != null;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public Object getItem(final int position) {
        return mOddsTitles[position];
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_odds_title, null);

        ((TextView) view.findViewById(R.id.text_odds_title)).setText(mOddsTitles[position]);

        Button buttonOdds = (Button) view.findViewById(R.id.button_odds_order_odds);
        Button buttonBracket = (Button) view.findViewById(R.id.button_odds_order_bracket_number);
        Button buttonGate = (Button) view.findViewById(R.id.button_odds_order_gate_number);
        Button buttonNagashi = (Button) view.findViewById(R.id.button_odds_order_nagashi);
        Button buttonBox = (Button) view.findViewById(R.id.button_odds_order_box);
        Button buttonFormation = (Button) view.findViewById(R.id.button_odds_order_formation);

        switch (position) {
            case 0:
                buttonBracket.setVisibility(View.GONE);
                buttonNagashi.setVisibility(View.GONE);
                buttonBox.setVisibility(View.GONE);
                buttonFormation.setVisibility(View.GONE);
                break;
            case 1:
                buttonGate.setVisibility(View.GONE);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                buttonBracket.setVisibility(View.GONE);
                buttonGate.setVisibility(View.GONE);
                break;
        }

        buttonOdds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, RaceOddsFragment.ORDER_BY_ODDS);
                }
            }
        });
        buttonBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, RaceOddsFragment.ORDER_BY_BRACKET);
                }
            }
        });
        buttonGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, RaceOddsFragment.ORDER_BY_GATE);
                }
            }
        });
        return view;
    }

    public interface OnOddsListOpen {
        void onOddsListOpen(final int position, final int orderBy);
    }

}
