package net.youbuntan.racing.util.comparator;

import net.youbuntan.racing.model.RaceMember;

import java.util.Comparator;

/**
 *
 */
public class RaceMemberComparator implements Comparator {

    public static final int GATE_NUMBER = 0;
    public static final int RESULT = 1;

    private int mTarget;

    /**
     * 並べ替えターゲットを指定したコンストラクタ
     * @param target
     */
    public RaceMemberComparator(final int target) {
        mTarget = target;
    }

    @Override
    public int compare(final Object lhs, final Object rhs) {
        RaceMember lMember = (RaceMember) lhs;
        RaceMember rMember = (RaceMember) rhs;

        if (mTarget == GATE_NUMBER) {
            return lMember.getGateNumber() - rMember.getGateNumber();
        } else {
            return Integer.parseInt(lMember.getResult()) - Integer.parseInt(rMember.getResult());
        }

    }
}
