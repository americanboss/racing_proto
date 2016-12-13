package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.Manager.BracketColorManager;
import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.item.OddsListItem;
import jp.co.equinestudio.racing.util.OddsUtils;

/**
 *
 */
public class OddsListAdapter extends BaseAdapter{

    private Context mContext;

    private LayoutInflater mInflater;
    private List<OddsListItem> mItems;

    private int mOddsPosition;
    private int mOrderBy;


    private BracketColorManager mBracketColorManager;

    private final LinearLayout.LayoutParams mLayoutParamsSmall;
    private final LinearLayout.LayoutParams mLayoutParamsMedium;
    private final LinearLayout.LayoutParams mLayoutParamsLarge;

    private final int NUMBER_HEIGHT = 30;
    private final int NUMBER_WIDTH_SMALL = 30;
    private final int NUMBER_WIDTH_MEDIUM = 48;
    private final int NUMBER_WIDTH_LARGE = 66;

    private OnCheckChanged mOnCheckChanged;


    public OddsListAdapter(final Context context, final int orderBy, final OnCheckChanged onCheckChanged) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mBracketColorManager = new BracketColorManager();
        mOrderBy = orderBy;

        mLayoutParamsSmall = new LinearLayout.LayoutParams(convertDpToPx(context, NUMBER_WIDTH_SMALL), convertDpToPx(context, NUMBER_HEIGHT));
        mLayoutParamsMedium = new LinearLayout.LayoutParams(convertDpToPx(context, NUMBER_WIDTH_MEDIUM), convertDpToPx(context, NUMBER_HEIGHT));
        mLayoutParamsLarge = new LinearLayout.LayoutParams(convertDpToPx(context, NUMBER_WIDTH_LARGE), convertDpToPx(context, NUMBER_HEIGHT));

