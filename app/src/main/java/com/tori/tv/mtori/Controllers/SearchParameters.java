package com.tori.tv.mtori.Controllers;

import android.net.Uri;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tvaisanen on 20.7.2016.
 */
public class SearchParameters {

    // TODO: Build url building with uri builder

    // "https://muusikoiden.net/tori/haku.php?keyword="
    private static final Logger LOGGER = Logger.getLogger(SearchParameters.class.getName() );
    public String[] keywords;

    public int pageOffset;
    private HashMap<String, String> hmTypes;
    private HashMap<String, String> hmProvinces;
    private HashMap<String, String> hmCategories;


    private String selectedType;
    private String selectedProvince;
    private String selectedCategory;

    public SearchParameters(){

        hmTypes = new HashMap<>();
        hmProvinces = new HashMap<>();
        hmCategories = new HashMap<>();

        selectedType = "";
        selectedProvince = "";
        selectedCategory = "";
        initLists();
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public String getSelectedType() {
        LOGGER.log(Level.FINE, "searchParameters.getSelectedType()");
        LOGGER.log(Level.FINE, this.selectedType);
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = getTypeParameter(selectedType);
        LOGGER.log(Level.FINE, "searchParameters.setSelectedType()");
        LOGGER.log(Level.FINE, this.selectedType);
    }

    public String getSelectedProvince() {
        LOGGER.log(Level.FINE, "searchParameters.getSelectedProvince()");
        LOGGER.log(Level.FINE, this.selectedProvince);
        return selectedProvince;
    }

    public void setSelectedProvince(String selectedProvince) {
        this.selectedProvince = getProvinceParameter(selectedProvince);
        LOGGER.log(Level.FINE, "searchParameters.getSelectedProvince()");
        LOGGER.log(Level.FINE, this.selectedProvince);
    }

    public String getSelectedCategory() {
        LOGGER.log(Level.FINE, "searchParameters.getSelectedCategory()");
        LOGGER.log(Level.FINE, this.selectedCategory);
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = getCategoryParameter(selectedCategory);
        LOGGER.log(Level.FINE, "searchParameters.getSelectedCategory()");
        LOGGER.log(Level.FINE, this.selectedCategory);
    }

    private String getTypeParameter(String key){
        return hmTypes.get(key);
    }

    private String getProvinceParameter(String key){
        return hmProvinces.get(key);
    }

    private String getCategoryParameter(String key) { return hmCategories.get(key); }

    private void initLists() {

        // TODO: init from file?

        hmTypes.put("Myydään", "type=sell");
        hmTypes.put("Ostetaan", "type=buy");
        hmTypes.put("Vaihdetaan", "type=exchange");
        hmTypes.put("Vuokrataan", "type=for_rent");
        hmTypes.put("Halutaan vuokrata", "type=want_rent");
        hmTypes.put("Muut", "type=other");

        hmProvinces.put("Kaikki", "location=all");
        hmProvinces.put("Suomi", "location=suomi");
        hmProvinces.put("Ulkomaat", "location=other");
        hmProvinces.put("Ahvenanmaa", "location=Ahvenanmaa");
        hmProvinces.put("Etelä-Suomen lääni", "location=Etel%E4-Suomen+l%E4%E4ni");
        hmProvinces.put("Itä-Suomen lääni", "location=It%E4-Suomen+l%E4%E4ni");
        hmProvinces.put("Lapin lääni", "location=Lapin+l%E4%E4ni");
        hmProvinces.put("Länsi-Suomen lääni", "location=L%E4nsi-Suomen+l%E4%E4ni");
        hmProvinces.put("Oulun lääni", "location=Oulun+l%E4%E4ni");

        hmCategories.put("Akustiset kitarat", "1");
        hmCategories.put("Sähkökitarat", "8");
        hmCategories.put("Bassot", "13");
        hmCategories.put("Rummut", "18");
        hmCategories.put("Kosketinsoittimet", "28");
        hmCategories.put("Muu instrumentit", "33");

    }
}
