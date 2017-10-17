package de.tdsoftware.liebstoeckel.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erich on 14/10/17.
 */

public class Week implements Serializable {

    private String period;
    private List<Day> days;

    public Week(String period, List<Day> days) {
        this.period = period;
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public Day getDay(String weekday){

        for (Day day:
             days) {
            if(day.getWeekday().equals(weekday)){
                return day;
            }
        }
        return null;
    }
}
