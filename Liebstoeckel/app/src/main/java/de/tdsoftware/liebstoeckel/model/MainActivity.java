package de.tdsoftware.liebstoeckel.model;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.fragments.ContactFragment;
import de.tdsoftware.liebstoeckel.fragments.DishesFragment;
import de.tdsoftware.liebstoeckel.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity implements ContactFragment.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener, DishesFragment.OnFragmentInteractionListener {

    /*
    Variables created in order to be possible to switch between fragments
     */
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*
                Dishes
                 */
                case R.id.navigation_todayDishes:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, new DishesFragment().newInstance(getDay(),null)).commit();
                    return true;
                /*
                Menu
                 */
                case R.id.navigation_menu:
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, new MenuFragment()).commit();
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

        /*
        Start the app with the dishes fragment, displaying the dishes of today. At this step, we should also be able to pass a Day object with all the content populated.
         */

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new DishesFragment().newInstance(getDay(),null)).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private String getWeekCurrentDay(){

        //get the current date
        Calendar c = Calendar.getInstance();

        //create formattedDate in order to display the day correctly
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.GERMANY);
        weekDay = dayFormat.format(c.getTime());
        return weekDay;
    }

    private String getCurrentDate(){

        //get the current date
        Calendar c = Calendar.getInstance();
        //create formattedDate in order to display the date correctly
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(c.getTime());

        return date;
    }

    private List<Dish> getDishes(){
        List<Dish> dishes = new ArrayList<>();

        /*
        Create and add the dishes to the list
         */
        Dish dish1 = new Dish("TAGESGERICHT FLEISCH/ FISCH", "Wolfsbarschfilet, dazu Quitten-Ingwer-Chutney, Kartoffel-Möhren-Püree, Spinat", "6,90€");
        Dish dish2 = new Dish("TAGESGERICHT VEGETARISCH / FISCH", "Frischer Blumenkohl mit Zucchini, Kartoffelstampf und Sauce Hollandaise", "6,90€");
        Dish dish3 = new Dish("TAGESSUPPE", "Linseneintopf mit rote Bete, Orange, Zucchini und Möhre", "3,90€");
        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        return dishes;
    }

    private Day getDay(){
        Day day = new Day(getWeekCurrentDay(), "VON 07:30Uhr BIS 17:00Uhr", getCurrentDate(), getDishes());
        return day;
    }
}
