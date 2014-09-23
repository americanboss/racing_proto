package net.youbuntan.racing.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(prefix = "m") @Getter @Setter
public class RaceData {

    @SerializedName("member")
    private ArrayList<RaceMember> mRaceMembers;

    @SerializedName("pay")
    private RacePay mPay;

}
