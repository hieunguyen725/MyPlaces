package parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import controller.NearbySearchActivity;
import model.Place;

/**
 * Created by hieunguyen725 on 10/25/2015.
 */
public class JSONParser {


    public List<Place> searchParse(String myJSON, String searchType) {
        List<Place> resultPlaces = new ArrayList<Place>();
        Log.i(NearbySearchActivity.TAG, "starting to parse");
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            JSONArray results = jsonObject.getJSONArray("results");
            if (results.length() > 0) {
                Log.i(NearbySearchActivity.TAG, "results > 0");
                for (int i = 0; i < results.length(); i++) {
                    Place newPlace = new Place();
                    JSONObject result = results.getJSONObject(i);
                    newPlace.setPlaceID(result.getString("place_id"));
                    newPlace.setName(result.getString("name"));
                    if (searchType.equalsIgnoreCase("nearby")) {
                       newPlace.setAddress(result.getString("vicinity"));
                    } else if (searchType.equalsIgnoreCase("related")) {
                        newPlace.setAddress(result.getString("formatted_address"));
                    }
                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    newPlace.setLat(location.getDouble("lat"));
                    newPlace.setLng(location.getDouble("lng"));
                    newPlace.setIconURL(result.getString("icon"));
                    newPlace.setMainType(result.getJSONArray("types").getString(0).replace("_", " "));
                    newPlace.setContentResource("onlineContent");
                    resultPlaces.add(newPlace);
                }
                return resultPlaces;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Place infoParse(String myJSON) {
        Place newPlace = new Place();
        try {
            // basic info
            JSONObject jsonObject = new JSONObject(myJSON);
            JSONObject resultInfo = jsonObject.getJSONObject("result");
            newPlace.setPlaceID(resultInfo.getString("place_id"));
            newPlace.setName(resultInfo.getString("name"));
            newPlace.setAddress(resultInfo.getString("formatted_address"));
            JSONObject location = resultInfo.getJSONObject("geometry").getJSONObject("location");
            newPlace.setLat(location.getDouble("lat"));
            newPlace.setLng(location.getDouble("lng"));
            newPlace.setIconURL(resultInfo.getString("icon"));
            newPlace.setMainType(resultInfo.getJSONArray("types").getString(0).replace("_", " "));
            // extra info
            if (resultInfo.has("photos")) {
                newPlace.setImageReferences(new ArrayList<String>());
                JSONArray photos = resultInfo.getJSONArray("photos");
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject photo = photos.getJSONObject(i);
                    newPlace.getImageReferences().add(photo.getString("photo_reference"));
                }
            }
            if (resultInfo.has("website")) {
                String websiteURL = resultInfo.getString("website");
                newPlace.setWebsiteURL(websiteURL);
            }
            if (resultInfo.has("opening_hours")) {
                JSONObject openingHours = resultInfo.getJSONObject("opening_hours");
                JSONArray weekdayText = openingHours.getJSONArray("weekday_text");
                String weekdayHours = "";
                for (int i = 0; i < weekdayText.length(); i++) {
                    weekdayHours += weekdayText.getString(i) + "\n";
                }
                newPlace.setOpeningHours(weekdayHours);
            }
            if (resultInfo.has("reviews")) {
                JSONArray reviews = resultInfo.getJSONArray("reviews");
                String reviewsText = "";
                for (int i = 0; i < reviews.length(); i++) {
                    JSONObject review = reviews.getJSONObject(i);
                    reviewsText += (i + 1) + ".   " + review.getString("text") + "\n\n";
                }
                newPlace.setReviews(reviewsText);
            }
            if (resultInfo.has("formatted_phone_number")) {
                String phone = resultInfo.getString("formatted_phone_number");
                newPlace.setPhoneNumber(phone);
            }
            newPlace.setContentResource("onlineContent");
            return newPlace;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
