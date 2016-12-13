package jp.co.equinestudio.racing.util.comparator;

import android.util.Log;

import java.util.Comparator;

import jp.co.equinestudio.racing.adapter.item.OddsListItem;
import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.util.OddsUtils;

/**
 *
 */
public class OddsItemListComparator implements Comparator {

    private int mTarget;

    /**
     * 並べ替えターゲットを指定したコンストラクタ
     * @param target
     */
    public OddsItemListComparator(final int target) {
        mTarget = target;
    }

    @Override
    public int compare(final Object lhs, final Object rhs) {
        OddsListItem lMember = (OddsListItem) lhs;
        OddsListItem rMember = (OddsListItem) rhs;

        double lOdds = getOddsMin(lMember.getOdds());
        double rOdds = getOddsMin(rMember.getOdds());

        if (mTarget == OddsUtils.Table.ORDER_BY_ODDS) {
            return (int) Math.ceil(lOdds - rOdds);
        } else {
            return Integer.parseInt(lMember.getOdds()) - Integer.parseInt(rMember.getOdds());
        }
    }

    private double getOddsMin(final String arg) {
        String odds;
        String delimiter = "～";
        if (arg.contains(delimiter)) {
            odds = arg.substring(0, arg.indexOf(delimiter));
        } else {
            odds = arg;
        }
        return Double.parseDouble(odds);

    }
}
