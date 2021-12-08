package com.example.saveo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    String website1 = "https://www.omdbapi.com/?s=friends&apikey=7ee18c5e&page=";
    String website2 = "https://www.omdbapi.com/?s=king&apikey=7ee18c5e&page=";

    final List<Movie> slideList = new ArrayList<>();
    final List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t1 = findViewById(R.id.textview1);

        Viewdata v1 = new Viewdata();
        v1.delegate = this;
        v1.setId(1);
        v1.setPage(1);
        v1.execute("GET", website1 + "1");

        Viewdata v2 = new Viewdata();
        v2.delegate = this;
        v2.setId(2);
        v2.setPage(1);
        v2.execute("GET", website2 + "1");
    }

    @Override
    public void processFinish(String output, int viewdata, final int page) {
        switch(viewdata) {
            case 1:
                try {
                    RecyclerView r1 = findViewById(R.id.recyclerview1);

                    JSONObject jsonObject = new JSONObject(output);
                    JSONArray data = jsonObject.getJSONArray("Search");
                    int pages = (jsonObject.getInt("totalResults") + 9) / 10;

                    for (int i = 0; i < data.length(); i++) {
                        Movie movie = new Movie();
                        JSONObject m = data.getJSONObject(i);
                        movie.setTitle(m.getString("Title"));
                        movie.setYear(m.getString("Year"));
                        movie.setImdbId(m.getString("imdbID"));
                        movie.setType(m.getString("Type"));
                        movie.setPoster(m.getString("Poster"));
                        slideList.add(movie);
                    }

                    r1.setHasFixedSize(true);
                    r1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

                    SlideAdapter slideAdapter = new SlideAdapter(this, R.layout.slide, slideList);
                    if (page == 1) {
                        r1.setAdapter(slideAdapter);
                    }
                    else {
                        slideAdapter.notifyItemRangeInserted((page - 1) * 10, 10);
                    }

                    if (page < pages) {
                        int next = page + 1;

                        Viewdata v3 = new Viewdata();
                        v3.delegate = MainActivity.this;
                        v3.setId(1);
                        v3.setPage(next);

                        v3.execute("GET", website1 + Integer.toString(next));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    GridView g1 = findViewById(R.id.gridview1);

                    JSONObject jsonObject = new JSONObject(output);
                    JSONArray data = jsonObject.getJSONArray("Search");
                    int pages = (jsonObject.getInt("totalResults") + 9) / 10;

                    for (int i = 0; i < data.length(); i++) {
                        Movie movie = new Movie();
                        JSONObject m = data.getJSONObject(i);
                        movie.setTitle(m.getString("Title"));
                        movie.setYear(m.getString("Year"));
                        movie.setImdbId(m.getString("imdbID"));
                        movie.setType(m.getString("Type"));
                        movie.setPoster(m.getString("Poster"));
                        movieList.add(movie);
                    }

                    MovieAdapter movieAdapter = new MovieAdapter(this, R.layout.slide, movieList);

                    if(page == 1) {
                        g1.setAdapter(movieAdapter);

                        if (page < pages) {
                            int next = page + 1;

                            Viewdata v4 = new Viewdata();
                            v4.delegate = MainActivity.this;
                            v4.setId(2);
                            v4.setPage(next);

                            v4.execute("GET", website2 + Integer.toString(next));
                        }
                    }
                    else
                    {
                        movieAdapter.notifyDataSetChanged();
                    }

                    g1.setOnScrollListener(new LazyLoader() {
                        @Override
                        public void loadMore(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            if (page < pages) {
                                int next = page + 1;

                                Viewdata v4 = new Viewdata();
                                v4.delegate = MainActivity.this;
                                v4.setId(2);
                                v4.setPage(next);

                                v4.execute("GET", website2 + Integer.toString(next));
                            }
                        }
                    });

                    g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            Movie movieObject = movieList.get(arg2);

                            Intent intent1 = new Intent(MainActivity.this, Slide.class);

                            Bundle bundle = new Bundle();
                            bundle.putParcelable("movie", movieObject);
                            intent1.putExtras(bundle);

                            startActivity(intent1);
                            finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}