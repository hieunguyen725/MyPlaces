package model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Hieu on 10/21/2015.
 */
public class Place {

    // Required Fields
    private String placeID;
    private String name;
    private String address;
    private String mainType;
    private int lat;
    private int lng;

    // Optional Fields
    private String phoneNumber;
    private List<String> types;
    private List<String> reviews;
    private Bitmap placeImage;
    private String websiteURL;

    public Place(int lng, int lat, String mainType, String address, String name, String placeID) {
        this.lng = lng;
        this.lat = lat;
        this.mainType = mainType;
        this.address = address;
        this.name = name;
        this.placeID = placeID;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public Bitmap getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(Bitmap placeImage) {
        this.placeImage = placeImage;
    }
}
