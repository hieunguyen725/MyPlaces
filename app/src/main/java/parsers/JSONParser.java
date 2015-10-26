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


    public List<Place> nearbySearchParse(String myJSON) {
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
                    String placeID = result.getString("place_id");
                    String placeName = result.getString("name");
                    String placeAddress = result.getString("vicinity");
                    newPlace.setPlaceID(placeID);
                    if (!placeName.equals(null)) {
                        newPlace.setName(placeName);
                    } else {
                        newPlace.setName("Local Address");
                    }
                    newPlace.setAddress(placeAddress);
                    JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    newPlace.setLat(lat);
                    newPlace.setLng(lng);
                    String iconURL = result.getString("icon");
                    newPlace.setIconURL(iconURL);
                    resultPlaces.add(newPlace);
                }
                return resultPlaces;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
