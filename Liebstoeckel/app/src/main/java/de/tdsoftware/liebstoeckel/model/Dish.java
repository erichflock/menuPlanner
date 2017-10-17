package de.tdsoftware.liebstoeckel.model;

import java.io.Serializable;

/**
 * Created by erich on 11/10/17.
 */

public class Dish implements Serializable{
    private String name;
    private String ingredients;
    private String price;

    public Dish(String name, String ingredients, String price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getPrice() {
        return price;
    }

}
