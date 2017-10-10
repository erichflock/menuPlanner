package de.tdsoftware.liebstoeckel.model;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
                    transaction.replace(R.id.content, new DishesFragment()).commit();
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
        Start the app with the dishes fragment, displaying the dishes of today.
         */

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new DishesFragment()).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
