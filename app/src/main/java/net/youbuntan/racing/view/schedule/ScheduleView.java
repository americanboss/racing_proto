package net.youbuntan.racing.view.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.youbuntan.racing.R;
import net.youbuntan.racing.model.Schedules;

/**
 */
public class ScheduleView extends LinearLayout {

    private TextView mYearText;

    public ScheduleView(final Context context) {
        this(context, null);
    }

    public ScheduleView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init () {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_schedule, this);
        mYearText = (TextView) view.findViewById(R.id.year);
    }

    public void setSchedule (Schedules.Schedule schedule) {
        mYearText.setText(schedule.getYear());
    }

}
