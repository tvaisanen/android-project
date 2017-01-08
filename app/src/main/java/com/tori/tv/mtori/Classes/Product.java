package com.tori.tv.mtori.Classes;

import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.Hashtable;

public class Product implements Serializable {

    ProgressBar pb;
    String type;
    String url;
    String id;
    String title;
    String imageUrl;
    String imagePath;
    String description;
    String userId;
    String userName;
    String dateAdded;
    String dateLastEdited;
    String dateExpires;
    String location;
    String price;
    String phoneNumber;

    Hashtable<String, String> categories;

    public Product(String title_, String description_){
        title = title_;
        description = description_;
    }

    public Product(){}

    public Product(String id_, String type_, String title_, String url_,
            String imageUrl_, String dateAdded_, String dateExpires_,
            String dateEdited_, String userId_, String userName_,
            String description_, String location_, String price_, String phoneNumber_){
        id = id_;
        type = type_;
        title = title_;
        url = url_;
        imageUrl = imageUrl_;
        dateAdded = dateAdded_;
        dateExpires = dateExpires_;
        dateLastEdited = dateEdited_;
        userId = userId_;
        userName =  userName_;
        description = description_;
        location = location_;
        price = price_;
        phoneNumber = phoneNumber;

    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public String toString() {
        return "Product{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", dateLastEdited='" + dateLastEdited + '\'' +
                ", dateExpires='" + dateExpires + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() { return location; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateAdded() {
        return dateLastEdited;
    }

    public String getDateExpires() {
        return dateExpires;
    }

    public int getPriceAsInt(){
        try {
            return Integer.parseInt(this.price);
        }
        catch (Exception e){
            return 0;
        }

    }
}
