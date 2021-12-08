package com.example.saveo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    Context con;
    int _resource;
    List<Movie> lival;

    public MovieAdapter(Context context, int resource, List<Movie> li) {
        super(context, resource, li);
        // TODO Auto-generated constructor stub
        con = context;
        _resource = resource;
        lival = li;
    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {
        LayoutInflater linflate = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = linflate.inflate(_resource, null);

        Movie m1 = lival.get(position);
        ImageView ima = vi.findViewById(R.id.imageView1);
        new ImageLoadTask(m1.getPoster(), ima).execute();

        return vi;
    }
}