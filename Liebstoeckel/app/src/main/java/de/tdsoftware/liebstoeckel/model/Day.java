package de.tdsoftware.liebstoeckel.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erich on 11/10/17.
 */

public class Day implements Serializable {

    private String weekday;
    private String openingHours;
    private List<Dish> dishes;

    public Day(String weekday, String openingHours, List<Dish> dishes) {
        this.weekday = weekday.toUpperCase();
        this.openingHours = openingHours;
        this.dishes = dishes;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

}
