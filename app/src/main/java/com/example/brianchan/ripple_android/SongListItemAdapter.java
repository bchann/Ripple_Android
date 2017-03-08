package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Brian Chan on 3/3/2017.
 */

public class SongListItemAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    SongListItemAdapter(Context ctx, int resourceId, List objects) {
        super(ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent ) {

        /* create a new view of my layout and inflate it in the row */
        convertView = (RelativeLayout) inflater.inflate( resource, null );

        /* Extract the city's object to show */
        Song songListItem = (Song) getItem(position);

        //songListItem.getData();

        /* Take the TextView from layout and set the city's name */
        TextView txtName = (TextView) convertView.findViewById(R.id.songName);
        txtName.setText(songListItem.getTitle());

        /* Take the TextView from layout and set the city's wiki link */
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.authorName);
        txtAuthor.setText(songListItem.getArtist());

        TextView txtAlbum = (TextView) convertView.findViewById(R.id.albumName);
        txtAlbum.setText(songListItem.getAlbumTitle());

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.ImageCity))
                .execute(songListItem.getMedImageURI());

        return convertView;
    }
}

/**
 * Task to fetch the album artwork and set it as the album cover.
 * I stole this from stackoverflow so don't ask me what it does.
 */
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;

    DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
