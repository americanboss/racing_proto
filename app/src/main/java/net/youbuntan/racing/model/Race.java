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
    private String mWeekDay;

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

    @SerializedName("race_division")
    private String mRaceDivisionCode;

    @SerializedName("entry_condition")
    private String mEntryCondition;

    @SerializedName("distance")
    private String mDistance;

    @SerializedName("track")
    private String mTrackCode;

    @SerializedName("track_course")
    private String mTrackCourse;

    @SerializedName("start_time")
    private String mStartTime;

    @SerializedName("entry_num")
    private String mEntryNum;

    @SerializedName("weather")
    private String mWeatherCode;

    @SerializedName("track_condition_turf")
    private String mTrackConditionTurf;

    @SerializedName("track_condition_dirt")
    private String mTrackConditionDirt;

}
