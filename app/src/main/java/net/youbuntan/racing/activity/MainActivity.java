package net.youbuntan.racing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.youbuntan.racing.R;
import net.youbuntan.racing.logic.AssetsLogic;
import net.youbuntan.racing.model.Schedules;
import net.youbuntan.racing.view.schedule.ScheduleView;

import java.lang.reflect.Type;


public class MainActivity extends Activity {

    private LinearLayout mScheduleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScheduleContainer = (LinearLayout) findViewById(R.id.schedule_container);

        LayoutInflater inflater = LayoutInflater.from(this);


        //開催予定をロードする
        String scheduleJson = AssetsLogic.getStringAsset(this, "schedule/schedule.test.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<Schedules>() { }. getType();
        Schedules schedules = gson.fromJson(scheduleJson, listType);

        // 日付ごとに処理
        for (Schedules.ScheduleDate scheduleDate : schedules.getScheduleDates()) {
            LinearLayout scheduleRow = (LinearLayout) inflater.inflate(R.layout.parts_schedule_selecter_row, null);
            ScheduleView columnA = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_a);
            ScheduleView columnB = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_b);
            ScheduleView columnC = (ScheduleView) scheduleRow.findViewById(R.id.schedule_container_column_c);

            // 開催場の数で見えなくなるカラムの設定
            if (scheduleDate.getSchedules().size() < 3) {
                columnC.setVisibility(View.GONE);
            }
            if (scheduleDate.getSchedules().size() < 2) {
                columnB.setVisibility(View.GONE);
            }

            int count = 0;
            for (Schedules.Schedule schedule : scheduleDate.getSchedules()) {
                switch (count) {
                    case 0 : columnA.setSchedule(schedule); break;
                    case 1 : columnB.setSchedule(schedule); break;
                    case 2 : columnC.setSchedule(schedule); break;
                }
                count++;
            }
            mScheduleContainer.addView(scheduleRow);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
