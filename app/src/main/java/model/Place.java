package model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Hieu on 10/21/2015.
 */
public class Place {

    // Required Fields in database
    private String placeID;
    private String username;
    private String name;
    private String address;
    private String mainType;
    private String iconURL;
    private String description;
    private String phoneNumber;
    private String contentResource;

    // required but not in database
    private Bitmap icon;
    private double lat;
    private double lng;

    // Optional Fields
    private List<Bitmap> placeImages;
    private List<String> imageReferences;
    private String websiteURL;
    private String openingHours;
    private String reviews;

    public Place(String placeID, String username, String name, String address, String mainType,
                 String iconURL, String description, String phoneNumber) {
        this.placeID = placeID;
        this.username = username;
        this.name = name;
        this.address = address;
        this.mainType = mainType;
        this.iconURL = iconURL;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.websiteURL = "Not available";
    }

    public Place() {
        this.phoneNumber = "Not available";
        this.description = "Not available";
        this.websiteURL = "Not available";
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

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public List<Bitmap> getPlaceImages() {
        return placeImages;
    }

    public void setPlaceImages(List<Bitmap> placeImages) {
        this.placeImages = placeImages;
    }

    public List<String> getImageReferences() {
        return imageReferences;
    }

    public void setImageReferences(List<String> imageReferences) {
        this.imageReferences = imageReferences;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getContentResource() {
        return contentResource;
    }

    public void setContentResource(String contentResource) {
        this.contentResource = contentResource;
    }

    @Override
    public String toString() {
        return name + "\n" + address + "\n" + mainType + "\n" + websiteURL;
    }

}
