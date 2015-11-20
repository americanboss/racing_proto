package jp.co.equinestudio.racing.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(prefix = "m") @Getter @Setter
public class RaceMember {

    @SerializedName("horse_code")
    private String mHorseCode;

    @SerializedName("bracket_number")
    private String mBracketNumber;

    @SerializedName("gate_number")
    private int mGateNumber;

    @SerializedName("horse_name")
    private String mHorseName;

    @SerializedName("father")
    private String mFatherName;

    @SerializedName("mother")
    private String mMotherName;

    @SerializedName("mothers_father")
    private String mMothersFatherName;

    @SerializedName("gender")
    private String mGender;

    @SerializedName("age")
    private int mAge;

    @SerializedName("jockey_name")
    private String mJockeyName;

    @SerializedName("jockey_code")
    private String mJockeyCode;

    @SerializedName("jockey_mark")
    private String mJockeyMark;

    @SerializedName("jockey_weight")
    private String mJockeyWeight;

    @SerializedName("trainer_name")
    private String mTrainerName;

    @SerializedName("trainer_belong_code")
    private String mTrainerBelongCode;

    @SerializedName("horse_color_code")
    private String mHorseColorCode;

    @SerializedName("trouble_code")
    private String mTroubleCode;

    @SerializedName("result")
    private String mResult;

    @SerializedName("odds_order")
    private String mOddsOrder;

    @SerializedName("time")
    private String mTime;

    @SerializedName("horse_weight")
    private String mHorseWeight;

    @SerializedName("horse_distance")
    private String mHorseDistance;

    @SerializedName("scratch_code")
    private int mScratchCode;

}
