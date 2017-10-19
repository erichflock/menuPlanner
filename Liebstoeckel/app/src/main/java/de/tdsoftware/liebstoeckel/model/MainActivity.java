package de.tdsoftware.liebstoeckel.model;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.dao.DBHelper;
import de.tdsoftware.liebstoeckel.fragments.ContactFragment;
import de.tdsoftware.liebstoeckel.fragments.DishesFragment;
import de.tdsoftware.liebstoeckel.fragments.MenuFragment;
import de.tdsoftware.liebstoeckel.web.LoadModelTaskCallback;
import de.tdsoftware.liebstoeckel.web.LoadModelTask;

public class MainActivity extends AppCompatActivity implements ContactFragment.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener, DishesFragment.OnFragmentInteractionListener {

    /*
    Variables created in order to be possible to switch between fragments
     */
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;

    LoadModelTask loadModelTask;

    Menu menu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*
                Dishes
                 */
                case R.id.navigation_todayDishes:
                    startDishFragment();
                    return true;
                /*
                Menu
                 */
                case R.id.navigation_menu:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, new MenuFragment().newInstance(menu.getWeek(), null)).commit();
                    return true;
                /*
                Contact
                 */
                case R.id.navigation_contact:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, new ContactFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadModelTask = new LoadModelTask("http://www.liebstoeckel-tagesbar.de/mittagskarte/");

        loadModelTask.execute();
        loadModelTask.setCallback(new LoadModelTaskCallback() {
            @Override
            public void onModelLoaded(Menu result) {

                /*
                If the result is null, it means that there is no internet connection. In this case, the app should get the data from the database.
                 */
                menu = result;
                DBHelper dbHelper = new DBHelper(MainActivity.this);

                if (menu == null) {
                    Log.d("MainActivity", "No Internet Connection");
                    menu = dbHelper.getMenu();
                    if(menu == null){
                        /*
                        database is empty. No information can be displayed to the user. Application will be closed.
                         */
                        Log.d("MainActivity", "DB is empty");
                        Toast.makeText(MainActivity.this, "No internet connection and database empty. No information can be displayed. Try again later.", Toast.LENGTH_SHORT).show();
                        //close application after 2000 ms
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.this.finish();
                            }
                        }, 2000);
                    }else{
                        startDishFragment();
                        initializeBottomNav();
                    }
                } else {
                    //if the data in the database is outdated or empty. The database should be updated.
                    if (!menu.getWeek().getPeriod().trim().equals(dbHelper.getWeekPeriod().trim())) { //if there is no data in the week table, the method getWeekPeriod return the string NO DATA
                        Log.d("MainActivity", "DB empty or outdated");
                        dbHelper.deleteValues();
                        dbHelper.insert(menu);
                    }
                    startDishFragment();
                    initializeBottomNav();
                }

            }
        });

    }

    private void initializeBottomNav() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void startDishFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new DishesFragment().newInstance(menu.getWeek().getDay(getCurrentDay()), null)).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private String getCurrentDay() {

        //get the current date
        Calendar c = Calendar.getInstance();

        //create formattedDate in order to display the day correctly
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.GERMANY);
        weekDay = dayFormat.format(c.getTime());
        return weekDay.toUpperCase();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
