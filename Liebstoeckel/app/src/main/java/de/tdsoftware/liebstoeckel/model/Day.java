package de.tdsoftware.liebstoeckel.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erich on 11/10/17.
 */

public class Day implements Serializable {

    String weekday;
    String openingHours;
    String date;
    List<Dish> dishes;

    public Day(String weekday, String openingHours, String date, List<Dish> dishes) {
        this.weekday = weekday;
        this.openingHours = openingHours;
        this.date = date;
        this.dishes = dishes;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
