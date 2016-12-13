package jp.co.equinestudio.racing.util.comparator;

import java.util.Comparator;

import jp.co.equinestudio.racing.fragment.race.RaceOddsFragment;
import jp.co.equinestudio.racing.model.Odds;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.util.OddsUtils;

/**
 *
 */
public class WinShowOddsListComparator implements Comparator {

    private int mTarget;

    /**
     * 並べ替えターゲットを指定したコンストラクタ
     * @param target
     */
    public WinShowOddsListComparator(final int target) {
        mTarget = target;
    }

    @Override
    public int compare(final Object lhs, final Object rhs) {
        Odds.WinShowOdds lMember = (Odds.WinShowOdds) lhs;
        Odds.WinShowOdds rMember = (Odds.WinShowOdds) rhs;

        if (mTarget == OddsUtils.Table.ORDER_BY_GATE) {
            return Integer.parseInt(lMember.getGateNumber()) - Integer.parseInt(rMember.getGateNumber());
        } else {
            return Integer.parseInt(lMember.getWinOrder()) - Integer.parseInt(rMember.getWinOrder());
        }

    }
}
