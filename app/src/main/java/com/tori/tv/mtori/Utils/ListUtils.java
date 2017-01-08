package com.tori.tv.mtori.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tvaisanen on 8.1.2017.
 */

public class ListUtils {

    public static void setSearchSideMenuData(List<String> listDataHeader, HashMap<String, List<String>> listDataChild){

        // Adding child data
        String type = "none";
        listDataHeader.add("Ilmoitustyyppi");
        listDataHeader.add("Sijainti");
        listDataHeader.add("Osasto");

        // Adding child data
        List<String> types = new ArrayList<>();
        types.add("Myydään");
        types.add("Ostetaan");
        types.add("Vaihdetaan");
        types.add("Vuokrataan");
        types.add("Halutaan vuokrata");
        types.add("Muut");

        List<String> provinces = new ArrayList<>();
        provinces.add("Kaikki");
        provinces.add("Suomi");
        provinces.add("Ulkomaat");
        provinces.add("Ahvenanmaa");
        provinces.add("Etelä-Suomen lääni");
        provinces.add("Itä-Suomen lääni");
        provinces.add("Lapin lääni");
        provinces.add("Länsi-Suomen lääni");
        provinces.add("Oulun lääni");

        List<String> categories = new ArrayList<String>();
        categories.add("Akustiset kitarat");
        categories.add("Sähkökitarat");
        categories.add("Bassot");
        categories.add("Rummut");
        categories.add("Kosketinsoittimet");
        categories.add("Muu instrumentit");

        listDataChild.put(listDataHeader.get(0), types); // Header, Child data
        listDataChild.put(listDataHeader.get(1), provinces);
        listDataChild.put(listDataHeader.get(2), categories);
    }
}



