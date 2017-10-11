package de.tdsoftware.liebstoeckel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.adapter.DishesAdapter;
import de.tdsoftware.liebstoeckel.model.Dish;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DishesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DishesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DishesFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listViewDishes;
    private View view;

    public DishesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DishesFragment newInstance(String param1, String param2) {
        DishesFragment fragment = new DishesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dishes, container, false);
        listViewDishes = (ListView) view.findViewById(R.id.listView_dishes);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {

        super.onResume();

        /*
        The inflation of the dishes list will be set here
         */

        /*
        At this step, the day's dishes should be known.
         */

        /*
        Fake dishes created in order to test the adpater
         */

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

        DishesAdapter dishesAdapter = new DishesAdapter(this.getContext(), dishes);

        //ArrayAdapter<Dish> adapter = new ArrayAdapter<Dish>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, dishes);

        listViewDishes.setAdapter(dishesAdapter);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
