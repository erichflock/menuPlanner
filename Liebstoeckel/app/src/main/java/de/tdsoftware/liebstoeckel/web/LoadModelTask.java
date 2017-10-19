package de.tdsoftware.liebstoeckel.web;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tdsoftware.liebstoeckel.model.Day;
import de.tdsoftware.liebstoeckel.model.Dish;
import de.tdsoftware.liebstoeckel.model.Menu;
import de.tdsoftware.liebstoeckel.model.Week;

/**
 * Created by erich on 14/10/17.
 */

public class LoadModelTask extends AsyncTask<Void, Void, Menu> {

    String page;

    /*
    IDs
     */
    long day_id = 0, dish_id = 0;

    private static final String ns = null;

    //Variables of JSOUP: ths contains the th tags and tds contains the td tags of the HTML page
    Elements ths, tds;

    LoadModelTaskCallback callback;

    public LoadModelTask(String page) {
        this.page = page;
    }

    @Override
    protected void onPostExecute(Menu menu) {
        super.onPostExecute(menu);
        getCallback().onModelLoaded(menu);
    }

    public LoadModelTaskCallback getCallback() {
        return callback;
    }

    public void setCallback(LoadModelTaskCallback callback) {
        this.callback = callback;
    }

    /*
    Parsing HTML with JSOUP: https://jsoup.org/
    Code based on: https://www.survivingwithandroid.com/2014/04/parsing-html-in-android-with-jsoup-2.html
     */

    @Override
    protected Menu doInBackground(Void... params) {

        /*
        Check internet connection
         */

        if(!checkConnection())
            return null;

        //contains the week period and the name of the dishes
        /*
        D/Tag: th
        D/Size of elements: 4
        D/Element 0 :: WOCHE VOM 16. Okt – 22. Okt
        D/Element 1 :: TAGESGERICHT FLEISCH/ FISCH
        D/Element 2 :: TAGESGERICHT VEGETARISCH / FISCH
        D/Element 3 :: TAGESSUPPE
        */
        ths = getTag("th");

        //contains the days, opening hours and ingredients
        /*
        D/Tag: td
        Size of elements: 28
        D/Element 0 :: MONTAG VON 07:30Uhr BIS 17:00Uhr
        D/Element 1 :: Geflügelleber in Sherry-Senf-Soße mit Äpfeln, Cranberries und Möhren, dazu Kartoffelstampf mit Röstzwiebeln 6,50€
        D/Element 2 :: Grillpaprika, dazu Rote Linsen-Risotto mit frischem Brokkoli 6,90€
        D/Element 3 :: BioKürbiscremesuppe mit gerösteten Kürbiskernen (Wochensuppe) 3,90€
        D/Element 4 :: Dienstag VON 07:30Uhr BIS 17:00Uhr
        D/Element 5 :: Rinderschmorbraten OWB, dazu rote BioKartoffeln und Brokkoli-Möhren-Püree 6,90€
        D/Element 6 :: Dampfkartoffeln mit Balsamicoquark, frischen Kräutern und Oliven 6,50€
        D/Element 7 :: Nudeleintopf mit frischem Wurzelgemüse und Muscheln 4,90€
        D/Element 8 :: Mittwoch VON 07:30Uhr BIS 17:00Uhr
        D/Element 9 :: Hirschgulasch, dazu Bio- Kürbis, Waldpilze, Hefeknödel, Balsamico-Kräuter-Schmand 7,50€
        D/Element 10 :: Zucchini mit Basilikumpesto gratiniert, BioKartoffel-Kürbis- Pfanne mit getr. Tomaten 6,90€
        D/Element 11 :: BioRote Bete-Cremesuppe mit grünem Apfel und Meerrettich 3,90€
        D/Element 12 :: DONNERSTAG VON 07:30Uhr BIS 17:00Uhr
        D/Element 13 :: Wildschweinbratwurst, dazu Kartoffel-Senf-Püree mit Spinat, BioMöhrengemüse 6,50€
        D/Element 14 :: Nudeln mit Kräuterrahm- Champignons, Weintrauben, roter Paprika und Parmesan 6,90€
        D/Element 15 :: Süßkartoffel-Zitronen- Cremesuppe mit Ingwer und frischem Spinat 3,90€
        D/Element 16 :: FREITAG VON 7:30Uhr BIS 16:00Uhr
        D/Element 17 :: Zanderfilet mit Petersiliensoße, roter Bete und Salzkartoffeln 6,90€
        D/Element 18 :: Perlgraupen-Steinpilz-Risotto, blauer BioKürbis mit gelben Möhren und Walnusspesto 6,90€
        D/Element 20 :: SONNABEND
        D/Element 21 :: GESCHLOSSEN
        D/Element 22 :: GESCHLOSSEN
        D/Element 23 :: GESCHLOSSEN
        D/Element 24 :: SONNTAG
        D/Element 25 :: GESCHLOSSEN
        D/Element 26 :: GESCHLOSSEN
        D/Element 27 :: GESCHLOSSEN
         */
        tds = getTag("td");
        String weekPeriod = ths.get(0).text();
        Week week = new Week(weekPeriod, createDays());
        Menu menu = new Menu(week);
        return menu;
    }

