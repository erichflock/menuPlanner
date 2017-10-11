package de.tdsoftware.liebstoeckel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.tdsoftware.liebstoeckel.R;
import de.tdsoftware.liebstoeckel.model.Dish;

/**
 * Created by erich on 11/10/17.
 */

public class DishesAdapter extends BaseAdapter {

    private final Context context;
    private final List<Dish> dishesList;

    public DishesAdapter(Context context, List<Dish> dishList) {
        this.context = context;
        this.dishesList = dishList;
    }

    @Override
    public int getCount() {
        return dishesList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Dish dish = dishesList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if(view == null)
            view = inflater.inflate(R.layout.item_dishes, parent, false);


        /*
        Set the name of the dish. Usually this name is TAGESGERICHT FLEISCH/ FISCH, TAGESGERICHT VEGETARISCH / FISCH or TAGESSUPPE
         */

        TextView option = (TextView) view.findViewById(R.id.textView_dishes_option);
        option.setText(dish.getName());

        /*
        Set the ingredients of the dish
         */

        TextView ingredients = (TextView) view.findViewById(R.id.textView_dishes_ingredients);
        ingredients.setText(dish.getIngredients());

        /*
        Set the price
         */

        TextView price = (TextView) view.findViewById(R.id.textView_dishes_price);
        price.setText(dish.getPrice());

        return view;
    }
}
