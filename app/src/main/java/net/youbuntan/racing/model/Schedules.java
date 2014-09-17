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
public class Schedules {

    @SerializedName("date_list")
    private ArrayList<ScheduleDate> mScheduleDates;

    @Accessors(prefix = "m") @Getter @Setter
    public class ScheduleDate {
        @SerializedName("date")
        private String mDate;

        @SerializedName("schedules")
        private ArrayList<Schedule> mSchedules;
    }

    @Accessors(prefix = "m") @Getter @Setter
    public class Schedule {
        @SerializedName("code")
        private String mCode;

        @SerializedName("year")
        private String mYear;

        @SerializedName("month_day")
        private String mMonthDay;

        @SerializedName("course")
        private String mCourse;

        @SerializedName("kaiji")
        private String mKaiji;

        @SerializedName("nichiji")
        private String mNichiji;

    }



}
