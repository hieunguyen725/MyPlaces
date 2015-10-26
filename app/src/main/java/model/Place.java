package model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Hieu on 10/21/2015.
 */
public class Place {

    // Required Fields
    private String placeID;
    private String username;
    private String name;
    private String address;
    private String mainType;
    private double lat;
    private double lng;


    // Optional Fields
    private String phoneNumber;
    private String description;
    private Bitmap placeImage;
    private Bitmap icon;
    private String imageURL;
    private String websiteURL;
    private List<String> types;
    private List<String> reviews;

    public Place(String placeID, String name, String address, String mainType, double lat, double lng) {
        this.placeID = placeID;
        this.name = name;
        this.address = address;
        this.mainType = mainType;
        this.lat = lat;
        this.lng = lng;
        this.phoneNumber = "Not available";
        this.description = "Not available";
        this.imageURL = "Not available";
        this.websiteURL = "Not available";
    }

    public Place() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name + "\n" + address;
    }
}
