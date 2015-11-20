package jp.co.equinestudio.racing.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import jp.co.equinestudio.racing.R;
import jp.co.equinestudio.racing.adapter.DrawerMenuListAdapter;
import jp.co.equinestudio.racing.fragment.HomeFragment;
import jp.co.equinestudio.racing.model.DrawerMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private LinearLayout mContainer;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawer;

    private LinearLayout mLeftDrawer;
    private LinearLayout mDrawerHeadContainer;

    private ListView mDrawerMenuList;
    private DrawerMenuListAdapter mDrawerMenuListAdapter;
    List<DrawerMenuItem> mDrawerMenuItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLeftDrawer = (LinearLayout) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerMenuList = (ListView) findViewById(R.id.eqs_drawer_menu_list);
        mDrawerHeadContainer = (LinearLayout) findViewById(R.id.eqs_drawer_head_container);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                             /* host Activity */
                mDrawer,                    /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.dialog_cancel  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // Defer code dependent on restoration of previous instance state.
        // NB: required for the drawer indicator to show up!
        mDrawer.setDrawerListener(mDrawerToggle);

        mDrawerMenuListAdapter = new DrawerMenuListAdapter(this);
        mDrawerMenuList.setAdapter(mDrawerMenuListAdapter);
        mDrawerMenuList.setOnItemClickListener(mDrawerMenuItemClickListener);

        mDrawerMenuItems = new ArrayList<>();

        replaceInformationFragment();

    }

    private AdapterView.OnItemClickListener mDrawerMenuItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            switch (mDrawerMenuItems.get(position).getId()) {
//                case DrawerMenuItem.ID_MAP:
//                    replaceLocationMapFragment(LocationMapFragment.MOVE_TO_PLACE_NONE);
//                    break;
            }

        }
    };

    protected void replaceInformationFragment () {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment informationFragment = HomeFragment.getNewInstance();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_container, informationFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        if (mDrawer.isDrawerOpen(mLeftDrawer)) {
            mDrawer.closeDrawer(mLeftDrawer);
        }
    }

    private void openDrawer() {
        if (!mDrawer.isDrawerOpen(mLeftDrawer)) {
            mDrawer.openDrawer(mLeftDrawer);
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
