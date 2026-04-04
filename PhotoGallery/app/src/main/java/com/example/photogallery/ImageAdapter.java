package com.example.photogallery;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imagePaths;

    public ImageAdapter(Context context, ArrayList<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() { return imagePaths.size(); }

    @Override
    public Object getItem(int position) { return imagePaths.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Hum ek simple built-in layout use kar rahe hain taaki aapko naya XML na banana pade
            convertView = new ImageView(context);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setPadding(8, 8, 8, 8);
        }
        ImageView imageView = (ImageView) convertView;
        imageView.setImageURI(Uri.fromFile(new File(imagePaths.get(position))));
        return imageView;
    }
}