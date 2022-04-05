package com.example.loaddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class AddMenu extends AppCompatActivity  {

    ArrayList<Series>seriesList;
    recyclerAdapter adapter;
    private Button finish;
    private EditText addTitle;
    private EditText addSeason;
    private EditText addEpisode;
    private NumberPicker numberPickerSeason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        finish = findViewById(R.id.finish);
        addTitle = findViewById(R.id.add_titleText);
        addSeason =findViewById(R.id.add_seasonText);
        addEpisode = findViewById(R.id.add_epText);
    }



    public void AddMenu(View view){
        String title = addTitle.getText().toString();
        addTitle.getText().clear();

        String season = addSeason.getText().toString();
        addSeason.getText().clear();

        String episode = addEpisode.getText().toString();
        addEpisode.getText().clear();

        Intent tent = new Intent(this,MainActivity.class);

        tent.putExtra("add_Title",title);
        tent.putExtra("add_Season",season);
        tent.putExtra("add_Episode",episode);

        startActivity(tent);
    }
}