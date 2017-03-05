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
        Song songListItem = (Song) getItem(position);

        /* Take the TextView from layout and set the city's name */
        TextView txtName = (TextView) convertView.findViewById(R.id.songName);
        txtName.setText(songListItem.getTitle());

        /* Take the TextView from layout and set the city's wiki link */
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.authorName);
        txtAuthor.setText(songListItem.getArtist());

        TextView txtAlbum = (TextView) convertView.findViewById(R.id.albumName);
        txtAlbum.setText(songListItem.getAlbum());

        /* Take the ImageView from layout and set the city's image */
        ImageView imageCity = (ImageView) convertView.findViewById(R.id.ImageCity);
        String uri = "drawable/" + songListItem.getArtUri();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable image = context.getResources().getDrawable(imageResource);
        imageCity.setImageDrawable(image);

        return convertView;
    }
}
