package de.tdsoftware.liebstoeckel.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.adapter.DayAdapter;
import de.tdsoftware.liebstoeckel.model.Day;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends android.support.v4.app.Fragment implements DishesFragment.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;
    private ListView listViewWeekDays;

    /*
    Variables used in order to be able to change fragments
     */

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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

        view = inflater.inflate(R.layout.fragment_menu, container, false);

        listViewWeekDays = (ListView) view.findViewById(R.id.listView_weekDay);

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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        Create the days and then add them to the list that will be sent to the adapter
         */

        /*
        Parameters: Day (String), Opening Hours (String), Date (Date), Dishes (List<Dish - usually 3 dishes)
         */

        String date = "16.10.17";
        Day monday = new Day("Montag", "VON 07:30Uhr BIS 17:00Uhr", date, null);
        date = "17.10.17";
        Day tuesday = new Day("Dienstag", "VON 07:30Uhr BIS 17:00Uhr", date, null);
         date = "18.10.17";
        Day wednesday = new Day("Mittwoch", "VON 07:30Uhr BIS 17:00Uhr", date, null);
        date = "19.10.17";
        Day thursday = new Day("Donnerstag", "VON 07:30Uhr BIS 17:00Uhr", date, null);
        date = "20.10.17";
        Day friday = new Day("Freitag", "VON 07:30Uhr BIS 17:00Uhr", date, null);
        date = "21.10.17";
        Day saturday = new Day("Samstag", "       Geschlossen       ", date, null);
        date = "22.10.17";
        Day sunday = new Day("Sontag", "       Geschlossen       ", date, null);
        List<Day> daysList = new ArrayList<>();
        daysList.add(monday);
        daysList.add(tuesday);
        daysList.add(wednesday);
        daysList.add(thursday);
        daysList.add(friday);
        daysList.add(saturday);
        daysList.add(sunday);

        DayAdapter dayAdapter = new DayAdapter(this.getContext(), daysList);
        listViewWeekDays.setAdapter(dayAdapter);

        /*
        After loading the content of the ListView, let's create a listener.
        When the user selects one weekday, the fragment dishes is opened displaying the dishes of the selected day.
         */

        listViewWeekDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                /*
                At this point, it's important to send the day object to the dishes fragment.
                 */
                Day selectedDay = (Day) list.getItemAtPosition(position);
                fragmentManager = getActivity().getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, new DishesFragment().newInstance(selectedDay,null)).commit();
            }
        });

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
