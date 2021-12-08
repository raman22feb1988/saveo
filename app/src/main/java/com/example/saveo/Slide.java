package com.example.saveo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Slide extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView i1 = findViewById(R.id.imageview2);
        TextView t1 = findViewById(R.id.textview2);
        TextView t2 = findViewById(R.id.textview3);

        Intent intent3 = getIntent();
        Bundle data = intent3.getExtras();

        Movie movie1 = (Movie) data.getParcelable("movie");

        new ImageLoadTask(movie1.getPoster(), i1).execute();
        t1.setText(movie1.getTitle() + " (" + movie1.getYear() + ")");
        t2.setText("IMDb ID: " + movie1.getImdbId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent5 = new Intent(Slide.this, MainActivity.class);

                startActivity(intent5);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        Intent intent4 = new Intent(Slide.this, MainActivity.class);

        startActivity(intent4);
        finish();
    }
}