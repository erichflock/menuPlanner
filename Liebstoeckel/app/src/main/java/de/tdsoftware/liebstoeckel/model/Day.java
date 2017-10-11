package de.tdsoftware.liebstoeckel.model;

import java.util.Date;
import java.util.List;

/**
 * Created by erich on 11/10/17.
 */

public class Day {

    String weekday;
    String openingHours;
    Date date;
    List<Dish> dishes;

    public Day(String weekday, String openingHours, Date date, List<Dish> dishes) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
