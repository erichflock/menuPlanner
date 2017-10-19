package de.tdsoftware.liebstoeckel.model;

import java.io.Serializable;

/**
 * Created by erich on 11/10/17.
 */

public class Dish implements Serializable{

    private long id;
    private String name;
    private String ingredients;
    private String price;

    public Dish(long id, String name, String ingredients, String price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

}
