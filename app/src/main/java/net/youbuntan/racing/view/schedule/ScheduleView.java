package net.youbuntan.racing.view.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.youbuntan.racing.R;
import net.youbuntan.racing.model.Schedules;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 */
public class ScheduleView extends LinearLayout implements View.OnClickListener {


    @Accessors(prefix = "m") @Setter
    private OnScheduleSelectedListener mListener;

    private LinearLayout mViewContainer;
    private TextView mYearText;

    private Schedules.Schedule mSchedule;

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
        mViewContainer = (LinearLayout) view.findViewById(R.id.view_container);
        mYearText = (TextView) view.findViewById(R.id.year);

        mViewContainer.setOnClickListener(this);
    }

    public void setSchedule (Schedules.Schedule schedule) {
        mSchedule = schedule;

        mYearText.setText(schedule.getYear());
    }

    public interface OnScheduleSelectedListener {
        void onScheduleSelect (String scheduleCode);
    }

    @Override
    public void onClick(final View v) {
        if (mListener != null) {
            mListener.onScheduleSelect(mSchedule.getCode());
        }
    }
}
