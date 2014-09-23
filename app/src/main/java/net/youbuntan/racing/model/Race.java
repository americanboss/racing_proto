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
public class Race {

    public static int KAKUTEI = 1;

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

}
