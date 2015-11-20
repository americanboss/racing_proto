package jp.co.equinestudio.racing.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(prefix = "m") @Getter @Setter
public class Race implements Serializable {

    public static int KAKUTEI = 1;

    // レース名の長さを指定するキー
    public static final int RACE_NAME_FULL = 0;
    public static final int RACE_NAME_10 = 10;
    public static final int RACE_NAME_6 = 6;
    public static final int RACE_NAME_3 = 3;

    @SerializedName("code")
    private String mCode;

    @SerializedName("schedule")
    private String mScheduleCode;

    @SerializedName("year")
    private String mYear;

    @SerializedName("month_day")
    private String mMonthDay;

    @SerializedName("course")
    private String mCourse;

    @SerializedName("race_num")
    private String mRaceNum;

    @SerializedName("weekday")
    private String mWeekDayCode;

    @SerializedName("race_name_full")
    private String mRaceNameFull;

    @SerializedName("race_name_sub")
    private String mRaceNameSub;

    @SerializedName("race_name_10")
    private String mRaceName10;

    @SerializedName("race_name_6")
    private String mRaceName6;

    @SerializedName("race_name_3")
    private String mRaceName3;

    @SerializedName("grade")
    private String mGradeCode;

    @SerializedName("distance")
    private String mDistance;

    @SerializedName("track")
    private String mTrack;

    @SerializedName("track_master")
    private String mTrackMaster;

    @SerializedName("track_course")
    private String mTrackCourse;

    @SerializedName("entry_num")
    private String mEntryNum;

    @SerializedName("weather")
    private String mWeatherCode;

    @SerializedName("track_condition_turf")
    private String mTrackConditionTurf;

    @SerializedName("track_condition_dirt")
    private String mTrackConditionDirt;

    @SerializedName("race_division")
    private String mRaceDivision;

    @SerializedName("start_time")
    private String mStartTime;

    @SerializedName("kakutei")
    private int mKakutei;


    /**
     * レース名を取得する
     * @param flag
     * @return
     */
    public String getRaceName(int flag) {
        String raceName = "";
        switch (flag) {
            case RACE_NAME_FULL:
                raceName = mRaceNameFull;
                break;
            case RACE_NAME_10:
                raceName = mRaceName10;
                break;
            case RACE_NAME_6:
                raceName = mRaceName6;
                break;
            case RACE_NAME_3:
                raceName = mRaceName3;
                break;
        }
        if (raceName == null || "".equals(raceName)) {
            raceName = mRaceDivision;
        }
        return raceName;
    }

}
