package jp.co.equinestudio.racing.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(prefix = "m") @Getter @Setter
public class RacePay {

    @SerializedName("Ta")
    private ArrayList<Pay> mWin;

    @SerializedName("Fu")
    private ArrayList<Pay> mShow;

    @SerializedName("Wa")
    private ArrayList<Pay> mBracketQuinella;

    @SerializedName("UR")
    private ArrayList<Pay> mQuinella;

    @SerializedName("Wi")
    private ArrayList<Pay> mWide;

    @SerializedName("UT")
    private ArrayList<Pay> mExacta;

    @SerializedName("SP")
    private ArrayList<Pay> mTrio;

    @SerializedName("ST")
    private ArrayList<Pay> mTrifecta;

    @Accessors(prefix = "m") @Getter @Setter
    public class Pay {

        @SerializedName("number")
        private String mNumber;

        @SerializedName("pay")
        private int mPay;

    }

}
