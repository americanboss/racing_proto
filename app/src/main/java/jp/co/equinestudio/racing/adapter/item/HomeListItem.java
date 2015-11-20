package jp.co.equinestudio.racing.adapter.item;

import jp.co.equinestudio.racing.model.Race;
import jp.co.equinestudio.racing.model.Schedules;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ホーム画面のRecyclerViewで使うListItem
 */
public class HomeListItem {
    public static final int TYPE_ID_HEADING = 0;
    public static final int TYPE_ID_MAIN_RACE = 1;
    public static final int TYPE_ID_SCHEDULE = 2;
    public static final int TYPE_ID_SCHEDULE_RACE = 3;

    @Accessors(prefix = "m") @Getter @Setter
    private int mId;

    @Accessors(prefix = "m") @Getter @Setter
    private int mTypeId;

    @Accessors(prefix = "m") @Getter @Setter
    private Race mRace;

    @Accessors(prefix = "m") @Getter @Setter
    private Schedules.Schedule mSchedule;


}
