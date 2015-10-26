package controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieunguyen725.myplaces.R;

import java.util.List;

import model.Place;

/**
 * Created by hieunguyen725 on 10/26/2015.
 */
class MyAdapter extends ArrayAdapter<Place> {

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

        ImageView placeIcon = (ImageView) theView.findViewById(R.id.row_layout_2_image);

//        placeIcon.setImageBitmap();
        return theView;
    }
}
