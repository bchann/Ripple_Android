package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
    private boolean isPlaylist;

    SongListItemAdapter(Context ctx, int resourceId, List objects, boolean isPlaylist) {
        super(ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context = ctx;
        this.isPlaylist = isPlaylist;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent ) {

        /* create a new view of my layout and inflate it in the row */
        convertView = (RelativeLayout) inflater.inflate( resource, null );

        /* Extract the city's object to show */
        Song songListItem = (Song) getItem(position);

        /* Take the TextView from layout and set the city's name */
        TextView txtName = (TextView) convertView.findViewById(R.id.songName);
        txtName.setText(songListItem.getTitle());
        txtName.setSelected(true);

        /* Take the TextView from layout and set the city's wiki link */
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.authorName);
        txtAuthor.setText(songListItem.getArtist());
        txtAuthor.setSelected(true);

        TextView txtAlbum = (TextView) convertView.findViewById(R.id.albumName);
        txtAlbum.setText(songListItem.getAlbumTitle());
        txtAlbum.setSelected(true);

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.ImageCity))
                .execute(songListItem.getMedImageURI());

        TextView moveUp = (TextView) convertView.findViewById(R.id.moveUp);
        TextView moveDown = (TextView) convertView.findViewById(R.id.moveDown);

        if (position == 0 && isPlaylist) {
            convertView.setBackgroundResource(R.drawable.album_border);
            txtName.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            txtAuthor.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            txtAlbum.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        if (position > 1) {
            moveUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 2) {
                        Global.party.getPlaylist().reorder(position, position - 1);
                    }
                }
            });

            moveDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != Global.party.getPlaylist().songs.size() - 1 && position > 1) {
                        Global.party.getPlaylist().reorder(position, position + 1);
                    }
                }
            });
        }

        if (!isPlaylist || position < 2) {
            moveUp.setText("");
            moveDown.setText("");
        }

        if (position == Global.party.getPlaylist().songs.size() - 1) {
            moveDown.setText("");
        }

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
            Log.e("Error", "couldn't create Bitmap");
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
