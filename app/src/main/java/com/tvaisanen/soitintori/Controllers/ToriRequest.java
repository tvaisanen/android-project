package com.tvaisanen.soitintori.Controllers;

/**
 * Created by tvaisanen on 20.6.2016.
 */

import android.os.AsyncTask;

import com.tvaisanen.soitintori.Classes.Product;
import com.tvaisanen.soitintori.Tasks.HttpResponseTask;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToriRequest {

    // TODO: URI builder for search paramaters

    private static final Logger LOGGER = Logger.getLogger(ToriRequest.class.getName() );

    private HttpResponseTask response;
    private int pageCount;

    private static int productCount;

    public int getProductCount() {
        return productCount;
    }

    private static ArrayList<Product> products = new ArrayList<>();
    String keyword;

    public ToriRequest(String keyword_) {

        keyword = keyword_;
        response = new HttpResponseTask(keyword);
        Document searchDocument = null;
        try {
            searchDocument = response.execute(keyword).get();
        } catch (Exception ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        products = getProductsFromResponse(searchDocument);
    }


    public static ArrayList<Product> getProducts() {
        return products;
    }


    public static ArrayList<Product> getProductsFromResponse(Document testResponse) {

        // get product titleNodes and parse rest of the information based on "mainNode"


        Document response;
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            response = testResponse;// getHttpResponse("gretsch");

            // TODO: Parse page offset -> load all adds

            Elements bs = response.select("b");
            Pattern offsetPattern = Pattern.compile("(yhteensä.\\d+)");
            Matcher offsetMatches = offsetPattern.matcher(response.toString());
            ArrayList<String> offsets = new ArrayList<>();

            // TODO: Calculate page count from offset and find a smart way to implement page scrolling
            String foundFirst = null;
            String foundSecond = null;

            for (Element b : bs){

                if (foundFirst == null && foundSecond == null){

                    // populate ArrayList dates by regex matching
                    while (offsetMatches.find()) {
                        offsets.add(offsetMatches.group());
                    }

                    foundFirst = offsets.get(0);
                    foundFirst= foundFirst.split(" ")[1];
                    System.out.println("Parseint: " + foundFirst + " ilmoitusta");
                    try {
                        productCount = Integer.parseInt(foundFirst);
                        System.out.println("Product count: " + productCount);
                    } catch (Exception ex){
                        LOGGER.log(Level.WARNING, ex.toString(), ex);
                    }
                }
            }

            // System.out.println("foundFirst: " + foundFirst);
            // productCount = Integer.parseInt(foundFirst.split(" ")[1]);
            // System.out.println("Hunt for offset: " + b.last().parents().get(0).text());

            Elements titles = response.select("td[class=tori_title]");

            // scrape product information based on html title tag
            for (Element title : titles) {
                // System.out.println(title.toString());
                try {
                    products.add(collectProductInformation(title));
                } catch (Exception ex){
                    System.out.println("collectProductInformation(title) raised exception: ");
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                }
            }

        } catch (Exception ex) {
            System.out.println("Get products from response");
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        return products;
    }

    public static Product collectProductInformation(Element title) {

        Node titleParent = title.parent();
        Elements titleSiblings = title.siblingElements();
        Elements parentSiblings = title.parent().siblingElements();

        String userName = "not defined";
        String typeOfTrade = title.select("b").text();
        String userId = "not defined";
        String productTitle = title.select("a").text();
        String categoryName;
        String categoryId;
        String productUrl = title.select("a").attr("href");
        String productID = null;
        String description = null;
        String price = "";
        String location = null;
        String phoneNumber = null;
        String imageUrl = null;
        String email = null;
        String addedAndEditedStr = null;

        Hashtable<String, String> categories = new Hashtable<>();


        try {
            productID = productUrl.split("id=")[1];
        } catch (Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex );
        }

        try {
            addedAndEditedStr = titleSiblings.toString().split("title")[1];
        } catch (Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex );
        }


        // regex for date extraction
        Pattern datePattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
        Matcher dateMatches = datePattern.matcher(addedAndEditedStr);
        ArrayList<String> dates = new ArrayList<>();

        // populate ArrayList dates by regex matching
        while (dateMatches.find()) {
            dates.add(dateMatches.group());
        }

        String added;
        String edited;
        String expires;

        // if product edited dates.size() = 4;
        if (dates.size() > 3) {
            added = dates.get(0);
            edited = dates.get(1);
            expires = dates.get(3);
        } else {
            added = dates.get(0);
            edited = "not edited";
            expires = dates.get(2);
        }

        // user data

        Elements userData = parentSiblings.get(0).select("a[href*=/jasenet/]");

        try {
            userName = userData.get(0).text();
            String[] idData = userData.get(0).attr("href").split("/");
            userId = idData[idData.length - 1];
        } catch (Exception ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }

        for (Element sibling : parentSiblings) {

            Elements msgData = sibling.select("font[class*=msg]");


            if (msgData.size() > 0) {
                description = msgData.text();
            }

            // TODO: PARSE EMAIL!

            try {
                for (Element s : sibling.select("b")) {

                    /*
                    for (Element sib : s.siblingElements()) {
                        System.out.println(sib.select("b:contains(sähköposti)").text());
                    }
                    */

                    if (s.parent().text().startsWith("Hinta:")) {
                        price = s.parent().text().split(" ")[1];
                    }
                    if (s.parent().text().startsWith("Osasto:")) {
                        // System.out.println(s.parent());
                        String data = s.parent().text();
                        String splitters = "(Osasto:)|(Paikkakunta:)|(Puhelin:)";
                        String[] dataArray = data.split(splitters);
                        categoryName = dataArray[1];
                        location = dataArray[2];
                        phoneNumber = dataArray[3];

                        /*
                        System.out.println("arr: " + dataArray.length);
                        if (dataArray.length > 4) {
                            System.out.println(dataArray[4]);
                        }
                        */

                    }

                    /*
                    if (s.parent().text().startsWith("S�hk�posti:")) {
                        System.out.println("Email!!!");
                    }
                    */
                }
            } catch (Exception ex) {
                LOGGER.log( Level.FINE, ex.toString(), ex );
            }

            try {
                imageUrl = sibling.select("a[id*=tori_image_link]").get(0).attr("href");
            } catch (Exception ex) {
                LOGGER.log( Level.FINE, ex.toString(), ex );
            }

            try {
                Elements categoryData = sibling.select("a[href*=category=]");

                if (categoryData.size() > 0) {
                    for (Element category : categoryData) {
                        String id = category.toString().split("category=")[1].split("\"")[0];
                        String name = category.text();
                        categories.put(id, name);
                    }
                }
            } catch (Exception ex) {
                LOGGER.log( Level.FINE, ex.toString(), ex );
            }
        }

        /*
        System.out.println("userName: " + userName);
        System.out.println("userId: " + userId);
        System.out.println("type: " + typeOfTrade);
        System.out.println("title: " + productTitle);
        System.out.println("url: " + productUrl);
        System.out.println("id: " + productID);
        System.out.println("added: " + added);
        System.out.println("edited: " + edited);
        System.out.println("expires: " + expires);
        System.out.println("description: " + description);
        System.out.println("price: " + price);
        System.out.println("location: " + location);
        System.out.println("phone: " + phoneNumber);
        System.out.println("imageUrl: " + imageUrl);
        */


        return new Product(productID, typeOfTrade, productTitle, productUrl,
                           imageUrl, added, expires, edited, userId, userName,
                           description, location, price, phoneNumber);

    }

    public boolean isResponseReady(){
        if (response.getStatus() == AsyncTask.Status.FINISHED){
            return true;
        } else {
            return false;
        }
    }

}
