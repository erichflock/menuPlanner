package de.tdsoftware.liebstoeckel.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import de.tdsoftware.liebstoeckel.model.Day;
import de.tdsoftware.liebstoeckel.model.Dish;
import de.tdsoftware.liebstoeckel.model.Menu;
import de.tdsoftware.liebstoeckel.model.Week;

/**
 * Created by erich on 18/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "liebstoeckel", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        Creating tables WEEK, DAY and DISH
         */

        db.execSQL("CREATE TABLE week (period TEXT, day_id INTEGER);");
        db.execSQL("CREATE TABLE day (day_id INTEGER, weekday TEXT, opening_hours TEXT, dish_id);");
        db.execSQL("CREATE TABLE dish (dish_id INTEGER, dish TEXT, ingredients TEXT, price TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS week");
        db.execSQL("DROP TABLE IF EXISTS day");
        db.execSQL("DROP TABLE IF EXISTS dish");

        // create new tables
        onCreate(db);

    }

    public void insert(Menu menu) {
        SQLiteDatabase db = getWritableDatabase();

        /*
        Inserting values in table week
        period TEXT, day_id INTEGER
         */
        for (Day day :
                menu.getWeek().getDays()) {
            ContentValues values = new ContentValues();
            values.put("period", menu.getWeek().getPeriod());
            values.put("day_id", day.getId());
            db.insert("week", null, values);
        }

        /*
        Inserting values in table day
        day_id INTEGER, weekday TEXT, opening_hours TEXT, dish_id
         */
        for (Day day :
                menu.getWeek().getDays()) {

            long id = day.getId();
            String weekday = day.getWeekday();
            String opening_hours = day.getOpeningHours();

            for (Dish dish :
                    day.getDishes()) {
                ContentValues values = new ContentValues();
                values.put("day_id", id);
                values.put("weekday", weekday);
                values.put("opening_hours", opening_hours);
                values.put("dish_id", dish.getId());
                db.insert("day", null, values);
            }
        }

        /*
        Inserting values in table dish
        dish_id INTEGER, dish TEXT, ingredients TEXT, price TEXT
         */
        for (Day day :
                menu.getWeek().getDays()) {
            for (Dish dish :
                    day.getDishes()) {
                ContentValues values = new ContentValues();
                values.put("dish_id", dish.getId());
                values.put("dish", dish.getName());
                values.put("ingredients", dish.getIngredients());
                values.put("price", dish.getPrice());
                db.insert("dish", null, values);
            }
        }
        db.close();
    }

    public Menu getMenu() {
        Menu menu = null;
        SQLiteDatabase db = getReadableDatabase();

        if(isDBEmpty(db))
            return null;

        //get dishes
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM dish;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {

            //dish_id INTEGER, dish TEXT, ingredients TEXT, price TEXT

            long id = c.getLong(c.getColumnIndex("dish_id"));
            String name = c.getString(c.getColumnIndex("dish"));
            String ingredients = c.getString(c.getColumnIndex("ingredients"));
            String price = c.getString(c.getColumnIndex("price"));
            //long id, String name, String ingredients, String price
            Dish dish = new Dish(id, name, ingredients, price);
            dishes.add(dish);
        }
        c.close();

        //get days
        List<Day> days = new ArrayList<>();
        sql = "SELECT DISTINCT day_id, weekday, opening_hours FROM day;";
        c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            //day_id INTEGER, day TEXT, opening_hours TEXT, dish_id
            long id = c.getLong(c.getColumnIndex("day_id"));
            String weekDay = c.getString(c.getColumnIndex("weekday"));
            String openingHours = c.getString(c.getColumnIndex("opening_hours"));
            Day day = new Day(id, weekDay, openingHours, null);
            days.add(day);
        }
        c.close();

        //Adding dishes to the days
        for (Day day :
                days) {
            sql = "SELECT dish_id FROM day WHERE weekday = ?";
            c = db.rawQuery(sql, new String[]{day.getWeekday()});
            List<Dish> dishes_temp = new ArrayList<>();
            while (c.moveToNext()) {
                long dish_id = c.getLong(c.getColumnIndex("dish_id"));
                for (Dish dish :
                        dishes) {
                    if (dish.getId() == dish_id) {
                        dishes_temp.add(dish);
                    }
                }
            }
            day.setDishes(dishes_temp);
        }

        //get week
        sql = "SELECT period FROM week;";
        c = db.rawQuery(sql, null);
        Week week = null;
        while (c.moveToNext()) {
            String period = c.getString(c.getColumnIndex("period"));
            week = new Week(period, days);
        }
        c.close();

        menu = new Menu(week);
        db.close();
        return menu;
    }

    private boolean isDBEmpty(SQLiteDatabase db) {
        //check if the database is empty
        String sql = "SELECT * FROM week;";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0)
            return false;
        else
            return true;
    }

    public void deleteValues() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM week;");
        db.execSQL("DELETE FROM day;");
        db.execSQL("DELETE FROM dish;");
        db.close();
    }

    public String getWeekPeriod() {
        String period = "NO DATA";
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT DISTINCT period FROM week;";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            period = c.getString(c.getColumnIndex("period"));
        }
        db.close();
        return period;
    }
}
