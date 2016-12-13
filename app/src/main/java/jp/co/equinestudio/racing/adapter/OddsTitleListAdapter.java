package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.util.OddsUtils;

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
        mItemEnable[OddsUtils.Table.WIN] = mOdds.getWinShowOddsMap() != null;
        mItemEnable[OddsUtils.Table.BRACKET] = mOdds.getBracketMap() != null;
        mItemEnable[OddsUtils.Table.QUINELLA] = mOdds.getQuinellaMap() != null;
        mItemEnable[OddsUtils.Table.WIDE] = mOdds.getWideMap() != null;
        mItemEnable[OddsUtils.Table.EXACTA] = mOdds.getExactaMap() != null;
        mItemEnable[OddsUtils.Table.TRIO] = mOdds.getTrioMap() != null;
        mItemEnable[OddsUtils.Table.TRIFECTA] = mOdds.getTrifectaMap() != null;
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
        Button buttonNagashiJ1 = (Button) view.findViewById(R.id.button_odds_order_nagashi_j1);
        Button buttonNagashiJ2 = (Button) view.findViewById(R.id.button_odds_order_nagashi_j2);
        Button buttonBox = (Button) view.findViewById(R.id.button_odds_order_box);
        Button buttonFormation = (Button) view.findViewById(R.id.button_odds_order_formation);

        buttonNagashiJ1.setVisibility(View.GONE);
        buttonNagashiJ2.setVisibility(View.GONE);
        switch (position) {
            case OddsUtils.Table.WIN:
                buttonBracket.setVisibility(View.GONE);
                buttonNagashi.setVisibility(View.GONE);
                buttonBox.setVisibility(View.GONE);
                buttonFormation.setVisibility(View.GONE);
                break;
            case OddsUtils.Table.BRACKET:
                buttonGate.setVisibility(View.GONE);
                break;
            case OddsUtils.Table.QUINELLA:
            case OddsUtils.Table.WIDE:
            case OddsUtils.Table.EXACTA:
            case OddsUtils.Table.TRIO:
            case OddsUtils.Table.TRIFECTA:
                buttonBracket.setVisibility(View.GONE);
                buttonGate.setVisibility(View.GONE);
                break;
        }
        if (position == OddsUtils.Table.TRIO || position == OddsUtils.Table.TRIFECTA) {
            buttonNagashiJ1.setVisibility(View.VISIBLE);
            buttonNagashiJ2.setVisibility(View.VISIBLE);
            buttonNagashi.setVisibility(View.GONE);
        }

        buttonOdds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.ORDER_BY_ODDS);
                }
            }
        });
        buttonBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.ORDER_BY_BRACKET);
                }
            }
        });
        buttonGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.ORDER_BY_GATE);
                }
            }
        });
        buttonNagashi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.PATTERN_NAGASHI);
                }
            }
        });
        buttonBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.PATTERN_BOX);
                }
            }
        });
        buttonFormation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.PATTERN_FORMATION);
                }
            }
        });
        buttonNagashiJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.PATTERN_NAGASHI_J1);
                }
            }
        });
        buttonNagashiJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnOddsListOpen != null) {
                    mOnOddsListOpen.onOddsListOpen(position, OddsUtils.Table.PATTERN_NAGASHI_J2);
                }
            }
        });
        return view;
    }

    public interface OnOddsListOpen {
        void onOddsListOpen(final int position, final int orderBy);
    }

}
