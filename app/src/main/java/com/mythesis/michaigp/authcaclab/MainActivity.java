package com.mythesis.michaigp.authcaclab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                    MyWebView.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private FragmentManager fragmentManager;

    public static final String PREFS_NAME = "MyPrefs";

    private static String language;

    protected String[] enSites = {"http://michaigp.webpages.auth.gr/project3/?q=en/home",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/19",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/33",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/22",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/24",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/25",
                                  "http://michaigp.webpages.auth.gr/project3/?q=en/node/32"};

    protected String[] elSites = {"http://michaigp.webpages.auth.gr/project3/?q=el/home",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/7",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/34",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/21",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/23",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/26",
                                  "http://michaigp.webpages.auth.gr/project3/?q=el/node/31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLang();
        sendBroadcast(new Intent(this, OnBootReceiver.class));
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().
                findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        fragmentManager = getSupportFragmentManager();
        Fragment myFragment;
        switch (position) {
            case 0:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1,(!language.equals("el"))? enSites[position]:elSites[position]), "0")//Home
                            .commit();
                }
                break;
            case 1:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));

                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el")) ? enSites[position] : elSites[position]), "1")//Presentation
                            .commit();
                }
                break;
            case 2:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el"))? enSites[position]:elSites[position]), "2")//Publications
                            .commit();
                }
                break;
            case 3:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el"))? enSites[position]:elSites[position]), "3")//Announcements
                            .commit();
                }
                break;
            case 4:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el"))? enSites[position] : elSites[position]), "4")//Research
                            .commit();
                }
                break;
            case 5:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el")) ? enSites[position] : elSites[position]), "5")//Courses
                            .commit();
                }
                break;
            case 6:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyWebView.newInstance(position + 1, (!language.equals("el")) ? enSites[position] : elSites[position]), "6")//People
                            .commit();
                }
                break;
            case 7:
                myFragment = fragmentManager.findFragmentByTag(Integer.toString(position));
                if (myFragment != null && myFragment.isVisible()) {
                    return;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, MyGoogleMap.newInstance(position + 1), "7")//Mail Address
                            .commit();
                }
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
        }
    }
    private void setLang(){

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        //If a language preference has been set
        if (preferences.contains("lang")) {
            String lang = preferences.getString("lang", null);

            if (lang!=null) {

                Resources res = getBaseContext().getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.locale = new Locale(lang.toLowerCase());
                Locale.setDefault(new Locale(lang.toLowerCase()));
                language = lang.toLowerCase();
                res.updateConfiguration(conf, dm);
            }
        }else{
            language = Locale.getDefault().getLanguage();
        }
    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}