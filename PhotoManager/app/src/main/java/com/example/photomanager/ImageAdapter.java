package com.example.photomanager;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<File> files;
    private OnItemClickListener listener;

    public interface OnItemClickListener { void onItemClick(File file); }

    public ImageAdapter(Context context, ArrayList<File> files, OnItemClickListener listener) {
        this.context = context;
        this.files = files;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(4, 4, 4, 4);
        return new ViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = files.get(position);
        holder.iv.setImageURI(Uri.fromFile(file));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(file));
    }

    @Override
    public int getItemCount() { return files.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public ViewHolder(ImageView itemView) { super(itemView); iv = (ImageView) itemView; }
    }
}