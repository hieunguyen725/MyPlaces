package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieunguyen725.myplaces.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by hieunguyen725 on 10/26/2015.
 */
public class MyAdapter extends ArrayAdapter<Place> {

    public static final String TAG = "MyAdapter";

    public MyAdapter(Context context, List<Place> places) {
        super(context, R.layout.row_layout_2, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout_2, parent, false);
        Place place = getItem(position);

        TextView placeName = (TextView) theView.findViewById(R.id.row_layout_2_placeName);
        placeName.setText(place.getName());

        TextView placeAddress = (TextView) theView.findViewById(R.id.row_layout_2_placeAddress);
        placeAddress.setText(place.getAddress());

        if (place.getIcon() != null) {
            Log.i(TAG, "Image at position " + position + " is not null");
            ImageView placeIcon = (ImageView) theView.findViewById(R.id.row_layout_2_image);
            placeIcon.setImageBitmap(place.getIcon());
        } else {
            Log.i(TAG, "Image at position " + position + " is null");
            GetIcon task = new GetIcon();
            task.execute(new PlaceViewContainer(place, theView));
        }

        return theView;
    }

    public class PlaceViewContainer {
        public Place place;
        public View view;

        public PlaceViewContainer(Place place, View view) {
            this.place = place;
            this.view = view;
        }
    }

    private class GetIcon extends AsyncTask<PlaceViewContainer, String, PlaceViewContainer> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting task");
        }

        @Override
        protected PlaceViewContainer doInBackground(PlaceViewContainer... params) {
            PlaceViewContainer container = params[0];
            if (container != null) {
                return container = getIcons(container);
            }
            return null;
        }

        private PlaceViewContainer getIcons(PlaceViewContainer container) {
            try {
                String iconURL = container.place.getIconURL();
                InputStream inputStream = (InputStream)
                        new URL(iconURL).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                container.place.setIcon(bitmap);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return container;

        }

        @Override
        protected void onPostExecute(PlaceViewContainer container) {
            ImageView placeIcon = (ImageView) container.view.findViewById(R.id.row_layout_2_image);
            placeIcon.setImageBitmap(container.place.getIcon());
            Log.i(TAG, "Task finished");
        }
    }
}
