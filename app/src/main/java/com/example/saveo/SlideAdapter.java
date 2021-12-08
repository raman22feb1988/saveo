package com.example.saveo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.ViewHolder> {
    Context con;
    int _resource;
    List<Movie> lival;

    View listItem;
    public ImageView ima;

    public SlideAdapter(Context context, int resource, List<Movie> li) {
        // TODO Auto-generated constructor stub
        con = context;
        _resource = resource;
        lival = li;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

            ima = itemView.findViewById(R.id.imageView1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(_resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie mObject = lival.get(position);

        new ImageLoadTask(mObject.getPoster(), ima).execute();

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent2 = new Intent(con, Slide.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", mObject);
                intent2.putExtras(bundle);

                con.startActivity(intent2);
                ((Activity) con).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lival.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}