    private Elements getTag(String tag) {
        Elements elems = null;
        try {
            Log.d("JSOUP", "Connecting to ["+page+"]");
            Document doc = Jsoup.connect(page).get();
            Log.d("JSOUP", "Connected to ["+page+"]");

            Log.d("Tag", tag);
            // Get info
            elems = doc.select(tag);
            Log.d("Size of elements", ""+elems.size());
            int count = 0;
            for (Element elem : elems) {
                String name = elem.text();
                Log.d("Element "+count+" : ", name);
                count++;
            }
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        return elems;
    }

    /*
    Help functions to deal with strings
     */

    private static String extractWeekDay(String text){
        /*
        Weekday will be the first element of the array
         */

        String[] strings = text.split(" ");

        return strings[0];
    }

    private static String extractOpeningHours(String text){

        String[] strings = text.split(" ");
        String openingHours = "";
        for(int i = 1; i < strings.length; i++){
            openingHours = openingHours + strings[i] + " ";
        }

        if(openingHours == "")
            openingHours = "Geschlossen";

        return openingHours.trim();
    }

    private static String extractPrice(String text){

        if(text.isEmpty() || text.contains("GESCHLOSSEN"))
            return null;

        char[] price = new char[5];
        text.getChars(text.length()-5, text.length(), price, 0);
        String priceString = "";
        for(int i = 0; i < price.length; i++){
            priceString = priceString + price[i];
        }
        return priceString;
    }

    private static String extractIngredients(String text){
        String ingredients = "";
        for(int i = 0; i < text.length() - 5; i++){
            ingredients = ingredients + text.charAt(i);
        }
        return ingredients;
    }

    private Dish createDish(int indexTH, int indexTD){
        dish_id++;
        return new Dish(dish_id, ths.get(indexTH).text(), extractIngredients(tds.get(indexTD).text()), extractPrice(tds.get(indexTD).text()));
    }

    /*
    This code was developed based on the pattern identified in the lists of elements tds and ths
     */

    private List<Day> createDays(){

        List<Day> days = new ArrayList<>();

        for(int i = 0; i < tds.size(); i = i+ 4){
            List<Dish> dishes = new ArrayList<>();
            /*
            Create 3 dishes
             */
            for(int l = 1; l <=3; l++){
                dishes.add(createDish(l,i+l));
            }
            /*
            Create a day and add it to a list. If the restaurant is closed, the day won't be added to the list
             */
            day_id++;
            Day day = new Day(day_id,extractWeekDay(tds.get(i).text()), extractOpeningHours(tds.get(i).text()), dishes);
            if(!day.getOpeningHours().toUpperCase().equals("GESCHLOSSEN"))
                days.add(day);
        }
        return days;
    }

    private boolean checkConnection(){
        try {
            Document document = Jsoup.connect(page).get();
            if(document.hasText()){
                Log.d("Internet Connection", "OK");
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Internet Connection", "NO");
        return false;
    }
}
