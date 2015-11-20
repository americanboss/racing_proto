package jp.co.equinestudio.racing;

/**
 *
 */
public interface ScheduleRaceListener {
    public void onScheduleSelected(final String scheduleCode);
    public void onRaceSelected(final String scheduleCode, final int racePosition);
}
