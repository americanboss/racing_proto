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
public class RaceList {

    @SerializedName("course")
    private String mCourse;

    @SerializedName("weekday")
    private String mWeekday;

    @SerializedName("race_list")
    private ArrayList<Race> mRaces;

    public RaceList() {
        mRaces = new ArrayList<Race>();
    }

}
