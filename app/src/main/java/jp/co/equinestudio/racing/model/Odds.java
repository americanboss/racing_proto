package jp.co.equinestudio.racing.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(prefix = "m") @Getter @Setter
public class Odds {

    @SerializedName("TaFu")
    private Map<String, WinShowOdds> mWinShowOddsMap;

    @SerializedName("Wa")
    private Map<String, String> mBracketMap;

    @SerializedName("UR")
    private Map<String, String> mQuinellaMap;

    @SerializedName("Wi")
    private Map<String, String> mWideMap;

    @SerializedName("UT")
    private Map<String, String> mExactaMap;

    @SerializedName("SP")
    private Map<String, String> mTrioMap;

    @SerializedName("ST")
    private Map<String, String> mTrifectaMap;

    @Accessors(prefix = "m") @Getter @Setter
    public class WinShowOdds {
        @SerializedName("Umaban")
        private String mGateNumber;

        @SerializedName("TanOdds")
        private String mWinOdds;

        @SerializedName("TanNinki")
        private String mWinOrder;

        @SerializedName("FukuOddsLow")
        private String mShowOddsLow;

        @SerializedName("FukuOddsHigh")
        private String mShowOddsHigh;

        @SerializedName("FukuNinki")
        private String mShowOrder;

    }


}
