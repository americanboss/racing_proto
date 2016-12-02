package jp.co.equinestudio.racing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.co.equinestudio.racing.Manager.BracketColorManager;
import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.item.RaceResultItem;
import jp.co.equinestudio.racing.model.RaceData;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.model.RacePay;

/**
 * レース結果のListAdapter
 */
public class RaceResultListAdapter extends  RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<RaceResultItem> mItems;

    public RaceResultListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mItems = new ArrayList<>();
    }

    public void setRaceData(final RaceData raceData) {
        //
        mItems.clear();
        int id = 0;

        // 着順を構築
        RaceResultItem resultHeadingItem = new RaceResultItem();
        resultHeadingItem.setTypeId(RaceResultItem.TYPE_ID_HEADING);
        resultHeadingItem.setHeading("着順");
        mItems.add(resultHeadingItem);

        for (RaceMember raceMember : raceData.getRaceMembers()) {
            RaceResultItem item = new RaceResultItem();
            item.setTypeId(RaceResultItem.TYPE_ID_RESULT);
            item.setResult(raceMember);
            mItems.add(item);
        }

        // 払戻金を構築
        RaceResultItem payHeadingItem = new RaceResultItem();
        payHeadingItem.setTypeId(RaceResultItem.TYPE_ID_HEADING);
        payHeadingItem.setHeading("払戻金");
        mItems.add(payHeadingItem);

        if (raceData.getPay() != null) {
            mItems.addAll(getPayItems("単勝", raceData.getPay().getWin()));
            mItems.addAll(getPayItems("複勝", raceData.getPay().getShow()));
            mItems.addAll(getPayItems("枠連", raceData.getPay().getBracketQuinella()));
            mItems.addAll(getPayItems("馬連", raceData.getPay().getQuinella()));
            mItems.addAll(getPayItems("馬単", raceData.getPay().getExacta()));
            mItems.addAll(getPayItems("三連複", raceData.getPay().getTrio()));
            mItems.addAll(getPayItems("三連単", raceData.getPay().getTrifecta()));
        }
    }

    public void clearRaceData() {
        mItems.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int typeId) {
        switch (typeId) {
            case RaceResultItem.TYPE_ID_HEADING :
                return new HeadingViewHolder(mInflater.inflate(R.layout.recycler_result_heading, parent, false));
            case RaceResultItem.TYPE_ID_RESULT :
                return new ResultViewHolder(mInflater.inflate(R.layout.recycler_result_result, parent, false));
            case RaceResultItem.TYPE_ID_PAY_HEADING:
                return new PayViewHeadingHolder(mInflater.inflate(R.layout.recycler_result_pay_heading, parent, false));
            case RaceResultItem.TYPE_ID_PAY:
                return new PayViewHolder(mInflater.inflate(R.layout.recycler_result_pay, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        switch (mItems.get(position).getTypeId()) {
            case RaceResultItem.TYPE_ID_HEADING :
                bindHeadingView(viewHolder, position);
                break;
            case RaceResultItem.TYPE_ID_RESULT :
                bindResultView(viewHolder, position);
                break;
            case RaceResultItem.TYPE_ID_PAY_HEADING:
                bindPayHeadingView(viewHolder, position);
                break;
            case RaceResultItem.TYPE_ID_PAY:
                bindPayView(viewHolder, position);
                break;
            default:
                break;
        }

    }

    @Override
    public long getItemId(final int position) {
        return mItems.get(position).getTypeId();
    }

    @Override
    public int getItemCount() {
//        return mRaceData.getRaceMembers().size();
        return mItems.size();
    }

    /**
     * 見出し
     */
    class HeadingViewHolder extends RecyclerView.ViewHolder {

        private TextView mHeading;

        HeadingViewHolder(final View itemView) {
            super(itemView);
            mHeading = (TextView) itemView.findViewById(R.id.heading);
        }
    }

    private void bindHeadingView(final RecyclerView.ViewHolder viewHolder, final int position) {
        HeadingViewHolder holder = (HeadingViewHolder) viewHolder;
        holder.mHeading.setText(mItems.get(position).getHeading());
    }

    /**
     * 着順
     */
    class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mResult;           // 着順
        private TextView mGateNumber;       // 馬番
        private TextView mHorseName;        // 馬名
        private TextView mHorseDistance;    // 着差

        ResultViewHolder(final View itemView) {
            super(itemView);
            mResult = (TextView) itemView.findViewById(R.id.text_result);
            mGateNumber = (TextView) itemView.findViewById(R.id.text_gate_number);
            mHorseName = (TextView) itemView.findViewById(R.id.text_horse_name);
            mHorseDistance = (TextView) itemView.findViewById(R.id.text_horse_distance);
        }
    }

    private void bindResultView(final RecyclerView.ViewHolder viewHolder, final int position) {
        ResultViewHolder holder = (ResultViewHolder) viewHolder;
        holder.mResult.setText(mItems.get(position).getResult().getResult());
        holder.mGateNumber.setText(Integer.toString(mItems.get(position).getResult().getGateNumber()));
        holder.mHorseName.setText(mItems.get(position).getResult().getHorseName());
        holder.mHorseDistance.setText(mItems.get(position).getResult().getHorseDistance());

        // 馬番に枠番を着色する
        BracketColorManager bracketColorManager = new BracketColorManager();
        bracketColorManager.setBracketNumber(mItems.get(position).getResult().getBracketNumber());
        holder.mGateNumber.setBackgroundColor(bracketColorManager.getBackgroundColorResourceId(mContext));
        holder.mGateNumber.setTextColor(bracketColorManager.getTextColorResourceId(mContext));
    }

    /**
     * 払戻金見出し
     */
    class PayViewHeadingHolder extends RecyclerView.ViewHolder {

        private TextView mHeading;

        PayViewHeadingHolder(final View itemView) {
            super(itemView);
            mHeading = (TextView) itemView.findViewById(R.id.heading);
        }
    }

    private void bindPayHeadingView(final RecyclerView.ViewHolder viewHolder, final int position) {
        PayViewHeadingHolder holder = (PayViewHeadingHolder) viewHolder;
        holder.mHeading.setText(mItems.get(position).getHeading());
    }

    /**
     * 払戻金額
     */
    class PayViewHolder extends RecyclerView.ViewHolder {

        private TextView mPayNumbers;
        private TextView mPayAmount;

        PayViewHolder(final View itemView) {
            super(itemView);
            mPayNumbers = (TextView) itemView.findViewById(R.id.pay_numbers);
            mPayAmount = (TextView) itemView.findViewById(R.id.pay_amount);
        }
    }

    private void bindPayView(final RecyclerView.ViewHolder viewHolder, final int position) {
        PayViewHolder holder = (PayViewHolder) viewHolder;
        holder.mPayNumbers.setText(mItems.get(position).getHeading());
        holder.mPayAmount.setText(mItems.get(position).getValue());
    }

    private List<RaceResultItem> getPayItems(final String heading, final ArrayList<RacePay.Pay> payList) {

        List<RaceResultItem> items = new ArrayList<>();

        if (payList.size() > 0) {
            RaceResultItem item = new RaceResultItem();
            item.setTypeId(RaceResultItem.TYPE_ID_PAY_HEADING);
            item.setHeading(heading);
            items.add(item);

            for (RacePay.Pay pay : payList) {
                RaceResultItem payItem = new RaceResultItem();
                payItem.setTypeId(RaceResultItem.TYPE_ID_PAY);
                payItem.setHeading(pay.getNumber());
                payItem.setValue(Integer.toString(pay.getPay()));
                items.add(payItem);
            }
        }

        return items;

    }


    @Override
    public int getItemViewType(final int position) {
        return mItems.get(position).getTypeId();
    }
}
