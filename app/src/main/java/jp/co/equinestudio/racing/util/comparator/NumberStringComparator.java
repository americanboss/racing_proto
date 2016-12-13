package jp.co.equinestudio.racing.util.comparator;

import java.util.Comparator;

import jp.co.equinestudio.racing.adapter.item.OddsListItem;
import jp.co.equinestudio.racing.util.OddsUtils;

/**
 *
 */
public class NumberStringComparator implements Comparator {

    /**
     */
    public NumberStringComparator() {
    }

    @Override
    public int compare(final Object lhs, final Object rhs) {
        String lMember = (String) lhs;
        String rMember = (String) rhs;

        return Integer.parseInt(lMember) - Integer.parseInt(rMember);
    }
}
