package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Brian Chan on 3/3/2017.
 */

public class SongListAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public SongListAdapter(Context ctx, int resourceId, List objects) {
        super(ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {

        /* create a new view of my layout and inflate it in the row */
        convertView = (RelativeLayout) inflater.inflate( resource, null );

        /* Extract the city's object to show */
        SongListItem songListItem = (SongListItem) getItem(position);

        /* Take the TextView from layout and set the city's name */
        TextView txtName = (TextView) convertView.findViewById(R.id.cityName);
        txtName.setText(songListItem.getName());

        /* Take the TextView from layout and set the city's wiki link */
        TextView txtWiki = (TextView) convertView.findViewById(R.id.cityLinkWiki);
        txtWiki.setText(songListItem.getSpotify_id());

        /* Take the ImageView from layout and set the city's image */
        ImageView imageCity = (ImageView) convertView.findViewById(R.id.ImageCity);
        String uri = "drawable/" + songListItem.getImage();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable image = context.getResources().getDrawable(imageResource);
        imageCity.setImageDrawable(image);
        return convertView;
    }
}
