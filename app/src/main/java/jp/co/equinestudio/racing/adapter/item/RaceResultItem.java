package jp.co.equinestudio.racing.adapter.item;

import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.RaceMember;
import jp.co.equinestudio.racing.model.RacePay;
import jp.co.equinestudio.racing.model.Schedules;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * レース結果画面のRecyclerViewで使うListItem
 */
public class RaceResultItem {
    public static final int TYPE_ID_HEADING = 0;
    public static final int TYPE_ID_RESULT = 1;
    public static final int TYPE_ID_PAY_HEADING = 2;
    public static final int TYPE_ID_PAY = 3;

    @Accessors(prefix = "m") @Getter @Setter
    private int mTypeId;

    @Accessors(prefix = "m") @Getter @Setter
    private String mHeading;

    @Accessors(prefix = "m") @Getter @Setter
    private String mValue;

    @Accessors(prefix = "m") @Getter @Setter
    private RaceMember mResult;

}
