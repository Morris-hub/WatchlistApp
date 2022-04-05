package com.example.loaddata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SeriesProfile extends AppCompatActivity {

    private ArrayList<Series> seriesList;
    recyclerAdapter adapter;
    int dIndex;

    private TextView title;
    private TextView season;
    private TextView episode;
    private Button statusBtn;
    private ImageView status;
    //  private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        title = findViewById(R.id.textView2);
        episode = findViewById(R.id.editEpisode);
        season = findViewById(R.id.editSeason);
        status = findViewById(R.id.statusLight);
       // statusBtn = findViewById(R.id.editStatus);


        display();
    }



 public void display(){
     //Get Intent from Main Class and Display
     Bundle extras = getIntent().getExtras();

     dIndex = extras.getInt("current_Position");

     String dTitle = extras.getString("current_Title");
     title.setText(dTitle);

     String dSeason = extras.getString("current_Season");
     season.setText(dSeason);

     String dEpisode = extras.getString("current_Episode");
     episode.setText(dEpisode);


     switch (dEpisode){
         case  "Finished":
         status.setImageResource(R.drawable.grey_circle);
         break;

         case  "0":
         status.setImageResource(R.drawable.orange_circle);
        break;

         default:
             status.setImageResource(R.drawable.green_circle);
             break;
     }
 }


/*
    Send new entry back to Main
    */

    public void changedData(View view) {

        Intent result = new Intent();


            result.putExtra("new_current_Position", dIndex);

            String newTitle = title.getText().toString();
            result.putExtra("new_current_Title", newTitle);

            String newEpisode = episode.getText().toString();
            result.putExtra("new_current_Episode", newEpisode);

            String newSeason = season.getText().toString();
            result.putExtra("new_current_Season", newSeason);

            setResult(RESULT_OK, result);
            finish();
    }
}




