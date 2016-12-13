package jp.co.equinestudio.racing.adapter.item;

import java.io.Serializable;

import jp.co.equinestudio.racing.model.RaceMember;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by masakikase on 2016/12/06.
 */
public class OddsListItem implements Serializable {

    @Accessors(prefix = "m") @Getter @Setter
    private String mHeadingAdditonal;
    @Accessors(prefix = "m") @Getter @Setter
    private String mNumber;
    @Accessors(prefix = "m") @Getter @Setter
    private String mBracketNumber;
    @Accessors(prefix = "m") @Getter @Setter
    private boolean mBracketShow;
    @Accessors(prefix = "m") @Getter @Setter
    private String mHorseName;
    @Accessors(prefix = "m") @Getter @Setter
    private String mOdds;
    @Accessors(prefix = "m") @Getter @Setter
    private String mOddsAdditional;



}