        mOnCheckChanged = onCheckChanged;
        mItems = new ArrayList<>();

    }

    public void setOddsItemList(final List<OddsListItem> oddsItemList) {
        mItems = oddsItemList;
    }

    public void setOddsPattern(int oddsPattern) {
        this.mOddsPosition = oddsPattern;
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

        final OddsListItem oddsItem = mItems.get(position);

        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_odds_show, null);

            viewHolder = new ViewHolder();
            viewHolder.mTargetNumber = ((TextView) view.findViewById(R.id.target_number));
            viewHolder.mHeadingAdditional = ((TextView) view.findViewById(R.id.heading_additional));
            viewHolder.mTextHorseName = ((TextView) view.findViewById(R.id.text_horse_name));
            viewHolder.mOdds = ((TextView) view.findViewById(R.id.odds));
            viewHolder.mOddsAdditional = ((TextView) view.findViewById(R.id.odds_additional));
            viewHolder.mChoiceSingle1 =  ((RadioButton) view.findViewById(R.id.choice_single1));
            viewHolder.mChoiceSingle2 =  ((RadioButton) view.findViewById(R.id.choice_single2));
            viewHolder.mChoiceMultiple1 =  ((CheckBox) view.findViewById(R.id.choice_multiple1));
            viewHolder.mChoiceMultiple2 =  ((CheckBox) view.findViewById(R.id.choice_multiple2));
            viewHolder.mChoiceMultiple3 =  ((CheckBox) view.findViewById(R.id.choice_multiple3));

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (mOddsPosition == OddsUtils.Table.WIN) {
            if (mOrderBy == OddsUtils.Table.ORDER_BY_GATE) {
                viewHolder.mHeadingAdditional.setVisibility(View.GONE);
            } else {
                viewHolder.mHeadingAdditional.setVisibility(View.VISIBLE);
                viewHolder.mHeadingAdditional.setText(oddsItem.getHeadingAdditonal());
            }
            viewHolder.mTargetNumber.setText(oddsItem.getNumber());
            viewHolder.mTextHorseName.setText(oddsItem.getHorseName());
            viewHolder.mOdds.setText(oddsItem.getOdds());
            viewHolder.mOddsAdditional.setText(oddsItem.getOddsAdditional());
            mBracketColorManager.setBracketNumber(oddsItem.getBracketNumber());
            viewHolder.mTargetNumber.setBackgroundColor(mBracketColorManager.getBackgroundColorResourceId(mContext));
            viewHolder.mTargetNumber.setTextColor(mBracketColorManager.getTextColorResourceId(mContext));
        } else if (mOddsPosition == OddsUtils.Table.ODDS_MULTIPLE) {
            // ボックス他のオッズ表示の対応
            viewHolder.mHeadingAdditional.setVisibility(View.GONE);
            viewHolder.mOddsAdditional.setVisibility(View.GONE);
            viewHolder.mTextHorseName.setVisibility(View.GONE);
            viewHolder.mTargetNumber.setText(oddsItem.getNumber());
            viewHolder.mOdds.setText(oddsItem.getOdds());

        } else {
            if (OddsUtils.isMultipleSelect(mOrderBy)) {
                viewHolder.mHeadingAdditional.setVisibility(View.GONE);
                viewHolder.mOdds.setVisibility(View.GONE);
                viewHolder.mOddsAdditional.setVisibility(View.GONE);

                viewHolder.mTargetNumber.setText(oddsItem.getNumber());
                viewHolder.mTextHorseName.setText(oddsItem.getHorseName());
                mBracketColorManager.setBracketNumber(oddsItem.getBracketNumber());
                viewHolder.mTargetNumber.setBackgroundColor(mBracketColorManager.getBackgroundColorResourceId(mContext));
                viewHolder.mTargetNumber.setTextColor(mBracketColorManager.getTextColorResourceId(mContext));

                // 枠連の場合は特殊表示を行う
                if (mOddsPosition == OddsUtils.Table.BRACKET) {
                    if (oddsItem.isBracketShow()) {
                        viewHolder.mTargetNumber.setText(oddsItem.getBracketNumber());
                        viewHolder.mTargetNumber.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.mTargetNumber.setVisibility(View.INVISIBLE);
                    }
                }

                viewHolder.mChoiceSingle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        // 枠連の場合は枠番
                        if (mOddsPosition == OddsUtils.Table.BRACKET) {
                            mOnCheckChanged.onSingleCheckChanged(0, b, oddsItem.getBracketNumber());
                        } else {
                            mOnCheckChanged.onSingleCheckChanged(0, b, oddsItem.getNumber());
                        }
                    }
                });
                viewHolder.mChoiceSingle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        mOnCheckChanged.onSingleCheckChanged(1, b, oddsItem.getNumber());
                    }
                });
                viewHolder.mChoiceMultiple1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        // 枠連の場合は枠番
                        if (mOddsPosition == OddsUtils.Table.BRACKET) {
                            mOnCheckChanged.onMultipleCheckChanged(0, b, oddsItem.getBracketNumber());
                        } else {
                            mOnCheckChanged.onMultipleCheckChanged(0, b, oddsItem.getNumber());
                        }
                    }
                });
                viewHolder.mChoiceMultiple2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        // 枠連の場合は枠番
                        if (mOddsPosition == OddsUtils.Table.BRACKET) {
                            mOnCheckChanged.onMultipleCheckChanged(1, b, oddsItem.getBracketNumber());
                        } else {
                            mOnCheckChanged.onMultipleCheckChanged(1, b, oddsItem.getNumber());
                        }
                    }
                });
                viewHolder.mChoiceMultiple3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        mOnCheckChanged.onMultipleCheckChanged(2, b, oddsItem.getNumber());
                    }
                });

            } else {
                viewHolder.mHeadingAdditional.setVisibility(View.GONE);
                viewHolder.mTextHorseName.setVisibility(View.GONE);
                viewHolder.mOddsAdditional.setVisibility(View.GONE);

                viewHolder.mTargetNumber.setText(oddsItem.getNumber());
                viewHolder.mOdds.setText(oddsItem.getOdds());
            }
        }

        // 馬番・枠番の横幅の設定
        if (mOddsPosition == OddsUtils.Table.WIN || OddsUtils.isMultipleSelect(mOrderBy)) {
            if (mOddsPosition == OddsUtils.Table.ODDS_MULTIPLE) {
                // 馬番選択式の識別はラージ表示
                viewHolder.mTargetNumber.setLayoutParams(mLayoutParamsLarge);
            } else {
                viewHolder.mTargetNumber.setLayoutParams(mLayoutParamsSmall);
            }
        } else if (mOddsPosition == OddsUtils.Table.BRACKET || mOddsPosition == OddsUtils.Table.QUINELLA || mOddsPosition == OddsUtils.Table.WIDE || mOddsPosition == OddsUtils.Table.EXACTA) {
            viewHolder.mTargetNumber.setLayoutParams(mLayoutParamsMedium);
        } else if (mOddsPosition == OddsUtils.Table.TRIO || mOddsPosition == OddsUtils.Table.TRIFECTA) {
            viewHolder.mTargetNumber.setLayoutParams(mLayoutParamsLarge);
        }

        // ラジオボタン・チェックボックスの設定
        // チェック状態の管理
        viewHolder.mChoiceMultiple1.setChecked(mOnCheckChanged.isMultipleChecked(0, oddsItem.getNumber()));
        viewHolder.mChoiceMultiple2.setChecked(mOnCheckChanged.isMultipleChecked(1, oddsItem.getNumber()));
        viewHolder.mChoiceMultiple3.setChecked(mOnCheckChanged.isMultipleChecked(2, oddsItem.getNumber()));

        if (mOddsPosition == OddsUtils.Table.ODDS_MULTIPLE) {
            viewHolder.mChoiceMultiple1.setVisibility(View.GONE);
            viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
            viewHolder.mChoiceMultiple3.setVisibility(View.GONE);
            viewHolder.mChoiceSingle1.setVisibility(View.GONE);
            viewHolder.mChoiceSingle2.setVisibility(View.GONE);
        } else  if (mOddsPosition == OddsUtils.Table.WIN || !OddsUtils.isMultipleSelect(mOrderBy) || mOrderBy == OddsUtils.Table.PATTERN_BOX) {
            viewHolder.mChoiceSingle1.setVisibility(View.GONE);
            viewHolder.mChoiceSingle2.setVisibility(View.GONE);
            if (mOddsPosition == OddsUtils.Table.BRACKET && !oddsItem.isBracketShow()) {
                viewHolder.mChoiceMultiple1.setVisibility(View.GONE);
            } else {
                viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
            }
            viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
            viewHolder.mChoiceMultiple3.setVisibility(View.GONE);
        } else {
            viewHolder.mChoiceSingle1.setVisibility(View.GONE);
            if (mOddsPosition == OddsUtils.Table.BRACKET || mOddsPosition == OddsUtils.Table.QUINELLA || mOddsPosition == OddsUtils.Table.WIDE || mOddsPosition == OddsUtils.Table.EXACTA) {
                viewHolder.mChoiceMultiple3.setVisibility(View.GONE);
                viewHolder.mChoiceSingle2.setVisibility(View.GONE);
                // フォーメーション
                if (mOrderBy == OddsUtils.Table.PATTERN_FORMATION) {
                    viewHolder.mChoiceSingle1.setVisibility(View.GONE);
                    if (mOddsPosition == OddsUtils.Table.BRACKET && !oddsItem.isBracketShow()) {
                        viewHolder.mChoiceMultiple1.setVisibility(View.GONE);
                        viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
                    } else {
                        viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
                        viewHolder.mChoiceMultiple2.setVisibility(View.VISIBLE);
                    }
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI) {
                    if (mOddsPosition == OddsUtils.Table.BRACKET && !oddsItem.isBracketShow()) {
                        viewHolder.mChoiceSingle1.setVisibility(View.GONE);
                        viewHolder.mChoiceMultiple1.setVisibility(View.GONE);
                    } else {
                        viewHolder.mChoiceSingle1.setVisibility(View.VISIBLE);
                        viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
                    }
                    viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
                }
            } else if (mOddsPosition == OddsUtils.Table.TRIO || mOddsPosition == OddsUtils.Table.TRIFECTA) {
                // フォーメーション
                if (mOrderBy == OddsUtils.Table.PATTERN_FORMATION) {
                    viewHolder.mChoiceSingle1.setVisibility(View.GONE);
                    viewHolder.mChoiceSingle2.setVisibility(View.GONE);
                    viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceMultiple2.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceMultiple3.setVisibility(View.VISIBLE);
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J1) {
                    viewHolder.mChoiceSingle1.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceSingle2.setVisibility(View.GONE);
                    viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
                    viewHolder.mChoiceMultiple3.setVisibility(View.GONE);
                } else if (mOrderBy == OddsUtils.Table.PATTERN_NAGASHI_J2) {
                    viewHolder.mChoiceSingle1.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceSingle2.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceMultiple1.setVisibility(View.VISIBLE);
                    viewHolder.mChoiceMultiple2.setVisibility(View.GONE);
                    viewHolder.mChoiceMultiple3.setVisibility(View.GONE);
                }
            }
        }

        return view;
    }

    private int convertDpToPx(Context context, int dp) {
        float d = context.getResources().getDisplayMetrics().density;
        return (int)((dp * d) + 0.5);
    }

    class ViewHolder {

        private LinearLayout mContainerBracketNumber; // 枠番のコンテナ

        private TextView mTargetNumber;        // 対象馬番・枠番
        private TextView mHeadingAdditional;   // 付加要素
        private TextView mTextHorseName;       // 馬名
        private TextView mOdds;                // オッズ
        private TextView mOddsAdditional;      // オッズ付加情報

        private RadioButton mChoiceSingle1;
        private RadioButton mChoiceSingle2;
        private CheckBox mChoiceMultiple1;
        private CheckBox mChoiceMultiple2;
        private CheckBox mChoiceMultiple3;


    }

    public interface OnCheckChanged {
        void onMultipleCheckChanged(int position, boolean checked, String number);
        void onSingleCheckChanged(int position, boolean checked, String number);
        boolean isMultipleChecked(int position, String number);
        boolean isSingleChecked(int position, String number);


    }



}
