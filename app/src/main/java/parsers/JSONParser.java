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
