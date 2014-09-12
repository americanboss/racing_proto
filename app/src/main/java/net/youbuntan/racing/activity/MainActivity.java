package net.youbuntan.racing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import net.youbuntan.racing.R;


public class MainActivity extends Activity {

    private LinearLayout mScheduleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScheduleContainer = (LinearLayout) findViewById(R.id.schedule_container);

        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.parts_schedule_selecter_row, null);
        LinearLayout columnA = (LinearLayout) row.findViewById(R.id.schedule_container_column_a);
        LinearLayout columnB = (LinearLayout) row.findViewById(R.id.schedule_container_column_a);
        LinearLayout columnC = (LinearLayout) row.findViewById(R.id.schedule_container_column_a);

        mScheduleContainer.addView(row);



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
