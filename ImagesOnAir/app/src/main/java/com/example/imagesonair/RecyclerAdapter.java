package com.example.imagesonair;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {

    private Context context;
    private List<Upload> uploads;
    private OnItemClickListener listener;

    public RecyclerAdapter(Context context, List<Upload> uploads){
        this.context = context;
        this.uploads = uploads;
    }


    @NonNull
    @Override
    public RecyclerAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_item, viewGroup, false );
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        Upload current = uploads.get(i);
        imageViewHolder.textView.setText(current.getName());
        Picasso.with(context)
                .load(current.getImageURL())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()  //resize the image
                .centerCrop()
                .into(imageViewHolder.imageView);

    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class  ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        //Text view and Image View for our Card Item
        public TextView textView;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onClick(View v) {
            if(listener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Options ");
            MenuItem share = menu.add(Menu.NONE, 1, 1, "Share Image");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete Image");

            share.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(listener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){

                        case 1:
                            listener.onShareClick(position);
                            return true;
                        case 2:
                            listener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
        void onShareClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemclickLiseter(OnItemClickListener onItemclickLiseter){
        listener = onItemclickLiseter;
    }


}
