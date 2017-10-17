package de.tdsoftware.liebstoeckel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.model.Day;

/**
 * Created by erich on 11/10/17.
 */

public class DayAdapter extends BaseAdapter {

    private final Context context;
    private final List<Day> daysList;

    public DayAdapter(Context context, List<Day> daysList) {
        this.context = context;
        this.daysList = daysList;
    }

    @Override
    public int getCount() {
        return daysList.size();
    }

    @Override
    public Object getItem(int position) {
        return daysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Day day = daysList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if(view == null)
            view = inflater.inflate(R.layout.item_weekday, parent, false);

        /*
        Set day
         */

        TextView textViewDay = (TextView) view.findViewById(R.id.textView_menu_weekDay);
        textViewDay.setText(day.getWeekday());

        /*
        Set opening hours
         */

        TextView textViewOpeningHours = (TextView) view.findViewById(R.id.textView_menu_openingHours);
        textViewOpeningHours.setText(day.getOpeningHours());

        return view;
    }
}
