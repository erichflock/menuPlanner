package de.tdsoftware.liebstoeckel.model;

/**
 * Created by erich on 11/10/17.
 */

public class Dish {
    String name;
    String ingredients;
    String price;

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

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
